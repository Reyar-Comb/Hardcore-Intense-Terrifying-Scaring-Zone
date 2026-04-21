package edu.hitsz.prop;


import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootStrategy.CircularShoot;
import edu.hitsz.shootStrategy.ScatterShoot;
import edu.hitsz.shootStrategy.SimpleShoot;

public class BulletPlusProp  extends BaseProp{

    public BulletPlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active() {

        super .active();
        HeroAircraft.getInstance().setShootStrategy(new CircularShoot());
    }

    @Override
    public void deactive() {
        HeroAircraft.getInstance().setShootStrategy(new SimpleShoot());
    }
}
