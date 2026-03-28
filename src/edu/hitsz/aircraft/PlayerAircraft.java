package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class PlayerAircraft extends AbstractAircraft{

    public int playerId = 0;

    public PlayerAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        return res;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

}
