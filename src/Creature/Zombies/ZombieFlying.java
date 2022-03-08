package Creature.Zombies;

/**
 * 飛行殭屍
 */
public class ZombieFlying extends Zombie implements Flyable {

    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieFlying() {
        super(2, FLYABILITY.CAN_FLY);
    }

    @Override
    public int currentTimeCount(int time) {
        return time-7;
    }
}
