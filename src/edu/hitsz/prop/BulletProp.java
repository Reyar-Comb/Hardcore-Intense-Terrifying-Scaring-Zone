package edu.hitsz.prop;


import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootStrategy.CircularShoot;
import edu.hitsz.shootStrategy.ScatterShoot;
import edu.hitsz.shootStrategy.SimpleShoot;

public class BulletProp  extends BaseProp{

    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active() {

        super .active();
        HeroAircraft.getInstance().setShootStrategy(new ScatterShoot());
    }

    @Override
    public void deactive() {
        HeroAircraft.getInstance().setShootStrategy(new SimpleShoot());
    }
}
