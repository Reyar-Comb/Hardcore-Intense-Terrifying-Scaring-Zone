package edu.hitsz.network;

import edu.hitsz.aircraft.RemotePlayerAircraft;
import edu.hitsz.application.Game;
import edu.hitsz.application.RemotePlayerController;

import java.rmi.Remote;

public class PlayerMoveEvent implements NetEvent{
    private int X;
    private int Y;

    public PlayerMoveEvent(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    @Override
    public void apply(RemotePlayerController rpController) {
        rpController.updateRemotePlayerLocation(X, Y);
    }
}
