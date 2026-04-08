package edu.hitsz.shootStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShoot implements IShootStrategy{

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft){
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.direction*2;
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + aircraft.direction*5;
        BaseBullet bullet;
        int shootNum = 3;
        int power = 30;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = aircraft instanceof HeroAircraft ?
                    new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX + (i * 2 + 1 - shootNum) , speedY, power) :
                    new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX + (i * 2 + 1 - shootNum) , speedY, power);
            res.add(bullet);
        }
        return res;
    }
}
