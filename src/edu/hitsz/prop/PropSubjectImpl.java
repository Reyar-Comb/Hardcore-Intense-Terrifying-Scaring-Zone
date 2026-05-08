package edu.hitsz.prop;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * PropSubject 的简单线程安全实现
 */
public class PropSubjectImpl implements PropSubject {

    private final List<PropObserver> observers = new CopyOnWriteArrayList<>();

    @Override
    public void addObserver(PropObserver o) {
        if (o != null) observers.add(o);
    }

    @Override
    public void removeObserver(PropObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyBomb(BaseProp prop) {
        for (PropObserver o : observers) {
            try {
                o.onBombActivated(prop);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    @Override
    public void notifyFreeze(BaseProp prop) {
        for (PropObserver o : observers) {
            try {
                o.onFreezeActivated(prop);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
