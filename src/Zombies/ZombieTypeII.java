package Zombies;

public class ZombieTypeII extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieTypeII(){
        super(13);
    }


    @Override
    public int currentTimeCount(int time) {
        return (time/10)*3;
    }
}
