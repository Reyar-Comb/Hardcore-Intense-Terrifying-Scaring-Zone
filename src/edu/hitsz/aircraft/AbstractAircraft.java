package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.prop.PropType;
import edu.hitsz.shootStrategy.IShootStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 所有种类飞机的抽象父类
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {

    //最大生命值
    protected int maxHp;
    protected int hp;

    public int direction = 1;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public void increaseHp(int increase, int max) {
        hp += increase;
        if(hp >= max){
            hp = max;
        }
    }

    public int getHp() {
        return hp;
    }


    /**
     * 飞机射击方法
     * @return
     *  可射击对象需实现，返回子弹列表
     *  非可射击对象空实现，返回空列表
     */
    public List<BaseBullet> shoot() {
        return shootStrategy.shoot(this);
    }

    protected List<PropType> getDroppableProps() {
        return Collections.emptyList();
    }

    public Optional<BaseProp> createProp() {
        List<PropType> droppableProps = getDroppableProps();
        if (droppableProps.isEmpty()) {
            return Optional.empty();
        }
        int index = (int) (Math.random() * droppableProps.size());
        PropType propType = droppableProps.get(index);
        return Optional.of(PropFactory.createProp(propType, locationX, locationY, speedX, (int)(speedY * 0.5)));
    }

    private IShootStrategy shootStrategy;

    public void setShootStrategy(IShootStrategy strategy) {
        this.shootStrategy = strategy;
    }

}


