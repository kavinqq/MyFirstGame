package company.gameobj.creature;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gameobj.creature.enemy.Enemy;
import company.gameobj.creature.human.Human;
import company.gametest9th.utils.*;

import java.awt.*;
import java.util.ArrayList;

public abstract class Creature extends GameObject {

    public enum FIGHTING_STATUS {
        FIGHTING, NOT_FIGHTING;
    }

    public enum FLY_ABILITY {
        CAN_FLY, CANNOT_FLY;
    }

    /**
     * 生物 的初始數值(也就是攻擊力)
     */

    private int value;
    private double speed;
    private final int defaultSpeed;
    private Image img;
    private FLY_ABILITY flyAbility;
    private FIGHTING_STATUS fightingStatus;
    private Animator.State moveStatus;
    private GameObject attackTarget;
    private int targetX;
    private int targetY;
    private boolean isAbleToGoRight = true;
    private boolean isAbleToGoLeft = true;
    private boolean isAbleToGoUp = true;
    private boolean isAbleToGoDown = true;

    private int hp;
    private int maxHp;


    private Global.Direction walkingDir;
    private Global.Direction blockedDir;

    private int sumOfCameraMoveX; // 鏡頭移動量X
    private int sumOfCameraMoveY; // 鏡頭移動量Y

    private Animator animator;// 人物動畫物件
    private GameObject collidingObject;

    private Delay attackDelay;
    private int attackDelaySpeed;

    public Creature(int x, int y, int targetX, int targetY, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, int speed, String img, FLY_ABILITY flyAbility, Animator.State moveStatus) {

        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight);

        this.targetX = targetX;
        this.targetY = targetY;

        this.value = value;

        this.speed = speed;
        this.defaultSpeed = speed;

        this.img = SceneController.getInstance().imageController().tryGetImage(img);

        this.setFlyAbility(flyAbility);

        this.fightingStatus = FIGHTING_STATUS.NOT_FIGHTING;

        this.moveStatus = moveStatus;

        // 原本的位置


        // 若無初始目的地的話預設人物出生方向朝下
        if (this.isAtTarget()) {
            this.setWalkingDir(Global.Direction.DOWN);
        }
        //不然則依據其目的地所在方向進行隨機
        else {
            ArrayList<Global.Direction> availableInitialDirs = new ArrayList<>();
            if (x > targetX) {
                availableInitialDirs.add(Global.Direction.LEFT);
            }
            if (x < targetX) {
                availableInitialDirs.add(Global.Direction.RIGHT);
            }
            if (y > targetY) {
                availableInitialDirs.add(Global.Direction.UP);
            }
            if (y < targetY) {
                availableInitialDirs.add(Global.Direction.DOWN);
            }
            this.setWalkingDir(availableInitialDirs.get(Global.random(0, availableInitialDirs.size() - 1)));
        }

        this.attackDelay = new Delay(attackDelaySpeed);
        attackDelay.loop();
    }


    protected void setMaxHp(int hp) {
        this.maxHp=hp;
        this.hp = maxHp;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public int getHp() {
        return hp;
    }


    /**
     * 獲取該人物物件的數值 (也就是每個人物的攻擊力)
     *
     * @return 每個人物的攻擊力
     */

    public int getValue() {      //獲取目前數值
        //做冷卻時間
//        if (attackDelay.count()) {
            return value;
//        }
//        return 0;
    }

    public void getAttacked(int value) {
        this.hp -= value;
    }

    public void setFlyAbility(FLY_ABILITY flyAbility) {
        this.flyAbility = flyAbility;
    }

    public void setFightingStatus(FIGHTING_STATUS fightingStatus) {
        this.fightingStatus = fightingStatus;
    }

    public FIGHTING_STATUS getFightingStatus() {
        return fightingStatus;
    }

    public boolean isAlive() {
        return (getHp() > 0);
    }


    public boolean isFlyable() {
        return (this.flyAbility == FLY_ABILITY.CAN_FLY);
    }


    public boolean isFighting() {
        return (this.fightingStatus == FIGHTING_STATUS.FIGHTING);
    }


    public void setAttackTarget(GameObject attackTarget) {
        this.attackTarget = attackTarget;

        ArrayList<Global.Direction> availableInitialDirs = new ArrayList<>();

        if (attackTarget.painter().centerX() > targetX) {
            availableInitialDirs.add(Global.Direction.RIGHT);
        }
        if (attackTarget.painter().centerX() < targetX) {
            availableInitialDirs.add(Global.Direction.LEFT);
        }
        if (attackTarget.painter().centerY() > targetY) {
            availableInitialDirs.add(Global.Direction.DOWN);
        }
        if (attackTarget.painter().centerY() < targetY) {
            availableInitialDirs.add(Global.Direction.UP);
        }
        if(!availableInitialDirs.isEmpty()){
            this.setWalkingDir(availableInitialDirs.get(Global.random(0, availableInitialDirs.size() - 1)));
            this.setMoveStatus(Animator.State.WALK);
        }

    }

    public void setAttackTargetToNull() {
        this.attackTarget = null;
        if (!this.isAtTarget()) {
            this.setMoveStatus(Animator.State.WALK);
        }
    }


    public GameObject getAttackTarget() {
        return attackTarget;
    }

    public boolean isHavingNoTarget() {
        return (this.attackTarget == null);
    }


    /**
     * 回傳這個生物的圖片
     *
     * @return 該生物的圖片
     */

    public Image getImg() {
        return this.img;
    }

    /**
     * 設定行走狀態
     *
     * @param walkingStatus 新的行走狀態
     */

    public void setMoveStatus(Animator.State walkingStatus) {
        this.moveStatus = walkingStatus;
        this.getAnimator().setState(walkingStatus);
    }

    /**
     * 回傳該生物目前的行走狀態 (站立 or 行走)
     *
     * @return 行走狀況
     */

    public Animator.State getMoveStatus() {
        return moveStatus;
    }

    /**
     * 設定目的地 x, y
     *
     * @param x 目的地X
     * @param y 目的地Y
     */

    public void setTargetXY(int x, int y) {
        if (!this.isAt(x, y)) {
            setTargetX(x);
            setTargetY(y);
            if (x != painter().centerX() && y != painter().centerY()) {
                Global.Direction[] arr = new Global.Direction[2];
                arr[0] = (targetX() > painter().centerX()) ? Global.Direction.RIGHT : Global.Direction.LEFT;
                arr[1] = (targetY() > painter().centerY()) ? Global.Direction.DOWN : Global.Direction.UP;
                this.setWalkingDir(arr[Global.random(0, 1)]);
            }
        }
    }

    /**
     * 單純設定 目的地X
     *
     * @param targetX 目的地X
     */

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }


    /**
     * 單純設定目的地X
     *
     * @param targetY 目的地X
     */

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }


    /**
     * 取得目的地X
     *
     * @return 目的地X
     */

    public int targetX() {
        return targetX;
    }

    /**
     * 取得目的地Y
     *
     * @return 目的地Y
     */

    public int targetY() {
        return targetY;
    }

    public boolean isSeeing(GameObject gameObject) {
        return this.detectRange().overlap(gameObject.painter());
    }

    /**
     * 每個生物的行走 (各自實現)
     */


    public void walk() {

        //quit walk if the human is not moving
        if (this.getMoveStatus() == Animator.State.STAND) {
            return;
        }
//        if(this instanceof Human){
//            System.out.println("human");
//        }
        int speed = (int) speed();

        if (this.getAttackTarget() != null) {
//            if(this instanceof Human){
//                System.out.println("Walk not null");
//            }

            if (getAttackTarget().painter().centerX() == painter().centerX()) {
                this.setWalkingDir((getAttackTarget().painter().centerY() > painter().centerY()) ? Global.Direction.DOWN : Global.Direction.UP);
            } else if (getAttackTarget().painter().centerY() == painter().centerY()) {
                this.setWalkingDir((getAttackTarget().painter().centerX() > painter().centerX()) ? Global.Direction.RIGHT : Global.Direction.LEFT);
            } else {
                switch (this.getWalkingDir()) {
                    case LEFT: {
                        if (this.getAttackTarget().painter().centerX() > this.painter().centerX()) {
                            this.setWalkingDir((this.getAttackTarget().painter().centerY() < this.painter().centerY()) ? Global.Direction.UP : Global.Direction.DOWN);
                        }
                        break;
                    }
                    case RIGHT: {
                        if (this.getAttackTarget().painter().centerX() < this.painter().centerX()) {
                            this.setWalkingDir((this.getAttackTarget().painter().centerY() < this.painter().centerY()) ? Global.Direction.UP : Global.Direction.DOWN);
                        }
                        break;
                    }
                    case UP: {
                        if (this.getAttackTarget().painter().centerY() > this.painter().centerY()) {
                            this.setWalkingDir((this.getAttackTarget().painter().centerX() < this.painter().centerX()) ? Global.Direction.LEFT : Global.Direction.RIGHT);
                        }
                        break;
                    }
                    case DOWN: {
                        if (this.getAttackTarget().painter().centerY() < this.painter().centerY()) {
                            this.setWalkingDir((this.getAttackTarget().painter().centerX() < this.painter().centerX()) ? Global.Direction.LEFT : Global.Direction.RIGHT);
                        }
                        break;
                    }
                }
            }
            if (this.getWalkingDir() == Global.Direction.LEFT || this.getWalkingDir() == Global.Direction.RIGHT) {
                speed = Math.min(speed, Math.abs(getAttackTarget().painter().centerX() - painter().centerX()));
            } else if (this.getWalkingDir() == Global.Direction.UP || this.getWalkingDir() == Global.Direction.DOWN) {
                speed = Math.min(speed, Math.abs(getAttackTarget().painter().centerX() - painter().centerY()));
            }
        } else {
//            if(this instanceof Human) {
//                System.out.println("Walk null");
//            }
            //System.out.println("BBBB");
            if (this.isAtTarget()) {
                this.setMoveStatus(Animator.State.STAND);
                return;
            }

            //沒有任何方向是被擋住的時候
            if (this.getBlockedDir() == null) {
                //System.out.println("Walk: no blocked direction");

                if (targetX() == painter().centerX()) {
                    this.setWalkingDir((targetY() > painter().centerY()) ? Global.Direction.DOWN : Global.Direction.UP);
                } else if (targetY() == painter().centerY()) {
                    this.setWalkingDir((targetX() > painter().centerX()) ? Global.Direction.RIGHT : Global.Direction.LEFT);
                } else {//for 剛離開障礙物的 case
                    switch (this.getWalkingDir()) {
                        case LEFT: {
                            if (targetX() > this.painter().centerX()) {
                                this.setWalkingDir((this.targetY() < this.painter().centerY()) ? Global.Direction.UP : Global.Direction.DOWN);
                            }
                            break;
                        }
                        case RIGHT: {
                            if (targetX() < this.painter().centerX()) {
                                this.setWalkingDir((this.targetY() < this.painter().centerY()) ? Global.Direction.UP : Global.Direction.DOWN);
                            }
                            break;
                        }
                        case UP: {
                            if (targetY() > this.painter().centerY()) {
                                this.setWalkingDir((this.targetX() < this.painter().centerX()) ? Global.Direction.LEFT : Global.Direction.RIGHT);
                            }
                            break;
                        }
                        case DOWN: {
                            if (targetY() < this.painter().centerY()) {
                                this.setWalkingDir((this.targetX() < this.painter().centerX()) ? Global.Direction.LEFT : Global.Direction.RIGHT);
                            }
                            break;
                        }
                    }
                }
                if (this.getWalkingDir() == Global.Direction.LEFT || this.getWalkingDir() == Global.Direction.RIGHT) {
                    speed = Math.min(speed, Math.abs(targetX() - painter().centerX()));
                } else if (this.getWalkingDir() == Global.Direction.UP || this.getWalkingDir() == Global.Direction.DOWN) {
                    speed = Math.min(speed, Math.abs(targetY() - painter().centerY()));
                }
            } else if (this.getBlockedDir() != null) {
                switch (this.getBlockedDir()) {
                    case LEFT:
                    case RIGHT:
                        //第一次撞到的時候
                        if (this.getWalkingDir() == this.getBlockedDir()) {
                            //當在往左或往右的時候撞到障礙物且已經來到目標的Ｙ做標時隨機選擇一個方向去走
                            if (this.painter().centerY() == targetY()) {
                                this.setWalkingDir((Global.random(0, 1) == 0) ? Global.Direction.UP : Global.Direction.DOWN);
                            }
                            //當在往左或往右的時候撞到障礙物且尚未來到目標的Ｙ做標時選擇會靠近目標的方向去走
                            else {
                                this.setWalkingDir((targetY() < this.painter().centerY()) ? Global.Direction.UP : Global.Direction.DOWN);
                            }
                        } else if (this.getBlockedDir() != this.getWalkingDir()) {
                            //因為 blocked direction != walking direction && 是有被擋住的, 所以人物一定是在沿著邊或是與被阻擋的相反方向走的
                            //如果目標跟人物在障礙物的同一側的話
                            if ((this.getBlockedDir() == Global.Direction.LEFT && targetX() >= this.painter().centerX()) || (this.getBlockedDir() == Global.Direction.RIGHT && targetX() <= this.painter().centerX())) {

                                if (targetX() == painter().centerX()) {
                                    this.setWalkingDir((targetY() > painter().centerY()) ? Global.Direction.DOWN : Global.Direction.UP);
                                } else if (targetY() == painter().centerY()) {
                                    this.setWalkingDir((targetX() > painter().centerX()) ? Global.Direction.RIGHT : Global.Direction.LEFT);
                                }

                                if (this.getWalkingDir() == Global.Direction.LEFT || this.getWalkingDir() == Global.Direction.RIGHT) {
                                    speed = Math.min(speed, Math.abs(targetX() - painter().centerX()));
                                } else if (this.getWalkingDir() == Global.Direction.UP || this.getWalkingDir() == Global.Direction.DOWN) {
                                    speed = Math.min(speed, Math.abs(targetY() - painter().centerY()));
                                }
                            }
                        }
                        break;
                    case UP:
                    case DOWN: {
                        //第一次撞到的時候
                        if (this.getWalkingDir() == this.getBlockedDir()) {
                            // 當在往上或往下的時候撞到障礙物且已經來到目標的Ｘ做標時隨機選擇一個方向去走
                            if (this.painter().centerX() == this.painter().centerX()) {
                                this.setWalkingDir((Global.random(0, 1) == 0) ? Global.Direction.LEFT : Global.Direction.RIGHT);
                            }
                            // 當在往上或往下的時候撞到障礙物且尚未來到目標的Ｘ做標時選擇會靠近目標的方向去走
                            else {
                                this.setWalkingDir((targetX() < this.painter().centerX()) ? Global.Direction.LEFT : Global.Direction.RIGHT);
                            }
                        } else if (this.getWalkingDir() != this.getBlockedDir()) {
                            //因為 blocked direction != walking direction && 是有被擋住的, 所以人物一定是在沿著邊或是與被阻擋的相反方向走的
                            //如果目標跟人物在障礙物的同一側的話
                            if ((this.getBlockedDir() == Global.Direction.UP && targetY() >= this.painter().centerY()) || (this.getBlockedDir() == Global.Direction.DOWN && targetY() <= this.painter().centerY())) {

                                if (targetX() == painter().centerX()) {
                                    this.setWalkingDir((targetY() > painter().centerY()) ? Global.Direction.DOWN : Global.Direction.UP);
                                } else if (targetY() == painter().centerY()) {
                                    this.setWalkingDir((targetX() > painter().centerX()) ? Global.Direction.RIGHT : Global.Direction.LEFT);
                                }

                                if (this.getWalkingDir() == Global.Direction.LEFT || this.getWalkingDir() == Global.Direction.RIGHT) {
                                    speed = Math.min(speed, Math.abs(targetX() - painter().centerX()));
                                } else if (this.getWalkingDir() == Global.Direction.UP || this.getWalkingDir() == Global.Direction.DOWN) {
                                    speed = Math.min(speed, Math.abs(targetY() - painter().centerY()));
                                }
                            }
                        }
                        break;
                    }
                }

                //System.out.println("-------------------------");
            }
        }
        switch (this.getWalkingDir()) {
            case LEFT: {
                this.translateX(-1 * speed);
                break;
            }
            case RIGHT: {
                this.translateX(speed);
                break;
            }
            case UP: {
                this.translateY(-1 * speed);
                break;
            }
            case DOWN: {
                this.translateY(speed);
                break;
            }
        }
        if (this.getAttackTarget() == null && this.isAtTarget()) {
            this.stop();
        }
    }


    /**
     * 每個生物的移動速度(每一幀)
     *
     * @return 移動速度
     */

    public double speed() {
        return speed;
    }

    public boolean isAbleToGoRight() {
        return isAbleToGoRight;
    }

    public boolean isAbleToGoLeft() {
        return isAbleToGoLeft;
    }

    public boolean isAbleToGoUp() {
        return isAbleToGoUp;
    }

    public boolean isAbleToGoDown() {
        return isAbleToGoDown;
    }

    public void setAbleToGoRight(boolean ableToGoRight) {
        isAbleToGoRight = ableToGoRight;
    }

    public void setAbleToGoLeft(boolean ableToGoLeft) {
        isAbleToGoLeft = ableToGoLeft;
    }

    public void setAbleToGoUp(boolean ableToGoUp) {
        isAbleToGoUp = ableToGoUp;
    }

    public void setAbleToGoDown(boolean ableToGoDown) {
        isAbleToGoDown = ableToGoDown;
    }


    public void setWalkingDir(Global.Direction walkingDir) {
        this.walkingDir = walkingDir;
    }

    public Global.Direction getWalkingDir() {
        return walkingDir;
    }

    public void setBlockedDir(Global.Direction blockedDir) {
        this.blockedDir = blockedDir;
    }

    public void setNoBlockedDir() {
        this.blockedDir = null;
    }

    public Global.Direction getBlockedDir() {
        return blockedDir;
    }

    public boolean isAt(int x, int y) {
        return (this.painter().centerX() == x && this.painter().centerY() == y);
    }

    public boolean isAtTarget() {
        return (this.painter().centerX() == targetX && this.painter().centerY() == targetY);
    }

    // 目的地是否在該gameObj裡面
    public boolean isTargetInObj(GameObject gameObject) {

        return targetX > gameObject.painter().left() && targetX < gameObject.painter().right() && targetY < gameObject.painter().bottom() && targetY > gameObject.painter().top();

    }

    /**
     * 鏡頭移動
     */

    @Override
    public void cameraMove() {

        // 本體跟著鏡頭動
        translate(Global.CAMERA_MOVE_VX, Global.CAMERA_MOVE_VY);

        // 目標也跟著動
        setTargetX(targetX + Global.CAMERA_MOVE_VX);
        setTargetY(targetY + Global.CAMERA_MOVE_VY);
    }

    /**
     * 重製視窗回初始位置
     */

    @Override
    public void resetObjectXY() {

        // 本體跟著鏡頭動
        translate(Global.SUM_OF_CAMERA_MOVE_VX * -1, Global.SUM_OF_CAMERA_MOVE_VY * -1);

        // 目標也跟著動
        setTargetX(targetX - Global.SUM_OF_CAMERA_MOVE_VX);
        setTargetY(targetY - Global.SUM_OF_CAMERA_MOVE_VY);
    }


    /**
     * 回傳生物的animator
     *
     * @return
     */
    public Animator getAnimator() {
        return animator;
    }

    /**
     * 將生物的animator設定成指定的animator
     *
     * @param animator
     */
    public void setAnimator(Animator animator) {
        this.animator = animator;
    }

    public void stop() {
        setTargetXY(painter().centerX(), painter().centerY());
        setMoveStatus(Animator.State.STAND);
    }

    public void brake() {
        this.speed = Math.max(this.speed - 0.1, 0);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setSpeedToDefault() {
        this.speed = defaultSpeed;
    }

    public void setCollidingObject(GameObject collidingObject) {
        this.collidingObject = collidingObject;
    }

    public GameObject getCollidingObject() {
        return collidingObject;
    }

    public Delay getDelay() {
        return attackDelay;
    }

    public int setDelaySpeed() {
        return attackDelaySpeed;
    }

    public void detect(Creature creature){
        if(creature.isAlive() && this.detectRange().overlap(creature.painter())){
            getDelay().firstNoDelay();
            this.setAttackTarget(creature);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        //畫出動畫
        getAnimator().paint(getWalkingDir(), painter().left(), painter().top(), painter().right(), painter().bottom(), g);
        g.setColor(Color.red);
        g.fillRect(painter().left(),painter().bottom(),painter().width(),Global.HP_HEIGHT);
        g.setColor(Color.green);
        g.fillRect(painter().left(),painter().bottom(),painter().width()*hp/maxHp,Global.HP_HEIGHT);
        if (this.getFightEffect() != null) {
            this.getFightEffect().paintComponent(g);
        }
    }


}
