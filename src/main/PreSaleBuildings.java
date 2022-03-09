package main;

import buildings.*;
//預建設建築物的資訊（每一個先建立一個實體，以供參考）
public class PreSaleBuildings {
    //目前總共有幾種建築物可供建造
    final int MAX_BUILDING_TYPE = 6;
    //用來裝這幾種建築的陣列
    private Building[] preBuilding;
    //建構子
    private int[] sum;

    /**
     * 建構子，用來把 每一個種類的房屋都建立一個實體 當成demo(用來取得裡面的方法)
     */
    public PreSaleBuildings(){
        sum = new int[MAX_BUILDING_TYPE];
        preBuilding = new Building[MAX_BUILDING_TYPE];
        preBuilding[0] = new House();
        preBuilding[1] = new Lab();
        preBuilding[2] = new Barracks();
        preBuilding[3] = new SawMill();
        preBuilding[4] = new SteelMill();
        preBuilding[5] = new Arsenal();
    }
    //回傳這一個裝著所有類型 建築物物件 的陣列
    public Building[] getPreBuildings(){
        return preBuilding;
    }

    /**
     * 透過ID來獲得 一個Building物件
     * @param index 建築物ID
     * @return 回傳 該ID建築物的預售屋在陣列中的實體
     */
    public Building getPreBuildingByIndex(int index){
        return preBuilding[index];
    }

    /**
     * 該ID的建築物 可升級數量 + 1
     * @param buildingID 該建築在陣列的位置
     */
    public void add(int buildingID){
        sum[buildingID - 1] += 1;
    }

    /**
     * 統計全部現有 && 可以升級的房子數量
     * @param index 他在 preBuilding陣列中 的位置
     * @return 回傳該建築物 有多少可升級的數量
     */
    public int getSum(int index){
        return sum[index];
    }

    /**
     * 將sum歸0
     */
    public void zeros() {
        for (int i = 0; i < MAX_BUILDING_TYPE; i++) {
            sum[i]=0;
        }
    }
}
