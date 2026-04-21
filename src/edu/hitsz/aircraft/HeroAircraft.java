package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.shootStrategy.CircularShoot;
import edu.hitsz.shootStrategy.SimpleShoot;
import edu.hitsz.ui.MainFrame;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {


    //子弹射击方向 (向上发射：-1，向下发射：1)


    private static HeroAircraft instance;

    public static HeroAircraft getInstance() {
        if (instance == null) {
            instance = new HeroAircraft(
                    MainFrame.WINDOW_WIDTH / 2,
                    MainFrame.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                    0, 0, 1000
            );
            instance.direction = -1;
        }
        return instance;
    }

    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setShootStrategy(new SimpleShoot());
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

}
