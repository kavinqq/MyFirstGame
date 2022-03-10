package main;

import creature.human.AirForceSoldier;
import creature.human.ArmySoldier;

import java.util.LinkedList;
import java.util.List;

public class Military {
    private List<AirForceSoldier> airForce;
    private List<ArmySoldier> armies;
    private static final int INITIAL_ARMY_SIZE = 10;
    private int armyLevel;
    private int airForceLevel;
    private int armyValue;
    private int airForceValue;


    public Military(){
        this.airForce = new LinkedList<>();
        this.armies = new LinkedList<>();
        this.armyLevel = 0;
        this.airForceLevel = 0;
        this.armyValue = 0;
        this.airForceValue = 0;
        for(int i=0; i<INITIAL_ARMY_SIZE; i++){
            this.armies.add(new ArmySoldier(this.armyLevel));
        }
        upDateArmyValue();
    }

    /**
     * add certain number of army soldiers to the army
     * @param num
     */
    public void addArmy(int num){
        for(int i=0; i<num; i++){
            this.armies.add(new ArmySoldier(this.airForceLevel));
        }
    }

    /**
     * add certain number of air force soldiers to the air force
     * @param num
     */
    public void addAirForce(int num){
        for(int i=0; i<num; i++){
            this.armies.add(new ArmySoldier(this.airForceLevel));
        }
    }

    /**
     * upgrade the level of army
     */
    public void upgradeArmy(){
        this.armyLevel++;
    }

    /**
     * upgrade the level of air force
     */
    public void upgradeAirForce(){
        this.airForceLevel++;
    }

    /**
     * get the total value of all army soldiers
     * @return
     */
    public int getArmyValue() {
        return armyValue;
    }

    /**
     * get the total value of all airmen
     * @return
     */
    public int getAirForceValue() {
        return airForceValue;
    }

    /**
     * 當陸軍遭受到攻擊，更新地面部隊的值
     */
    public void upDateArmyValue(){
        this.armyValue = 0;
        for(ArmySoldier armySoldier : this.armies){
            this.armyValue+= armySoldier.getValue();
        }
    }
    /**
     * 當空軍遭受到攻擊，更新空中部隊的值
     */
    public void upDateAirForceValue(){
        this.airForceValue = 0;
        for(AirForceSoldier airForceSoldier : this.airForce){
            this.airForceValue += airForceSoldier.getValue();
        }
    }
    /**
     * 空軍被攻擊，受到特定點數的傷害
     * @param value 所受到的傷害
     */
    public void getAirForceHarmed(int value){
        this.airForceValue -= value;
        AirForceSoldier airForceSoldier;
        for(int i = 0; value>0 && i< airForce.size(); i++){
            airForceSoldier = airForce.get(i);
            if(value>=airForceSoldier.getValue()){
                value -= airForceSoldier.getValue();
                airForce.remove(i);
                i--;
            }
            else{
                airForceSoldier.getAttacked(value);
                value = 0;
            }
        }
    }

    /**
     * 陸軍被攻擊，受到特定點數的傷害
     * @param value 所受到的傷害
     */
    public void getArmyHarmed(int value){
        this.armyValue -= value;
        ArmySoldier armySoldier;
        for(int i = 0; value>0 && i< airForce.size(); i++){
            armySoldier = armies.get(i);
            if(value>=armySoldier.getValue()){
                value -= armySoldier.getValue();
                airForce.remove(i);
                i--;
            }
            else{
                armySoldier.getAttacked(value);
                value = 0;
            }
        }
    }
    /**
     * 空軍被全滅
     */
    public void getAirForceWipedOut(){
        this.airForce.clear();
        this.armyValue = 0;
    }

    /**
     * 陸軍被全滅
     */
    public void getArmyWipedOut(){
        this.armies.clear();
        this.armyValue = 0;
    }

    /**
     * 檢查軍隊是否已全軍覆沒
     * @return
     */
    public boolean isAllDied(){
        return (this.armies.isEmpty() && this.airForce.isEmpty());
    }

    /**
     * 回傳空軍的數量
     * @return
     */
    public int getNumOfAirmen(){
        return this.airForce.size();
    }

    /**
     * 回傳陸軍士兵的數量
     * @return
     */
    public int getNumOfArmySoldier(){
        return this.armies.size();
    }
}
