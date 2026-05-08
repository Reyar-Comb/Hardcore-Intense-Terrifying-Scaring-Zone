package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.audio.AudioManager;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.dataAccess.ScoreRecordDao;
import edu.hitsz.dataAccess.ScoreRecordDaoImpl;
import edu.hitsz.enemyfactory.*;
import edu.hitsz.prop.*;
import edu.hitsz.ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * 游戏主面板，游戏启动
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    //调度器, 用于定时任务调度
    private final Timer timer;
    //时间间隔(ms)，控制刷新频率
    private final int timeInterval = 40;
    public static double enemyHpMultiplier = 1;
    public static double bossHpMultiplier = 1;
    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseProp> props;

    private final PropSubject propSubject = new PropSubjectImpl();

    private MobEnemyFactory mobEnemyFactory;
    private EliteEnemyFactory eliteEnemyFactory;
    private ElitePlusEnemyFactory elitePlusEnemyFactory;
    private EliteProEnemyFactory eliteProEnemyFactory;
    private BossFactory bossFactory;

    //屏幕中出现的敌机最大数量
    protected int enemyMaxNumber = 5;

    //敌机生成周期
    protected double enemySpawnCycle  =  10;
    private int enemySpawnCounter = 0;

    //敌机生成概率
    protected double eliteProb = 0.6;
    protected double elitePlusProb = 0.3;
    protected double eliteProProb = 0.1;

    //英雄机和敌机射击周期
    protected double shootCycle = 20;
    private int shootCounter = 0;

    //当前玩家分数
    public int score = 0;

    protected int bossScore = 100;
    // nextBossScore: score threshold at which next boss will spawn (score-based delta)
    protected int nextBossScore;

    public final ScoreRecordDao dao = new ScoreRecordDaoImpl();

    // d
    public String difficulty = "easy";

    public MainFrame mainFrame;

    //游戏结束标志
    private boolean gameOverFlag = false;

    public Game() {
        heroAircraft = HeroAircraft.getInstance();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        mobEnemyFactory = new MobEnemyFactory();
        eliteEnemyFactory = new EliteEnemyFactory();
        elitePlusEnemyFactory = new ElitePlusEnemyFactory();
        eliteProEnemyFactory = new EliteProEnemyFactory();
        bossFactory = new BossFactory();

        AudioManager.getInstance().Init();

        // initialize nextBossScore threshold
        this.nextBossScore = this.bossScore;

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public final void action() {
        onStart();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                templateTick();
            }
        };
        timer.schedule(task, 0, timeInterval);
    }

    private final void templateTick() {
        updateVariable();
        generateEnemyStep();
        shootStep();
        bulletsMoveStep();
        propsMoveStep();
        aircraftsMoveStep();
        crashCheckStep();
        postProcessStep();
        paintStep();
        checkResultStep();
    }

    protected void onStart() { AudioManager.getInstance().PlayBGM(); }
    protected void generateEnemyStep() {
        enemySpawnCounter++;
        // spawn boss when player's score reaches threshold and boss not already present
        if (!bossFactory.IsCreated && score >= nextBossScore) {
            generateBoss();
        }

        if (enemySpawnCounter >=enemySpawnCycle) {
            enemySpawnCounter = 0;
            // 产生普通敌机
            generateEnemy();

        }
    }
    protected void updateVariable() {}
    protected void shootStep() { shootAction(); }
    protected void bulletsMoveStep() { bulletsMoveAction(); }
    protected void propsMoveStep() { propMoveAction(); }
    protected void aircraftsMoveStep() { aircraftsMoveAction(); }
    protected void crashCheckStep() { crashCheckAction(); }
    protected void postProcessStep() { postProcessAction(); }
    protected void paintStep() { repaint(); }
    protected void checkResultStep() { checkResultAction(); }





    //***********************
    //      Action 各部分
    //***********************

    // 产生道具

    //产生敌机
    private void generateEnemy() {
        if (enemyAircrafts.size() < enemyMaxNumber) {
            double rand = Math.random();
            AbstractAircraft enemy = null;
            if (rand < eliteProProb) {
                enemy = eliteProEnemyFactory.create();
            } else if (rand < elitePlusProb) {
                enemy = elitePlusEnemyFactory.create();
            } else if (rand < eliteProb) {
                enemy = eliteEnemyFactory.create();
            } else {
                enemy = mobEnemyFactory.create();
            }
            enemyAircrafts.add(enemy);
            if (enemy instanceof PropObserver) {
                propSubject.addObserver((PropObserver) enemy);
            }

        }
    }

    private void generateBoss() {
        if (!bossFactory.IsCreated) {
            AbstractAircraft boss = bossFactory.create();
            enemyAircrafts.add(boss);
            if (boss instanceof PropObserver) {
                propSubject.addObserver((PropObserver) boss);
            }
            bossFactory.IsCreated = true;

            AudioManager.getInstance().StopBGM();
            AudioManager.getInstance().PlayBoss();
        }
    }

    private void shootAction() {
        shootCounter++;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            //英雄机射击
            heroBullets.addAll(heroAircraft.shoot());
            // 敌机射击并注册子弹观察者
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                List<BaseBullet> newBullets = enemyAircraft.shoot();
                if (!newBullets.isEmpty()) {
                    enemyBullets.addAll(newBullets);
                    for (BaseBullet b : newBullets) {
                        if (b instanceof PropObserver) {
                            propSubject.addObserver((PropObserver) b);
                        }
                    }
                }
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    /**
     * 道具逻辑：
     * - 对于炸弹、冰冻：只有敌机或敌机子弹碰到时生效（通过 PropSubject 通知观察者）
     * - 其它道具（回血、子弹等）：英雄碰到时生效（保持原逻辑）
     */
    private void propMoveAction() {
        for (BaseProp prop : props) {
            if (prop.notValid()) {
                continue;
            }

            // 先移动道具，确保道具按原逻辑前进
            prop.forward();

            // 如果移动后已失效，跳过
            if (prop.notValid()) {
                continue;
            }

            // 只有英雄能触发道具
            if (prop.crash(heroAircraft)) {
                prop.active();
                // 对于炸弹/冰冻：英雄拾取后对所有观察者生效
                if (prop instanceof BombProp) {
                    propSubject.notifyBomb(prop);
                } else if (prop instanceof FreezeProp) {
                    propSubject.notifyFreeze(prop);
                }
                prop.vanish();
            }
        }
    }



    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给（保留原逻辑）
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄机
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();

            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid() && enemyAircraft instanceof Boss) {
                        for (int i = 0; i < 3; i++ ) {
                            Optional<BaseProp> prop = enemyAircraft.createProp();
                            prop.ifPresent(props::add);
                        }
                        score += 100;
                        AudioManager.getInstance().StopBoss();
                        AudioManager.getInstance().PlayBGM();
                        // boss defeated: reset boss factory flag and set next spawn threshold to current score + bossScore
                        bossFactory.IsCreated = false;
                        nextBossScore = this.score + this.bossScore;
                    }
                    if (enemyAircraft.notValid()) {
                        // 获得分数，产生道具补给
                        Optional<BaseProp> prop = enemyAircraft.createProp();
                        prop.ifPresent(props::add);

                        score += 10;
                    }
                    AudioManager.getInstance().PlaySFX("hit");
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 保留英雄拾取道具的行为（对于非炸弹/非冰冻道具）
        
    }


    /**
     * 后处理：
     * 1. 删除无效的子弹（并注销观察者）
     * 2. 删除无效的敌机（并注销观察者）
     * 3. 删除无效的道具
     */
    private void postProcessAction() {
        // 注销并移除无效敌机子弹
        Iterator<BaseBullet> itb = enemyBullets.iterator();
        while (itb.hasNext()) {
            BaseBullet b = itb.next();
            if (b.notValid()) {
                if (b instanceof PropObserver) {
                    propSubject.removeObserver((PropObserver) b);
                }
                itb.remove();
            }
        }

        // 我方子弹简单移除
        heroBullets.removeIf(AbstractFlyingObject::notValid);

        // 注销并移除无效敌机
        Iterator<AbstractAircraft> ita = enemyAircrafts.iterator();
        while (ita.hasNext()) {
            AbstractAircraft a = ita.next();
            if (a.notValid()) {
                if (a instanceof PropObserver) {
                    propSubject.removeObserver((PropObserver) a);
                }
                ita.remove();
            }
        }

        // 道具移除
        props.removeIf(AbstractFlyingObject::notValid);
    }

    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction(){
        // 游戏结束检查英雄机是否存活
        if (heroAircraft.getHp() <= 0) {
            // stop timer
            timer.cancel(); // 取消定时器并终止所有调度任务
            gameOverFlag = true;
            if (AudioManager.getInstance().IsBoss) {
                AudioManager.getInstance().StopBoss();
            } else {
                AudioManager.getInstance().StopBGM();
            }
            System.out.println("Game Over!");

            AudioManager.getInstance().PlaySFX("gameover");

            // UI updates must run on Swing EDT. Also ensure leaderBoard has dao reference set.
            javax.swing.SwingUtilities.invokeLater(() -> {
                if (mainFrame != null) {
                    if (mainFrame.leaderBoard != null) {
                        mainFrame.leaderBoard.dao = this.dao;
                        mainFrame.leaderBoard.RefreshTable(this);
                    }
                    mainFrame.cardLayout.show(mainFrame.mainContainer, "leaderBoard");
                    mainFrame.mainContainer.revalidate();
                    mainFrame.mainContainer.repaint();
                }
            });
        }
    };

    //***********************
    //      Paint 各部分
    //***********************
    /**
     * 重写 paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        paintBG(g);

        this.backGroundTop += 1;
        if (this.backGroundTop == MainFrame.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);

        // 绘制道具
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintBG(Graphics g) {
        switch (difficulty) {
            case "easy": {
                g.drawImage(ImageManager.BACKGROUND_IMAGE_EASY, 0, this.backGroundTop - MainFrame.WINDOW_HEIGHT, null);
                g.drawImage(ImageManager.BACKGROUND_IMAGE_EASY, 0, this.backGroundTop, null);
                break;
            }
            case "normal": {
                g.drawImage(ImageManager.BACKGROUND_IMAGE_NORMAL, 0, this.backGroundTop - MainFrame.WINDOW_HEIGHT, null);
                g.drawImage(ImageManager.BACKGROUND_IMAGE_NORMAL, 0, this.backGroundTop, null);
                break;
            }
            case "hard": {
                g.drawImage(ImageManager.BACKGROUND_IMAGE_HARD, 0, this.backGroundTop - MainFrame.WINDOW_HEIGHT, null);
                g.drawImage(ImageManager.BACKGROUND_IMAGE_HARD, 0, this.backGroundTop, null);
                break;
            }
        }
    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE: " + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

    public void SetDifficulty(String difficulty) {
        switch (difficulty) {
            case "easy": {
                this.difficulty = "easy";
                break;
            }
            case "normal": {
                this.difficulty = "normal";
                break;
            }
            case "hard": {
                this.difficulty = "hard";
                break;
            }
        }
    }

}
