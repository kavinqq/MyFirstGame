package Zombies;

public class ZombieLichKing extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieLichKing(){
        super(25);
    }

    /**
     * @param round 這是第幾波的進攻
     * @return 回傳這波巫妖的總攻擊力
     */
    @Override
    public int getAttack(double round){
        return (int)(round  / 10d) * super.getAttack();
    }
}
