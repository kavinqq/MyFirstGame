package Zombies;

public class ZombieKing extends Zombie {
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieKing() {
        super(17);
    }


    @Override
    public int currentTimeCount(int time) {
        return (time / 10) * 2;
    }
}
