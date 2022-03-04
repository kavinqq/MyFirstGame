package Zombies;

public class ZombieKing extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieKing(){
        super(17);
    }

    /**
     * @param round 這是第幾波的進攻
     * @return 回傳這波殭屍王的總攻擊力
     */
    @Override
    public int getAttack(double round){
        return (int)( round / 10d * 2d) * super.getAttack();
    }
}
