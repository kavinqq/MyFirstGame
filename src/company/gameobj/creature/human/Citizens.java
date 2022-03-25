package company.gameobj.creature.human;

import company.Global;
import company.gameobj.GameObject;
import oldMain.OldMain;
//import sun.awt.image.ImageWatched;

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
//        this.add(defaultNumOfCitizens); // 暫時先註解掉一下 我測試東西而已

        maxCitizen = 100;
        count = 0;

        citizensInBox = new ArrayList<>();
    }

    /**
     * 新增 num 個居民
     *
     * @param num
     */
    public void add(int num) {
        Citizen citizen;
        this.numOfFreeCitizens += num;
        for (int i = 0; i < num; i++) {
            citizen = new Citizen(Global.SCREEN_X/2, Global.SCREEN_Y/2);//TODO: set the correct x and y
            this.valueOfCitizens += citizen.getValue();
            this.citizens.add(citizen);
        }
    }

    /**
     * 直接加入一個村民
     * @param citizen 新村民
     */
    public void add(Citizen citizen) {
        // 如果村民數量未達上限
        if(count < maxCitizen){
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

    public void paintAll(Graphics g){
        for(Citizen citizen: citizens){
            citizen.paint(g);
        }
    }

    public void updateAll(){

        for(Citizen citizen: citizens){
            citizen.update();
        }
    }


    /**
     * 取個一個村民 => 參數(x,y) 在村民的painter()內
     * @param x X座標
     * @param y Y座標
     * @return 一個村民
     */
    public Citizen getCitizen(int x, int y){

        // 尋訪每一個村民
        for(Citizen citizen: citizens){

            // 如果(x, y) 在這個村民的painter範圍內
            if(citizen.isEntered(x, y)){
                return citizen;
            }
        }

        // 沒有村民在這個範圍內
        return null;
    }

    public List<Human> getBoxCitizens(GameObject box){

        // 如果 上次框選的村民還在 清空他
        if(!citizensInBox.isEmpty()){
            citizensInBox.clear();
        }

        // 檢查 所有村民 和 傳進的的box 有沒有碰撞
        for(Citizen citizen: citizens){
            if(citizen.painter().overlap(box.painter())){
                citizensInBox.add(citizen);
            }
        }

        return citizensInBox;
    }

    public List<Citizen> getAllCitizens(){
        return this.citizens;
    }

}
