package edu.hitsz.enemyfactory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.ui.MainFrame;

public class ElitePlusEnemyFactory implements IEnemyFactory{
    public AbstractAircraft create() {
        return new ElitePlusEnemy(
                (int) (Math.random() * (MainFrame.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * MainFrame.WINDOW_HEIGHT * 0.05),
                (int) (Math.random() * 4 - 2),
                7,
                30
        );
    }
}
