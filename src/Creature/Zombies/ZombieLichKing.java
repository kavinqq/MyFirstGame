package Creature.Zombies;

public class ZombieLichKing extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieLichKing(){
        super(25, FLYABILITY.CAN_FLY);
    }


    @Override
    public int currentTimeCount(int time) {
        return time/10;
    }
}
