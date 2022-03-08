package Creature.human;

public class Citizen extends Human {   //市民
    /**
     * 建構子 預設市民數值為1 , 不能打架 , 初始設定為閒人
     */
    public enum STATUS {
        FREE, WOOD, STEEL;
    }
    private STATUS status;
    public Citizen(){
        this.setType(TYPE.CITIZEN);
        this.status = STATUS.FREE; //建立時 同時設定狀態為 Free
        this.setFlyability(FLYABILITY.CANNOT_FLY);
    }
    /**
     * 派遣村民去採木頭
     */
    public void setStateToWood(){
        status = STATUS.WOOD;
    }

    /**
     * 派遣村民去採鐵
     */
    public void setStateToSteel(){
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
    public STATUS getState(){
        return status;
    }
}
