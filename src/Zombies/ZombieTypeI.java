package Zombies;

public class ZombieTypeI extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieTypeI(){
        super(10);
    }


    @Override
    public int currentTimeCount(int time) {
        return (time/10)*4;
    }
}