package Creature.Zombies;

public class ZombieBigger extends Zombie {
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieBigger(){
        super(7, FLYABILITY.CANNOT_FLY);
    }

    @Override
    public int currentRoundCount(int round) {
        return (round/10)*5;
    }
}