package company.gameObj.creature.human;

import company.gametest9th.utils.Path;

import java.awt.*;

public class ArmySoldier extends Soldier  {  //士兵
    private static final int painterWidth = 50;
    private static final int painterHeight = 50;
    private static final int colliderWidth = 50;
    private static final int colliderHeight = 50;
    /**
     * 建構子 預設士兵數值為2 ,等級為1
     */
    public ArmySoldier(int x, int y, int level){
        super(x,y, painterWidth, painterHeight, colliderWidth, colliderHeight, 2 + 3*level, new Path().img().humans().armySoldier(), FLY_ABILITY.CANNOT_FLY, SOLDIER_TYPE.ARMY);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.getImg(), this.painter().left(), this.painter().top(), null);
    }

    @Override
    public void update() {
        //TODO:???
    }
}

