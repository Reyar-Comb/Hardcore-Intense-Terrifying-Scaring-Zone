package edu.hitsz.enemyfactory;

import edu.hitsz.aircraft.Boss;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossFactory implements IEnemyFactory{
    public boolean IsCreated = false;
    public Boss create() {
        return new Boss(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                0,
                500
        );
    }
}
