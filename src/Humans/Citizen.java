package Humans;

public class Citizen extends Human {   //市民
    /**
     * 建構子 預設市民數值為1 , 不能打架 , 初始設定為閒人
     */
    public Citizen(){
        super(1, false);  //初始值為1
        setStateToFree(); //建立時 同時設定狀態為 Free
    }
}
