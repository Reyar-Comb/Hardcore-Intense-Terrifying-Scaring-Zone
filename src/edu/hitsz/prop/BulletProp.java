package edu.hitsz.prop;


public class BulletProp  extends BaseProp{

    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active() {
        System.out.println("Collected Bullet");
    }
}
