package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class BloodProp  extends BaseProp{

    private final HeroAircraft heroAircraft = HeroAircraft.getInstance();

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active() {
        heroAircraft.increaseHp(100, 10000);
    }
}
