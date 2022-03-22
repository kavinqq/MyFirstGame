package company.gameObj.creature.human;

import company.gametest9th.utils.Delay;
import company.gametest9th.utils.Path;

import java.awt.*;

public class Citizen extends Human {   //市民
    private WORK_STATUS workStatus;
    private static final int CITIZEN_INITIAL_VALUE = 1;
    private static final int painterWidth = 50;
    private static final int painterHeight = 50;
    private static final int colliderWidth = 50;
    private static final int colliderHeight = 50;
    private Delay toolDelay;

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(getImg(), this.painter().left(), this.painter().top(), null);
    }

    @Override
    public void update() {
        //TODO
        if (workStatus == WORK_STATUS.FREE) {
            //randomWalk();
        } else {
            if (toolDelay.count()) {
                //wave tool();
            }
        }
    }

    public enum WORK_STATUS {
        FREE, WOOD, STEEL;
    }

    /**
     * 建構子 預設市民數值為1 , 不能打架 , 初始設定為閒人
     */
    public Citizen(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, CITIZEN_INITIAL_VALUE, new Path().img().humans().citizen(), FLY_ABILITY.CANNOT_FLY, HUMAN_TYPE.CITIZEN);
        toolDelay = new Delay(30);
    }

    /**
     * 派遣村民去採木頭
     */
    public void startToLog() {
        workStatus = WORK_STATUS.WOOD;
        this.toolDelay.loop();
    }

    /**
     * 派遣村民去採鐵
     */
    public void startToMine() {
        workStatus = WORK_STATUS.STEEL;
        this.toolDelay.loop();
    }

    /**
     * 設定 村民 = 閒人
     */
    public void setStateToFree() {
        workStatus = WORK_STATUS.FREE;
    }

    /**
     * 取得狀態
     *
     * @return 該人物的狀態
     */
    public WORK_STATUS getWorkStatus() {
        return workStatus;
    }

    public boolean isFree() {
        return (this.workStatus == WORK_STATUS.FREE);
    }
}
