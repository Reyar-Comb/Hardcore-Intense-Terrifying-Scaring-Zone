package edu.hitsz.network;

import edu.hitsz.application.LocalPlayerController;
import edu.hitsz.application.MouseController;
import edu.hitsz.application.RemotePlayerController;

public class PlayerHpEvent implements NetEvent {
    private int playerId;
    private int hp;

    public PlayerHpEvent(int playerId, int hp) {
        this.playerId = playerId;
        this.hp = hp;
    }

    @Override
    public void apply(RemotePlayerController rpController,  LocalPlayerController lpController) {
        if (playerId == Client.getInstance().PlayerId) {
            lpController.updateLocalPlayerHp(hp);
        } else {
            rpController.updateRemotePlayerHp(hp);
        }
    }
}
