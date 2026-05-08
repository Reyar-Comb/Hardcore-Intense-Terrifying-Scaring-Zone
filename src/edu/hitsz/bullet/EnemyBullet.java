package edu.hitsz.bullet;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.PropObserver;

/**
 * 敌机子弹
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements PropObserver {
    private volatile int origSpeedX = Integer.MIN_VALUE;
    private volatile int origSpeedY = Integer.MIN_VALUE;

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void forward() {
        super.forward();
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
        this.speedX = 0;
        this.speedY = 0;

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
