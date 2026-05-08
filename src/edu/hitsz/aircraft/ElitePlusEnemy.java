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
import edu.hitsz.shootStrategy.SimpleShoot;
import edu.hitsz.ui.MainFrame;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ElitePlusEnemy extends AbstractAircraft implements PropObserver {
     private volatile int origSpeedX = Integer.MIN_VALUE;
     private volatile int origSpeedY = Integer.MIN_VALUE;

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.direction = 1;
        this.setShootStrategy(new DoubleShoot());
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
                PropType.BOMB
        );
    }

    @Override
    public void onBombActivated(BaseProp prop) {
        this.vanish();
    }

    @Override
    public void onFreezeActivated(BaseProp prop) {
        if (origSpeedX == Integer.MIN_VALUE) {
            origSpeedX = this.speedX;
            origSpeedY = this.speedY;
        }
        // 速度减半
        this.speedX = (int)(this.speedX * 0.5);
        this.speedY = (int)(this.speedY * 0.5);

        new Thread(() -> {
            try {
                Thread.sleep(5000L);
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
