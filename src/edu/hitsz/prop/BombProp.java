package edu.hitsz.prop;


import edu.hitsz.audio.AudioManager;

public class BombProp  extends BaseProp{

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active() {
        AudioManager.getInstance().PlaySFX("bomb");
        System.out.println("Collected Bomb");
    }
}
