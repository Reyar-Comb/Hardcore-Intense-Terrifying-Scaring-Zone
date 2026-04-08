package edu.hitsz.shootStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.ArrayList;
import java.util.List;

public class SimpleShoot implements IShootStrategy{
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> baseBulletList = new ArrayList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.direction*2;
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + aircraft.direction*5;
        int shootNum = 1;
        int power = 30;
        BaseBullet bullet;
        for(int i = 0; i < shootNum; i++) {
            bullet = aircraft instanceof HeroAircraft ?
                    new HeroBullet(x, y, speedX, speedY, power) :
                    new EnemyBullet(x, y, speedX, speedY, power);
            baseBulletList.add(bullet);
        }
        return baseBulletList;
    }
}
