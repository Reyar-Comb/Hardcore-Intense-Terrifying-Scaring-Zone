package edu.hitsz.prop;

public interface PropSubject {
    void addObserver(PropObserver observer);
    void removeObserver(PropObserver observer);
    void notifyBomb(BaseProp prop);
    void notifyFreeze(BaseProp prop);
}
