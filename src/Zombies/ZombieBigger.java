package Zombies;

public class ZombieBigger extends Zombie {
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieBigger(){
        super(7);
    }

    /**
     * @param round 這是第幾波的進攻
     * @return 回傳這波 大殭屍 的總攻擊力
     */
    @Override
    public int getAttack(double round){
        return (int)(round / 10d * 5d) * super.getAttack();
    }
}