package Zombies;

public class ZombieTypeI extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieTypeI(){
        super(10);
    }

    /**
     * @param round 這是第幾波的進攻
     * @return 回傳這波 I型變異體 的總攻擊力
     */
    @Override
    public int getAttack(double round){
        return (int)(round / 10d * 4d) * super.getAttack();
    }
}
