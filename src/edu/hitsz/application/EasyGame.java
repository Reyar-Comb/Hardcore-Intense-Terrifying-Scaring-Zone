package edu.hitsz.application;

public class EasyGame extends Game {
    public EasyGame() {
        this.mainFrame = mainFrame;
        this.enemySpawnCycle = 12.0;
        this.shootCycle = 24.0;
        this.enemyMaxNumber = 3;
        this.eliteProb = 0.4;
        this.elitePlusProb = 0.2;
        this.eliteProProb = 0.05;
        this.bossScore = Integer.MAX_VALUE;
        this.difficulty = "easy";
    }
}
