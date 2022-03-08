package Creature.Zombies;

public class ZombieNormal extends Zombie {
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieNormal() {
        super(5, FLYABILITY.CAN_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        return round * 3;
    }
}
