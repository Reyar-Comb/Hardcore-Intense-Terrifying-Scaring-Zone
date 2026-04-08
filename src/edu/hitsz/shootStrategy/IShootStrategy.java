package edu.hitsz.shootStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface IShootStrategy {
    List<BaseBullet> shoot(AbstractAircraft aircraft);
}
