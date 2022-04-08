package company.gameobj.creature.human;

import company.gameobj.GameObject;
import company.gameobj.creature.Creature;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.HumanAnimator;

public abstract class Human extends Creature {

    /**
     * 人物種類 (村民 or 士兵)
     */
    public enum HUMAN_TYPE {
        CITIZEN, SOLDIER;
    }

    private int characterType; // 用來選擇該角色的圖片

    private Animator.State animatorState;// 動畫目前的狀態

    private HUMAN_TYPE humanType;

    private int upgradePoint;

    private GameObject blockingObject;



    public Human(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, int speed, String img, FLY_ABILITY flyAbility) {

        super(x, y, x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, value, speed, img, flyAbility, Animator.State.STAND);

        // 預設出生後站立
        this.animatorState = Animator.State.STAND;

        // 依照能否飛行 選擇 圖片
        if(flyAbility == FLY_ABILITY.CAN_FLY){
            this.characterType = 5;
        } else {
            this.characterType = 2;
        }

        // 創建動畫物件
        this.setAnimator(new HumanAnimator(characterType, animatorState));

    }

    public Human(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, int speed, String img, FLY_ABILITY flyAbility, HUMAN_TYPE humanType, int characterType) {

        // 呼叫Creature建構子
        super(x, y, x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, value, speed, img, flyAbility, Animator.State.STAND);

        this.humanType = humanType;

        // 剛出生都是站著不動
        animatorState = Animator.State.STAND;

        // 該人物要選哪張圖片
        this.characterType = characterType;

        // 創建動畫物件
        this.setAnimator(new HumanAnimator(characterType, animatorState));
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

    public int getCharacterType() {
        return characterType;
    }

    public void setCharacterType(int type) {
        characterType = type;
    }

    /**
     * 設定目的地 X Y
     * @param x 目的地X座標
     * @param y 目的地Y座標
     */
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

    public GameObject getBlockingObject() {
        return blockingObject;
    }

    public void setBlockingObject(GameObject blockingObject) {
        this.blockingObject = blockingObject;
    }
}

