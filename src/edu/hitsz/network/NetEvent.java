package edu.hitsz.network;

import edu.hitsz.application.Game;
import edu.hitsz.application.RemotePlayerController;

public interface NetEvent {
    void apply(RemotePlayerController rpController);
}
