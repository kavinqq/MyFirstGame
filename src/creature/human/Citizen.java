package creature.human;

import company.gametest9th.utils.Path;

public class Citizen extends Human {   //市民
    private STATUS status;
    private int value;
    public enum STATUS {
        FREE, WOOD, STEEL;
    }
    /**
     * 建構子 預設市民數值為1 , 不能打架 , 初始設定為閒人
     */
    public Citizen(){
        super(1, new Path().img().humans().citizen(), FLY_ABILITY.CANNOT_FLY, HUMAN_TYPE.CITIZEN);
    }
    /**
     * 派遣村民去採木頭
     */
    public void startToLog(){
        status = STATUS.WOOD;
    }

    /**
     * 派遣村民去採鐵
     */
    public void startToMine(){
        status = STATUS.STEEL;
    }

    /**
     * 設定 村民 = 閒人
     */
    public void setStateToFree(){
        status = STATUS.FREE;
    }

    /**
     * 取得狀態
     * @return 該人物的狀態
     */
    public STATUS getStatus(){
        return status;
    }

    public boolean isFree(){
        return (this.status==STATUS.FREE);
    }
}
