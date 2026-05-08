package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.PropObserver;
import edu.hitsz.prop.PropType;
import edu.hitsz.shootStrategy.DoubleShoot;
import edu.hitsz.shootStrategy.NullShoot;
import edu.hitsz.shootStrategy.ScatterShoot;
import edu.hitsz.ui.MainFrame;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EliteProEnemy extends AbstractAircraft implements PropObserver {
    private volatile int origSpeedX = Integer.MIN_VALUE;
    private volatile int origSpeedY = Integer.MIN_VALUE;

    public EliteProEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.direction = 1;
        this.setShootStrategy(new ScatterShoot());
    }


    @Override
    public void forward() {
        super.forward();
        if (locationY >= MainFrame.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    protected List<PropType> getDroppableProps() {
        return Arrays.asList(
                PropType.BLOOD,
                PropType.BULLET,
                PropType.BULLET_PLUS,
                PropType.BOMB,
                PropType.FREEZE
        );
    }
    @Override
    public void onBombActivated(BaseProp prop) {

        this.decreaseHp(50);
    }

    @Override
    public void onFreezeActivated(BaseProp prop) {
        if (origSpeedX == Integer.MIN_VALUE) {
            origSpeedX = this.speedX;
            origSpeedY = this.speedY;
        }
        // 速度减半（这里采用静止恢复为减速要求为 3s 恢复为原速）
        this.speedX = 0;
        this.speedY = 0;

        new Thread(() -> {
            try {
                Thread.sleep(3000L);
                if (this.notValid()) return;
                if (origSpeedX != Integer.MIN_VALUE) {
                    this.speedX = origSpeedX;
                    this.speedY = origSpeedY;
                    origSpeedX = Integer.MIN_VALUE;
                    origSpeedY = Integer.MIN_VALUE;
                }
            } catch (InterruptedException ignored) {}
        }).start();
    }

}
