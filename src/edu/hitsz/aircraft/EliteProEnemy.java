package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.PropType;
import edu.hitsz.shootStrategy.DoubleShoot;
import edu.hitsz.shootStrategy.NullShoot;
import edu.hitsz.shootStrategy.ScatterShoot;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EliteProEnemy extends AbstractAircraft {
    public EliteProEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.direction = 1;
        this.setShootStrategy(new ScatterShoot());
    }


    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
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
