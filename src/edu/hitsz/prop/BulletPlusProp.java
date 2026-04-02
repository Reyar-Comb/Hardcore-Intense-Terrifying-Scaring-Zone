package edu.hitsz.prop;


public class BulletPlusProp  extends BaseProp{

    public BulletPlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active() {
        System.out.println("Collected Bullet Plus");
    }
}
