package edu.hitsz.enemyfactory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteProEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.ui.MainFrame;

public class EliteProEnemyFactory implements IEnemyFactory{
    public AbstractAircraft create() {
        return new EliteProEnemy(
                (int) (Math.random() * (MainFrame.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * MainFrame.WINDOW_HEIGHT * 0.05),
                (int) (Math.random() * 6 - 3),
                8,
                30
        );
    }
}
