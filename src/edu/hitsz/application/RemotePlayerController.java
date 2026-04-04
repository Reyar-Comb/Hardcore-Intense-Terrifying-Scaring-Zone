package edu.hitsz.application;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.RemotePlayerAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.RemoteBullet;

import java.util.concurrent.ConcurrentHashMap;

public class RemotePlayerController {
    private Game game;
    private RemotePlayerAircraft remotePlayerAircraft;

    public RemotePlayerController(Game game, RemotePlayerAircraft remotePlayerAircraft){
        this.game = game;
        this.remotePlayerAircraft = remotePlayerAircraft;
    }

    public void updateRemotePlayerLocation(int locationX, int locationY) {
        if (locationX<0 || locationX>Main.WINDOW_WIDTH || locationY<0 || locationY>Main.WINDOW_HEIGHT){
            // 防止超出边界
            return;
        }
        refineSetRemoteLocation(locationX, locationY);
    }

    public void refineSetRemoteLocation(int locationX, int locationY) {
        remotePlayerAircraft.setLocation(Main.WINDOW_WIDTH - locationX, Main.WINDOW_HEIGHT - locationY);
    }

    public void remotePlayerShoot(int locationX, int locationY, int speedX, int speedY, int power) {
        game.addRemoteBullet(new RemoteBullet(Main.WINDOW_WIDTH - locationX, Main.WINDOW_HEIGHT - locationY, -speedX, -speedY, power));
    }

    public void updateRemotePlayerHp(int hp) {
        remotePlayerAircraft.setHp(hp);
    }
}
