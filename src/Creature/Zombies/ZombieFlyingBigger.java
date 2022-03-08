package Creature.Zombies;

/**
 * @author Lillian
 * @Date 2022/3/3
 * @Description
 */
public class ZombieFlyingBigger extends Zombie implements Flyable {

    public ZombieFlyingBigger(){
        super(4, FLYABILITY.CAN_FLY);
    }


    @Override
    public int currentTimeCount(int time) {
        return (time-7)/2;
    }
}
