package creature.Zombies;

public class ZombieKing extends Zombie {
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieKing() {
        super(17, FLYABILITY.CANNOT_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        return (round * 2 )/12 ;
    }
}
