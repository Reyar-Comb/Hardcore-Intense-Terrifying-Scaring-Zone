package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.PropType;
import edu.hitsz.shootStrategy.CircularShoot;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Boss extends AbstractAircraft {
    public Boss(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.speedX = 10;
        this.setShootStrategy(new CircularShoot());
    }

    @Override
    public void forward() {
        locationX += speedX;
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            // 横向超出边界后反向
            speedX = -speedX;
        }
    }

    @Override
    protected List<PropType> getDroppableProps() {
        return Arrays.asList(
                PropType.BLOOD,
                PropType.BULLET,
                PropType.BULLET_PLUS,
                PropType.BOMB,
                PropType.FREEZE
        );
    }

}
