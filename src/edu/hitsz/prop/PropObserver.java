package edu.hitsz.prop;

public interface PropObserver {
    void onBombActivated(BaseProp prop);
    void onFreezeActivated(BaseProp prop);
}
