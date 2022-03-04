package Humans;

public class Human {
    /**
     * 用來辦別他是不是士兵
     */
    private final boolean isSoldier;
    /**
     * 該人物的狀態
     * 若為市民 有 Wood(採木)  Steel(採鐵)  Free(空閒) 三狀態
     * 若為士兵 則為 Fighter
     */
    private String state="";
    /**
     * 人物 的初始數值(也就是攻擊力)
     */
    private int value;
    /**
     * 該人物 的初始數值(也就是攻擊力)
     * @param value 攻擊數值
     * @param isSolider 他是否是一個士兵
     */
    public Human(int value, boolean isSolider){
        this.value=value;
        this.isSoldier = isSolider;
        //若為士兵  設定士兵狀態為 Fighter
        if(this.isSoldier){
            state = "Fighter";
        }
    }
    /**
     * 拿取isSoldier 判斷 他是否是士兵
     * @return 如果是 士兵 回傳 true 不是 回傳 false
     */
    public boolean getIsSoldier(){
        return isSoldier;
    }
    /**
     * 升級該物件的等級 (每次升級 等級 + 1 && 數值 + 3)
     */
    public void levelUP()
    {
        if(isSoldier){
            value = value + 3;
        }
    }
    /**
     * 獲取該人物物件的數值 (也就是每個人物的攻擊力)
     * @return 每個人物的攻擊力
     */
    public int getValue(){      //獲取目前數值
        return value;
    }

    /**
     * 取得狀態 (用來判斷 1.是否是士兵 2.村民工作狀態)
     * @return 該人物 物件的狀態
     */
    public String getState(){
        return state;
    }
    /**
     * 派遣村民去採木頭
     */
    public void setStateToWood(){
        state = "Wood";
    }

    /**
     * 派遣村民去採鐵
     */
    public void setStateToSteel(){
        state = "Steel";
    }

    /**
     * 設定 村民 = 閒人
     */
    public void setStateToFree(){
        state = "Free";
    }

}

