package creature.Zombies;

import company.gametest9th.utils.Path;

public class ZombieWitch extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieWitch(){
        super(25, new Path().img().zombies().zombieWitch(), FLY_ABILITY.CANNOT_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        return round/10;
    }
}
