package company.gameobj.creature.human;

import company.gameobj.GameObject;
import company.gameobj.buildings.Base;
import oldMain.OldMain;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Citizens {

    private List<Citizen> citizens = new LinkedList<>();

    private List<Human> citizensInBox; // 把目前村民 傳進來的box Obj 有碰撞的存進去&& 傳出去

    /**
     * 最大村民數量
     */
    private int maxCitizen;
    /**
     * 村民計數器
     */
    private int count;

    private int valueOfCitizens;
    /**
     * 目前的閒人(可指派工作之村民總共有幾個)
     */
    private int numOfFreeCitizens;
    /**
     * 目前在伐木的人
     */
    private int numOfLoggingCitizens;
    /**
     * 目前在挖鋼的人
     */
    private int numOfMiningCitizens;


    /**
     * 創建新的居民群體並將size設成城市所傳入的預設值
     *
     * @param defaultNumOfCitizens
     */
    public Citizens(int defaultNumOfCitizens) {

        this.valueOfCitizens = 0;

        this.numOfFreeCitizens = 0;

        this.numOfLoggingCitizens = 0;

        this.numOfMiningCitizens = 0;

        this.add(defaultNumOfCitizens, Base.BASE_X-100,Base.BASE_Y);


        // 上限10個
        maxCitizen = 10;

        // 共有幾個村民
        count = 0;

        // 框選了幾個村民 => 用A:ist存
        citizensInBox = new ArrayList<>();
    }

    /**
     * 新增 num 個村民
     *
     * @param num 要加幾個村民
     */
    public void add(int num,int x,int y) {
        if(num <= 0){
            return;
        }
        // 新的村民宣告
        Citizen citizen;

        // 新增?個村民 閒民數量+?  [舊程式碼部分]
        this.numOfFreeCitizens += num;

        // 下面是新增村民之後 他的出生地點 [所有XY數值都是測試用 沒意義]
        for (int i = 0; i < num; i++) {
            if(i%6==5){
                x=x-74;
            }
//            citizen = new Citizen(Global.SCREEN_X/2, Global.SCREEN_Y/2);//TODO: set the correct x and y
            citizen = new Citizen(x, y + i%5 * 74); //100 ->x 400-y
            this.valueOfCitizens += citizen.getValue();
            this.citizens.add(citizen);
        }
    }

    /**
     * 直接加入一個村民
     *
     * @param citizen 新村民
     */
    public void add(Citizen citizen) {
        // 如果村民數量未達上限
        if (count < maxCitizen) {
            // 閒人 ++
            this.numOfFreeCitizens += 1;
            valueOfCitizens += citizen.getValue();
            citizens.add(citizen);
        }
    }


    /**
     * 居民群體受到傷害
     *
     * @param value 殭屍攻擊力
     */
    public void getHarmed(int value) {
        this.valueOfCitizens -= value;
        Citizen citizen;
        for (int i = 0; value > 0 && i < this.citizens.size(); i++) {
            citizen = this.citizens.get(i);
            //平民被殺掉
            if (value >= citizen.getValue()) {
                //損耗殭屍總攻擊力
                value -= citizen.getValue();
                //將掛掉的平民從list移除
                this.citizens.remove(i);
                i--;
            } else {
                //平民存活，扣血
                citizen.getAttacked(value);
                value = 0;
            }
        }
    }

    /**
     * 整個居民群體被消滅
     */
    public void getWipedOut() {
        this.citizens.clear();
        this.valueOfCitizens = 0;
        this.numOfFreeCitizens = 0;
        this.numOfLoggingCitizens = 0;
        this.numOfMiningCitizens = 0;
    }


    /**
     * 回傳現在正在伐木的市民人數
     *
     * @return
     */
    public int getNumOfLoggingCitizens() {
        return numOfLoggingCitizens;
    }

    /**
     * 回傳現在正在採鋼的居民人數
     *
     * @return
     */
    public int getNumOfMiningCitizens() {
        return numOfMiningCitizens;
    }

    /**
     * 回傳現在整個市民群體的總體血量
     *
     * @return
     */
    public int getValueOfCitizens() {
        return valueOfCitizens;
    }

    /**
     * 回傳現在狀態是空閑的居民人數
     *
     * @return
     */
    public int getNumOfFreeCitizens() {
        return numOfFreeCitizens;
    }

    /**
     * 將特定數量的市民指派去做特定種類的工作
     *
     * @param num
     * @param work
     */
    public void assignCitizenToWork(int num, OldMain.Command work) {

        this.numOfFreeCitizens -= num;

        if (work == OldMain.Command.WOOD) {
            this.numOfLoggingCitizens += num;
        } else if (work == OldMain.Command.STEEL) {
            this.numOfMiningCitizens += num;
        }

        Citizen citizen;

        for (int i = 0; num > 0; i++) {
            citizen = citizens.get(i);
            if (citizen.isFree()) {
                if (work == OldMain.Command.WOOD) {
                    citizen.startToLog();
                } else if (work == OldMain.Command.STEEL) {
                    citizen.startToMine();
                }
                num--;
            }
        }
    }

    /**
     * 回傳是否整個小鎮的居民都已經陣亡
     *
     * @return
     */
    public boolean isAllDead() {
        return this.citizens.isEmpty();
    }

    /**
     * 回傳現在市民群體的市民數量
     *
     * @return
     */
    public int getNumOfCitizens() {
        return this.citizens.size();
    }


    // 畫出所有的村民
    public void paintAll(Graphics g) {

        // 遍尋村民List
        for (Citizen citizen : citizens) {

            // 如果他現在能被看見的話 => 畫出他
            if (citizen.getVisible()) {
                citizen.paint(g);
            }
        }
    }

    /**
     * 更新所有村民
     */
    public void updateAll() {

        Citizen citizen;
        for (int i=0; i<citizens.size(); i++) {
            citizen = citizens.get(i);
            if(citizen.isAlive()){
                citizen.update();
            }
            else{
                citizens.remove(i);
                i--;
            }
        }
    }

    public void cameraMove(){
        for (Citizen citizen : citizens) {
            citizen.cameraMove();
        }
    }


    public void resetObjectXY() {
        for (Citizen citizen : citizens) {
            citizen.resetObjectXY();
        }
    }


    /**
     * 取個一個村民 => 參數(x,y) 在村民的painter()內
     *
     * @param x X座標
     * @param y Y座標
     * @return 一個村民
     */

    public Citizen getSingleCitizenByXY(int x, int y) {

        // 尋訪每一個村民
        for (Citizen citizen : citizens) {

            // 如果(x, y) 在這個村民的painter範圍內
            if (citizen.isEntered(x, y)) {
                return citizen;
            }
        }

        // 沒有村民在這個範圍內
        return null;
    }


    /**
     * 獲得框選的村民
     *
     * @param box 框選的框框
     * @return 回傳被框選的人民
     */

    public List<Human> getBoxCitizens(GameObject box) {

        // 如果 上次框選的村民還在 清空他
        if (!citizensInBox.isEmpty()) {
            citizensInBox.clear();
        }

        // 檢查 所有村民 和 傳進的的box 有沒有碰撞
        for (Citizen citizen : citizens) {
            if (citizen.painter().overlap(box.painter())) {
                citizensInBox.add(citizen);
            }
        }

        return citizensInBox;
    }


    public List<Citizen> getAllCitizens() {
        return this.citizens;
    }

}
