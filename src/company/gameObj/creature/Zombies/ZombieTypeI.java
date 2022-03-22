package company.gameObj.creature.Zombies;

import company.gametest9th.utils.Path;

public class ZombieTypeI extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieTypeI(){
        super(10, new Path().img().zombies().zombieTypeI(), FLY_ABILITY.CANNOT_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        return (round*4)/12;
    }
}
