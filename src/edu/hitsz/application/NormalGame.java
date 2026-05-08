package edu.hitsz.application;

public class NormalGame extends Game {
    private final int increaseIntervalSeconds = 30;
    private final double speedIncreaseFactor = 1.10;
    private final double hpIncreaseAmount = 10;

    private int tick = 0;
    private int maxtick = 3000;
    public NormalGame() {
        this.enemySpawnCycle = 10.0;
        this.shootCycle = 20.0;
        this.enemyMaxNumber = 5;
        this.eliteProb = 0.6;
        this.elitePlusProb = 0.3;
        this.eliteProProb = 0.1;
        this.bossScore = 100;
        this.difficulty = "normal";
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
