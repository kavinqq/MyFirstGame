package creature.Zombies;

public class ZombieTypeI extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieTypeI(){
        super(10, FLYABILITY.CAN_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        return (round/10)*4;
    }
}