package company.gameobj.creature.human;

import company.Global;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.HumanAnimator;
import company.gametest9th.utils.Path;

import java.awt.*;

public class ArmySoldier extends Soldier  {  //士兵

    private static final int painterWidth = 64;
    private static final int painterHeight = 64;
    private static final int colliderWidth = 256;
    private static final int colliderHeight = 256;
    private static final int SPEED = 4;


    /**
     * 建構子 預設士兵數值為2 ,等級為1
     */
    public ArmySoldier(int x, int y, int level){

        super(x,y, painterWidth, painterHeight, colliderWidth, colliderHeight, 2 + 3 * level + 50, SPEED, new Path().img().actors().Actor2(), FLY_ABILITY.CANNOT_FLY, SOLDIER_TYPE.ARMY);
        setMaxHp(400);
        setBuildingOriginalX(Global.SUM_OF_CAMERA_MOVE_VX);
        setBuildingOriginalY(Global.SUM_OF_CAMERA_MOVE_VY);
        // 預設人物出生方向朝下
        setWalkingDir(Global.Direction.DOWN);
    }


    @Override
    public void stop() {
        //先給你停止動作, 額外部分你再擴充
        setTarget(painter().centerX(), painter().centerY());
        setMoveStatus(Animator.State.STAND);
    }
}

