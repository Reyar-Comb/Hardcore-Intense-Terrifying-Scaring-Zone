package edu.hitsz.shootStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class CircularShoot implements IShootStrategy{
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft){
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();
        int speedX = 0;
        int speedY = aircraft.getSpeedY();
        int speedCir = 10;
        int shootNum = 20;
        int power = 30;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            double angle = i * (2 * Math.PI / shootNum);
            int speedCirX = (int)(Math.cos(angle) * speedCir);
            int speedCirY = (int)(Math.sin(angle) * speedCir);
            bullet = aircraft instanceof HeroAircraft ?
                    new HeroBullet(x, y, speedCirX , speedCirY, power) :
                    new EnemyBullet(x, y, speedCirX , speedCirY, power);
            res.add(bullet);
        }
        return res;
    }
}
