package edu.hitsz.application;

import edu.hitsz.aircraft.HeroAircraft;

public class LocalPlayerController {
    private Game game;
    private HeroAircraft heroAircraft;

    public LocalPlayerController(Game game, HeroAircraft heroAircraft){
        this.game = game;
        this.heroAircraft = heroAircraft;
    }

    public void updateLocalPlayerHp(int Hp) {
        heroAircraft.setHp(Hp);
    }

    public void GameOver(boolean isWinner) {
        game.GameOver(isWinner);
    }
}

