package creature.Zombies;

public class ZombieTypeII extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieTypeII(){
        super(13, FLYABILITY.CANNOT_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        return (round*3)/12;
    }
}
