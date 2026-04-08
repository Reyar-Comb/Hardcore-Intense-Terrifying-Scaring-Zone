package edu.hitsz.prop;


import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootStrategy.CircularShoot;
import edu.hitsz.shootStrategy.ScatterShoot;

public class BulletProp  extends BaseProp{

    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active() {
        HeroAircraft.getInstance().setShootStrategy(new ScatterShoot());
    }
}
