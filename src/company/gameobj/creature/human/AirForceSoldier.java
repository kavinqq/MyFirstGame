package company.gameobj.creature.human;

import company.Global;
import company.gametest9th.utils.Animator;
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
    public static final int colliderWidth = 256;
    public static final int colliderHeight = 256;
    private static final int SPEED = 10;

    public AirForceSoldier(int x, int y, int level){

        super(x,y, painterWidth, painterHeight, colliderWidth, colliderHeight, 2 + 2*level, SPEED, new Path().img().actors().Actor2(),FLY_ABILITY.CAN_FLY, SOLDIER_TYPE.AIR_FORCE);
        setMaxHp(900);
        setBuildingOriginalX(Global.SUM_OF_CAMERA_MOVE_VX);
        setBuildingOriginalY(Global.SUM_OF_CAMERA_MOVE_VY);
        // 預設人物出生方向朝下
        setWalkingDir(Global.Direction.DOWN);
    }

//    @Override
//    public void paintComponent(Graphics g) {
//        //畫出動畫
//        getAnimator().paint(getWalkingDir(), painter().left(), painter().top(), painter().right(), painter().bottom(), g);
//    }


    @Override
    public void stop() {

        //先給你停止動作, 額外部分你再擴充
        setTarget(painter().centerX(), painter().centerY());
        setMoveStatus(Animator.State.STAND);
    }
}
