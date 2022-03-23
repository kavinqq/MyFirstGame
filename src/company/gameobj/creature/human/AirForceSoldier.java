package company.gameobj.creature.human;

import company.gametest9th.utils.Path;

import java.awt.*;

/**
 * @author Lillian
 * @Date 2022/3/7
 * @Description
 */
public class AirForceSoldier extends Soldier{
    private static final int painterWidth = 64;
    private static final int painterHeight = 64;
    private static final int colliderWidth = 80;
    private static final int colliderHeight = 80;
    private static final int SPEED = 10;

    public AirForceSoldier(int x, int y, int level){
        super(x,y, painterWidth, painterHeight, colliderWidth, colliderHeight, 2 + 2*level, SPEED, new Path().img().humans().armySoldier() ,FLY_ABILITY.CAN_FLY, SOLDIER_TYPE.AIR_FORCE);
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