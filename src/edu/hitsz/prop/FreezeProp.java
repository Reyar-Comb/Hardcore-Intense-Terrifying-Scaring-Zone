package edu.hitsz.prop;


public class FreezeProp  extends BaseProp{

    public FreezeProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active() {
        super .active();
        System.out.println("Collected Freeze");
    }
}
