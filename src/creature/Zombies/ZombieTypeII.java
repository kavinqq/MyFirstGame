package creature.Zombies;

import company.gametest9th.utils.Path;

public class ZombieTypeII extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieTypeII(){
        super(13, new Path().img().zombies().zombieTypeII(), FLY_ABILITY.CANNOT_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        return (round*3)/12;
    }
}
