package edu.hitsz.application;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.RemotePlayerAircraft;

import java.util.concurrent.ConcurrentHashMap;

public class RemotePlayerController {
    private Game game;
    private RemotePlayerAircraft remotePlayerAircraft;

    public RemotePlayerController(Game game, RemotePlayerAircraft remotePlayerAircraft){
        this.game = game;
        this.remotePlayerAircraft = remotePlayerAircraft;
    }


    private final ConcurrentHashMap<Integer, RemotePlayerAircraft> remotePlayer = new ConcurrentHashMap<>();

    public void updateRemotePlayer(int playerId, int locationX, int locationY, int Hp) {
        RemotePlayerAircraft remote = remotePlayer.get(playerId);

        if (remote == null)  {
            remote = new RemotePlayerAircraft(locationX, locationY, 0, 0, Hp, playerId);
            remotePlayer.put(playerId, remote);
        } else {
            remote.setLocation(locationX, locationY);
            remote.setHp(Hp);
        }
    }

    public RemotePlayerAircraft getRemotePlayerAircraft(int playerId) {
        return remotePlayer.get(playerId);
    }

}
