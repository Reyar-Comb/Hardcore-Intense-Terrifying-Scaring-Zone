package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    @Test
    void getInstance() {
        HeroAircraft h1 = HeroAircraft.getInstance();
        HeroAircraft h2 = HeroAircraft.getInstance();
        assertNotNull(h1, "单例实例不应为 null");
        assertSame(h1, h2, "getInstance 应返回同一实例");
    }

    @Test
    void shoot() {
        HeroAircraft hero = HeroAircraft.getInstance();
        List<BaseBullet> bullets = hero.shoot();
        assertNotNull(bullets, "shoot() 应返回非 null 的列表");

    }

    @Test
    void decreaseHp(){
        HeroAircraft h = HeroAircraft.getInstance();
        int before = h.getHp();
        h.decreaseHp(1);
        assertTrue(h.getHp() <= before, "decreaseHp 应减少或不增 hp");
        h.decreaseHp(Integer.MAX_VALUE);
        assertTrue(h.getHp() >= 0, "hp 不应为负数");
    }
}