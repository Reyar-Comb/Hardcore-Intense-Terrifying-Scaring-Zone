package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.PropType;
import edu.hitsz.shootStrategy.SimpleShoot;
import edu.hitsz.ui.MainFrame;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractAircraft{
    private int shootNum = 1;
    private int power = 30;
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.direction = 1;
        this.setShootStrategy(new SimpleShoot());
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainFrame.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    protected List<PropType> getDroppableProps() {
        return Arrays.asList(
            PropType.BLOOD,
            PropType.BULLET,
            PropType.BULLET_PLUS
        );
    }


}
