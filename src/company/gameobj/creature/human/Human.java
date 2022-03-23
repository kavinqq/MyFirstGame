package company.gameobj.creature.human;

import company.Global;
import company.gameobj.creature.Creature;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.HumanAnimator;

public abstract class Human extends Creature {

    private int characterType;
    private Animator.State state;
    private boolean canMove;
    private boolean hasMove;

    private HumanAnimator animator;


    public Human(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, int speed, String img, FLY_ABILITY flyAbility, HUMAN_TYPE humanType) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, value, speed, img, flyAbility);
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
        if (this.isHavingNoTarget()) {

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
        this.setTargetXY(x, y);

        // 如果該 目的地XY 需要走動才能到達 開啟行走
        if (targetX() != painter().centerX() && targetY() != painter().centerY()) {
            canMove = true;
        }
    }

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
            if (Math.abs(targetX() - painter().centerX()) < speed()) {
                speed = Math.abs(targetX() - painter().centerX());
            }

            // 如果 目的地在角色 右邊 往右走
            if (targetX() > painter().centerX() && !hasMove) {
                setDir(Global.Direction.RIGHT);
                this.translateX(speed);
                hasMove = true;
            }
            // 如果 目的地在角色 左邊 往左走
            if (targetX() < painter().centerX() && !hasMove) {
                setDir(Global.Direction.LEFT);
                this.translateX(-1 * speed);
                hasMove = true;
            }
        }


        // 處理Y
        // 如果當前Y沒走到目的地Y
        if (targetY() != painter().centerY()) {

            // 如果剩下的距離 < 一步 那麼 一步距離 = 剩下的距離
            if (Math.abs(targetY() - painter().centerY()) < speed()) {
                speed = Math.abs(targetY() - painter().centerY());
            }

            // 如果 目的地在角色 下面 往下走
            if (targetY() > painter().centerY() && !hasMove) {
                setDir(Global.Direction.DOWN);
                this.translateY(speed);
                hasMove = true;
            }

            // 如果 目的地在角色 上面 往上走
            if (targetY() < painter().centerY() && !hasMove) {
                setDir(Global.Direction.UP);
                this.translateY(-1 * speed);
            }
        }

        // 走到了目的地 把能移動關起來
        if (targetX() == painter().centerX() && targetY() == painter().centerY()) {
            animator.setState(Animator.State.STAND);
            canMove = false;
        }
    }
}

