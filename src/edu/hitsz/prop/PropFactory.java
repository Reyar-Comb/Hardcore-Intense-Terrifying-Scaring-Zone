package edu.hitsz.prop;

public class PropFactory {
    public static BaseProp createProp(PropType type, int locationX, int locationY, int speedX, int speedY) {
        switch(type) {
            case BLOOD:
                return new BloodProp(locationX, locationY, speedX, speedY);
            case BOMB:
                return new BombProp(locationX, locationY, speedX, speedY);
            case BULLET:
                return new BulletProp(locationX, locationY, speedX, speedY);
            case BULLET_PLUS:
                return new BulletPlusProp(locationX, locationY, speedX, speedY);
            case FREEZE:
                return new FreezeProp(locationX, locationY, speedX, speedY);
        }
        return new BloodProp(locationX, locationY, speedX, speedY);
    }
}
