package creature.Zombies;

public class ZombieLichKing extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieLichKing(){
        super(25, FLYABILITY.CANNOT_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        return round/10;
    }
}
