package edu.hitsz.application;

public class HardGame extends Game {
    private final int increaseIntervalSeconds = 30;
    private final double speedIncreaseFactor = 1.10;
    private final double hpIncreaseAmount = 10;

    private int tick = 0;
    private int maxtick = 2000;
    public HardGame() {
        this.enemySpawnCycle = 8.0;
        this.shootCycle = 25.0;
        this.enemyMaxNumber = 10;
        this.eliteProb = 0.7;
        this.elitePlusProb = 0.4;
        this.eliteProProb = 0.2;
        this.bossScore = 100;
        this.difficulty = "hard";
        bossHpMultiplier = 1.2;
    }

    @Override
    protected void updateVariable() {
        super.updateVariable();
        this.tick++;
        if (tick > maxtick) {
            this.enemySpawnCycle = Math.max(2.0, this.enemySpawnCycle * 0.95);
            enemyHpMultiplier += 0.1;
        }
    }
}
