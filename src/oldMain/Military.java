package oldMain;

import company.Global;

import company.gameobj.buildings.Base;
import company.gameobj.creature.human.AirForceSoldier;
import company.gameobj.creature.human.ArmySoldier;

import company.gameobj.GameObject;
import company.gameobj.creature.human.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static company.Global.BUILDING_HEIGHT;
import static company.Global.BUILDING_WIDTH;

public class Military {
    private List<AirForceSoldier> airForce;
    private List<ArmySoldier> army;
    private static final int INITIAL_ARMY_SIZE = 1;//10;
    private int armyLevel;
    private int airForceLevel;
    private int armyValue;
    private int airForceValue;


    private List<Human> soldiersInBox;


    public Military() {

        this.airForce = new LinkedList<>();
        this.army = new LinkedList<>();
        this.armyLevel = 0;
        this.airForceLevel = 0;
        this.armyValue = 0;
        this.airForceValue = 0;

        this.addArmy(INITIAL_ARMY_SIZE, Base.BASE_X+250+Base.BASE_WIDTH,Base.BASE_Y);

        upDateArmyValue();
        upDateAirForceValue();

        soldiersInBox = new ArrayList<>();
    }

    /**
     * add certain number of army soldiers to the army
     *
     * @param num
     */
    public void addArmy(int num,int x,int y){
        if(num <= 0){
            return;
        }

        ArmySoldier armySoldier;

        for (int i = 0; i < num; i++) {
            armySoldier = new ArmySoldier(x, y, this.armyLevel);
            this.armyValue += armySoldier.getValue();
            this.army.add(armySoldier);
        }
    }

    /**
     * add certain number of air force soldiers to the air force
     *
     * @param num
     */
    public void addAirForce(int num,int x,int y){
        if(num <= 0){
            return;
        }
        AirForceSoldier airForceSoldier;

        for(int i=0; i<num; i++){
            //飛機不能超過4台
            if(airForce.size()>4){
                break;
            }
            airForceSoldier = new AirForceSoldier(x, y, this.airForceLevel);//TODO: use the correct x and y
            this.airForceValue += airForceSoldier.getValue();
            this.airForce.add(airForceSoldier);
        }
    }

    //取得飛行士兵的數量
    public int getAirForceNum(){
        return airForce.size();
    }

    /**
     * upgrade the level of army
     */
    public void upgradeArmy() {
        this.armyLevel++;
    }

    /**
     * upgrade the level of air force
     */
    public void upgradeAirForce() {
        this.airForceLevel++;
    }

    /**
     * get the total value of all army soldiers
     *
     * @return
     */
    public int getArmyValue() {
        return armyValue;
    }

    /**
     * get the total value of all airmen
     *
     * @return
     */
    public int getAirForceValue() {
        return airForceValue;
    }

    /**
     * 當陸軍遭受到攻擊，更新地面部隊的值
     */
    public void upDateArmyValue() {
        this.armyValue = 0;
        for (ArmySoldier armySoldier : this.army) {
            this.armyValue += armySoldier.getValue();
        }
    }

    /**
     * 當空軍遭受到攻擊，更新空中部隊的值
     */
    public void upDateAirForceValue() {
        this.airForceValue = 0;
        for (AirForceSoldier airForceSoldier : this.airForce) {
            this.airForceValue += airForceSoldier.getValue();
        }
    }

    /**
     * 空軍被攻擊，受到特定點數的傷害
     *
     * @param value 所受到的傷害
     */
    public void getAirForceHarmed(int value) {
        this.airForceValue -= value;
        AirForceSoldier airForceSoldier;
        for (int i = 0; value > 0 && i < airForce.size(); i++) {
            airForceSoldier = airForce.get(i);
            if (value >= airForceSoldier.getValue()) {
                value -= airForceSoldier.getValue();
                airForce.remove(i);
                i--;
            } else {
                airForceSoldier.getAttacked(value);
                value = 0;
            }
        }
    }

    /**
     * 陸軍被攻擊，受到特定點數的傷害
     *
     * @param value 所受到的傷害
     */
    public void getArmyHarmed(int value) {
        this.armyValue -= value;
        ArmySoldier armySoldier;
        for (int i = 0; value > 0 && i < army.size(); i++) {
            armySoldier = army.get(i);
            if (value >= armySoldier.getValue()) {
                value -= armySoldier.getValue();
                army.remove(i);
                i--;
            } else {
                armySoldier.getAttacked(value);
                value = 0;
            }
        }
    }

    /**
     * 空軍被全滅
     */
    public void getAirForceWipedOut() {
        this.airForce.clear();
        this.airForceValue = 0;
    }

    /**
     * 陸軍被全滅
     */
    public void getArmyWipedOut() {
        this.army.clear();
        this.armyValue = 0;
    }

    /**
     * 檢查軍隊是否已全軍覆沒
     *
     * @return
     */
    public boolean isAllDead() {
        return (this.army.isEmpty() && this.airForce.isEmpty());
    }

    /**
     * 回傳空軍的數量
     *
     * @return
     */
    public int getNumOfAirmen() {
        return this.airForce.size();
    }

    /**
     * 回傳陸軍士兵的數量
     *
     * @return
     */
    public int getNumOfArmySoldier() {
        return this.army.size();
    }

    /**
     * 畫出軍隊
     *
     * @param g Graphics
     */
    public void paintAll(Graphics g) {

        for (ArmySoldier armySoldier : army) {
            armySoldier.paint(g);
        }

        for (AirForceSoldier airForceSoldier : airForce) {
            airForceSoldier.paint(g);
        }
    }

    public void cameraMove(){
        for(ArmySoldier armySoldier: army){
            armySoldier.cameraMove();
        }

        for(AirForceSoldier airForceSoldier: airForce){
            airForceSoldier.cameraMove();
        }
    }

    public void resetObjectXY(){
        for(ArmySoldier armySoldier: army){
            armySoldier.resetObjectXY();
        }

        for(AirForceSoldier airForceSoldier: airForce){
            airForceSoldier.resetObjectXY();
        }
    }

    public List<AirForceSoldier> getAirForce() {
        return airForce;
    }

    public List<ArmySoldier> getArmy() {
        return army;
    }

    public Soldier getSingleSoldierByXY(int x, int y) {

        for (ArmySoldier armySoldier : army) {
            if (armySoldier.isEntered(x, y)) {
                return armySoldier;
            }
        }

        for (AirForceSoldier airForceSoldier : airForce) {
            if (airForceSoldier.isEntered(x, y)) {
                return airForceSoldier;
            }
        }

        return null;
    }

    public List<Human> getBoxSelectSoldier(GameObject box) {

        // 如果 上次框選的軍人還在 清空他
        if (!soldiersInBox.isEmpty()) {
            soldiersInBox.clear();
        }

        // 檢查 所有軍人 和 傳進的的box 有沒有碰撞
        for (ArmySoldier armySoldier : army) {
            if (armySoldier.isCollision(box)) {
                soldiersInBox.add(armySoldier);
            }
        }

        for (AirForceSoldier airForceSoldier : airForce) {
            if (airForceSoldier.isCollision(box)) {
                soldiersInBox.add(airForceSoldier);
            }
        }

        return soldiersInBox;
    }


    public void updateAll() {
        ArmySoldier armySoldier;
        for (int i = 0; i < army.size(); i++) {
            armySoldier = army.get(i);
            if (armySoldier.isAlive()) {
                armySoldier.update();
            } else {
                army.remove(i);
                i--;
            }
        }

        AirForceSoldier airForceSoldier;
        for (int i = 0; i < airForce.size(); i++) {
            airForceSoldier = airForce.get(i);
            if (airForceSoldier.isAlive()) {
                airForceSoldier.update();
            } else {
                airForce.remove(i);
                i--;
            }
        }
    }
}
