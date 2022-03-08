package Creature.Zombies;

public class ZombieNormal extends Zombie {
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieNormal() {
        super(5, FLYABILITY.CAN_FLY);
    }


    @Override
    public int currentTimeCount(int time) {
        return time * 3;
    }
}
