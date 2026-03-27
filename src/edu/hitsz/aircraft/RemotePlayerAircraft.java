package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class RemotePlayerAircraft extends PlayerAircraft{
    public RemotePlayerAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int playerId) {
        super(locationX, locationY, speedX, speedY, hp, playerId);
    }

    @Override
    public void forward() {
        // From UDP Server
    }

    @Override
    /*
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        return res;
    }

}
