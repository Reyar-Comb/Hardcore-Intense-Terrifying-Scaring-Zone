package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.PropObserver;
import edu.hitsz.shootStrategy.NullShoot;
import edu.hitsz.ui.MainFrame;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击、不掉落道具
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft implements PropObserver {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setShootStrategy(new NullShoot());
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainFrame.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public void onBombActivated(BaseProp prop) {
        this.vanish();
    }

    @Override
    public void onFreezeActivated(BaseProp prop) {
        this.speedX = 0;
        this.speedY = 0;
    }

}
