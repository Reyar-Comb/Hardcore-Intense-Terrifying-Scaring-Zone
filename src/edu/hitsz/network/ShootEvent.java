package edu.hitsz.network;

import edu.hitsz.application.LocalPlayerController;
import edu.hitsz.application.RemotePlayerController;

public class ShootEvent implements NetEvent {
    private int X;
    private int Y;
    private int SpeedX;
    private int SpeedY;
    private int Power;

    public ShootEvent(int X, int Y, int SpeedX, int SpeedY, int Power) {
        this.X = X;
        this.Y = Y;
        this.SpeedX = SpeedX;
        this.SpeedY = SpeedY;
        this.Power = Power;
    }

    @Override
    public void apply(RemotePlayerController rpController, LocalPlayerController lpController) {
        rpController.remotePlayerShoot(X, Y, SpeedX, SpeedY, Power);
    }
}
