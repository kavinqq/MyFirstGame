package company.gameobj.creature.human;

import company.Global;
import company.gameobj.creature.Creature;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.HumanAnimator;

public abstract class Human extends Creature {

    private int characterType;
    private Animator.State state;
//    private boolean canMove;
//    private boolean hasMove;

    private HumanAnimator animator;


    public Human(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, int speed, String img, FLY_ABILITY flyAbility, HUMAN_TYPE humanType) {
        super(x, y, x, y,painterWidth, painterHeight, colliderWidth, colliderHeight, value, speed, img, flyAbility, Animator.State.STAND);
        this.setType(humanType);
    }

    public enum HUMAN_TYPE {
        CITIZEN, SOLDIER;
    }

    private HUMAN_TYPE type;

    /**
     * 人物 的初始數值(也就是攻擊力)
     */
    private int value;
    /**
     *
     */
    private int upgradePoint;

    /**
     * 該人物 的初始數值(也就是攻擊力)
     * @param value 攻擊數值
     * @param type 讓各個subclass決定
     */

    /**
     * 升級該物件的等級 (每次升級 等級 + 1 && 數值 + 3)
     */


    /**
     * 獲取該人物物件的數值 (也就是每個人物的攻擊力)
     *
     * @return 每個人物的攻擊力
     */
    public int getValue() {      //獲取目前數值
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setType(HUMAN_TYPE type) {
        this.type = type;
    }

    public boolean isCitizen() {
        return (this.type == HUMAN_TYPE.CITIZEN);
    }

    public boolean isSoldier() {
        return (this.type == HUMAN_TYPE.SOLDIER);
    }

    public boolean isAlive() {
        return (this.value > 0);
    }

    public void getAttacked(int value) {
        this.setValue(this.getValue() - value);
    }

    @Override
    public void walk() {
        //        if (this.isHavingNoTarget()) {
//
//        }

        //quit walk if the human is not moving
        if(this.getMoveStatus() == Animator.State.STAND){
            return;
        }
        //is not blocked by any buildings
        if(this.getBlockedDir()==null){
            if (targetX() == painter().centerX()){
                this.setWalkingDir((targetY() > painter().centerY()) ? Global.Direction.DOWN : Global.Direction.UP);
            }
            else if (targetY() == painter().centerY()){
                this.setWalkingDir((targetX() > painter().centerX()) ? Global.Direction.RIGHT: Global.Direction.LEFT);
            }
        }
        else if(this.getBlockedDir()!=null){
            if(this.getWalkingDir()==this.getBlockedDir()){
                switch (this.getBlockedDir()){
                    case LEFT:
                    case RIGHT:{
                        //當在往上或往下的時候撞到障礙物且已經來到目標的Ｘ做標時隨機選擇一個方向去走
                        if(this.painter().centerY()==targetY()){
                            this.setWalkingDir((Global.random(0,1)==0) ? Global.Direction.UP : Global.Direction.DOWN);
                        }
                        //當在往上或往下的時候撞到障礙物且尚未來到目標的Ｘ做標時選擇會靠近目標的方向去走
                        else{
                            this.setWalkingDir((targetY()>this.painter().centerY()) ? Global.Direction.UP : Global.Direction.DOWN);
                        }
                        break;
                    }
                    case UP:
                    case DOWN:{
                        //當在往上或往下的時候撞到障礙物且已經來到目標的Ｘ做標時隨機選擇一個方向去走
                        if(this.painter().centerX()==this.painter().centerX()){
                            this.setWalkingDir((Global.random(0,1)==0) ? Global.Direction.LEFT : Global.Direction.RIGHT);
                        }
                        //當在往上或往下的時候撞到障礙物且尚未來到目標的Ｘ做標時選擇會靠近目標的方向去走
                        else{
                            this.setWalkingDir((targetX()<this.painter().centerX()) ? Global.Direction.LEFT : Global.Direction.RIGHT);
                        }
                        break;
                    }
                }
            }
        }
        int speed = speed();
        if(this.getBlockedDir()==null){
            if(this.getWalkingDir()== Global.Direction.LEFT || this.getWalkingDir()== Global.Direction.RIGHT){
                speed = Math.min(speed, Math.abs(targetX() - painter().centerX()));
            }
            else if(this.getWalkingDir()== Global.Direction.UP || this.getWalkingDir()== Global.Direction.DOWN){
                speed = Math.min(speed, Math.abs(targetY() - painter().centerY()));
            }
        }
        switch (this.getWalkingDir()){
            case LEFT:{
                this.translateX(-1*speed);
                break;
            }
            case RIGHT:{
                this.translateX(speed);
                break;
            }
            case UP:{
                this.translateY(-1*speed);
                break;
            }
            case DOWN:{
                this.translateY(speed);
                break;
            }
        }
        if(this.isAtTarget()){
            this.setMoveStatus(Animator.State.STAND);
        }
    }

    public int getCharacterType() {
        return characterType;
    }

    public void setCharacterType(int type) {
        characterType = type;
    }


    public void setTarget(int x, int y) {
        // 設定目的地X Y
        if(this.isAt(x,y)){
            return;
        }
        else{
            this.setMoveStatus(Animator.State.WALK);
            this.setTargetXY(x, y);
        }
    }


    /**
     * 新增方法 stop() => 按下s的同時停止動作
     */
    abstract public void stop();

    /*
    public void mouseToMove() {
        // 如果現在不能移動 那下面都不用跑
        if (!canMove) {
            return;
        }

        // 確定能走了, 把狀態改為walk
        animator.setState(Animator.State.WALK);

        // 這次update 移動過了沒
        hasMove = false;

        // 速度(一步的距離) = 初始速度
        int speed = speed();

        // 處理X
        // 如果當前X還沒走到目的地X
        if (targetX() != painter().centerX()) {
            // 如果剩下的距離 < 一步 那麼 一步距離 = 剩下的距離
            speed = Math.min(speed, Math.abs(targetX() - painter().centerX()));


            // 如果 目的地在角色 右邊 往右走
            if (targetX() > painter().centerX() && !hasMove) {
                setWalkingDir(Global.Direction.RIGHT);
                this.translateX(speed);
                hasMove = true;
            }
            // 如果 目的地在角色 左邊 往左走
            if (targetX() < painter().centerX() && !hasMove) {
                setWalkingDir(Global.Direction.LEFT);
                this.translateX(-1 * speed);
                hasMove = true;
            }
        }


        // 處理Y
        // 如果當前Y沒走到目的地Y
        if (targetY() != painter().centerY()) {

            // 如果剩下的距離 < 一步 那麼 一步距離 = 剩下的距離
            speed = Math.min(speed ,Math.abs(targetY() - painter().centerY()));


            // 如果 目的地在角色 下面 往下走
            if (targetY() > painter().centerY() && !hasMove) {
                setWalkingDir(Global.Direction.DOWN);
                this.translateY(speed);
                hasMove = true;
            }

            // 如果 目的地在角色 上面 往上走
            if (targetY() < painter().centerY() && !hasMove) {
                setWalkingDir(Global.Direction.UP);
                this.translateY(-1 * speed);
            }
        }

        // 走到了目的地 把能移動關起來
        if (targetX() == painter().centerX() && targetY() == painter().centerY()) {
            animator.setState(Animator.State.STAND);
            canMove = false;
        }
    }
     */
}

