package edu.hitsz.enemyfactory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.ui.MainFrame;

public class MobEnemyFactory implements IEnemyFactory{
    public AbstractAircraft create() {
        return new MobEnemy(
                (int) (Math.random() * (MainFrame.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * MainFrame.WINDOW_HEIGHT * 0.05),
                0,
                7,
                30
        );
    }
}
