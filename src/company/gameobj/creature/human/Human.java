package company.gameobj.creature.human;

import company.Global;
import company.gameobj.creature.Creature;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.HumanAnimator;

public abstract class Human extends Creature {

    public enum HUMAN_TYPE {
        CITIZEN, SOLDIER;
    }

    private int characterType;

    private Animator.State state;

    private HumanAnimator animator;

    private HUMAN_TYPE humanType;

    private int value;

    private int upgradePoint;



    public Human(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, int speed, String img, FLY_ABILITY flyAbility, Animator.State state) {
        super(x, y, x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, value, speed, img, flyAbility, Animator.State.STAND);
    }

    public Human(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, int speed, String img, FLY_ABILITY flyAbility, HUMAN_TYPE humanType,Animator.State state) {
        super(x, y, x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, value, speed, img, flyAbility, Animator.State.STAND);
        this.humanType = humanType;
    }




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

    public void setHumanType(HUMAN_TYPE humanType) {
        this.humanType = humanType;
    }

    public boolean isCitizen() {
        return (this.humanType == HUMAN_TYPE.CITIZEN);
    }

    public boolean isSoldier() {
        return (this.humanType == HUMAN_TYPE.SOLDIER);
    }

    public boolean isAlive() {
        return (this.value > 0);
    }

    public void getAttacked(int value) {
        this.setValue(this.getValue() - value);
    }

    @Override
    public void walk() {

        //quit walk if the human is not moving
        if (this.getMoveStatus() == Animator.State.STAND) {
            return;
        }


        int speed = speed();
        //沒有任何方向是被擋住的時候
        if(this.getBlockedDir()==null){
            if (targetX() == painter().centerX()){

                this.setWalkingDir((targetY() > painter().centerY()) ? Global.Direction.DOWN : Global.Direction.UP);
            } else if (targetY() == painter().centerY()) {

                this.setWalkingDir((targetX() > painter().centerX()) ? Global.Direction.RIGHT : Global.Direction.LEFT);
            }

            if(this.getWalkingDir()== Global.Direction.LEFT || this.getWalkingDir()== Global.Direction.RIGHT){
                speed = Math.min(speed, Math.abs(targetX() - painter().centerX()));
            }
            else if(this.getWalkingDir()== Global.Direction.UP || this.getWalkingDir()== Global.Direction.DOWN){
                speed = Math.min(speed, Math.abs(targetY() - painter().centerY()));
            }
        }

        //如果有被擋住某個方向的時候
        else if(this.getBlockedDir()!=null){
            if(this.getWalkingDir()==this.getBlockedDir()){
                switch (this.getBlockedDir()){
                    case LEFT:
                    case RIGHT:{
                        //當在往左或往右的時候撞到障礙物且已經來到目標的Ｙ做標時隨機選擇一個方向去走
                        if(this.painter().centerY()==targetY()){
                            this.setWalkingDir((Global.random(0,1)==0) ? Global.Direction.UP : Global.Direction.DOWN);
                        }
                        //當在往左或往右的時候撞到障礙物且尚未來到目標的Ｙ做標時選擇會靠近目標的方向去走
                        else{
                            this.setWalkingDir((targetY()>this.painter().centerY()) ? Global.Direction.UP : Global.Direction.DOWN);

                        }
                        break;
                    }
                    case UP:
                    case DOWN: {
                        // 當在往上或往下的時候撞到障礙物且已經來到目標的Ｘ做標時隨機選擇一個方向去走
                        if (this.painter().centerX() == this.painter().centerX()) {

                            this.setWalkingDir((Global.random(0, 1) == 0) ? Global.Direction.LEFT : Global.Direction.RIGHT);
                        }
                        // 當在往上或往下的時候撞到障礙物且尚未來到目標的Ｘ做標時選擇會靠近目標的方向去走
                        else {

                            this.setWalkingDir((targetX() < this.painter().centerX()) ? Global.Direction.LEFT : Global.Direction.RIGHT);
                        }
                        break;
                    }
                }
            }
        }


        switch (this.getWalkingDir()){
            case LEFT:{
                this.translateX(-1*speed);
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
        if (this.isAtTarget()) {
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
        if (this.isAt(x, y)) {
            return;
        } else {
            this.setMoveStatus(Animator.State.WALK);
            this.setTargetXY(x, y);
        }
    }

    /**
     * 停止移動 目的地 直接 =  現在位置(就是直接讓他到目的地的意思)
     */
    public void stop() {
        setTarget(painter().centerX(), painter().centerY());
        setMoveStatus(Animator.State.STAND);
    }

}

