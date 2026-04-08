package edu.hitsz.shootStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.ArrayList;
import java.util.List;

public class NullShoot implements IShootStrategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        // do nothing
        return new ArrayList<>();
    }
}
