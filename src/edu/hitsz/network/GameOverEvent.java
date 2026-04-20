package edu.hitsz.network;

import edu.hitsz.application.LocalPlayerController;
import edu.hitsz.application.RemotePlayerController;

public class GameOverEvent implements NetEvent {
    private int winnerId;

    public GameOverEvent(int winnerId) {
        this.winnerId = winnerId;
    }

    @Override
    public void apply(RemotePlayerController rpController, LocalPlayerController lpController){
        lpController.GameOver(winnerId == Client.getInstance().PlayerId);
    }
}
