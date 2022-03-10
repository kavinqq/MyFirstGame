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

    public void addArmy(int num){
        for(int i=0; i<num; i++){
            this.armies.add(new ArmySoldier(this.airForceLevel));
        }
    }

    public void addAirForce(int num){//可以再加依據生產開始時間而有所不同的等級
        for(int i=0; i<num; i++){
            this.armies.add(new ArmySoldier(this.airForceLevel));
        }
    }

    public void upgradeArmy(){
        this.armyLevel++;
    }

    public void upgradeAirForce(){
        this.airForceLevel++;
    }

    public int getArmyValue() {
        return armyValue;
    }

    public int getAirForceValue() {
        return airForceValue;
    }

    public void upDateArmyValue(){
        this.armyValue = 0;
        for(ArmySoldier armySoldier : this.armies){
            this.armyValue+= armySoldier.getValue();
        }
    }

    public void upDateAirForceValue(){
        this.airForceValue = 0;
        for(AirForceSoldier airForceSoldier : this.airForce){
            this.airForceValue += airForceSoldier.getValue();
        }
    }

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

    public void getAirForceWipedOut(){
        this.airForce.clear();
        this.armyValue = 0;
    }

    public void getArmyWipedOut(){
        this.armies.clear();
        this.armyValue = 0;
    }

    public boolean isAllDied(){
        return (this.armies.isEmpty() && this.airForce.isEmpty());
    }

    public int getNumOfAirmen(){
        return this.airForce.size();
    }

    public int getNumOfArmySoldier(){
        return this.armies.size();
    }
}
