package Creature.Zombies;

public class ZombieBigger extends Zombie {
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieBigger(){
        super(7, FLYABILITY.CANNOT_FLY);
    }

    @Override
    public int currentTimeCount(int time) {
        return (time/10)*5;
    }
}