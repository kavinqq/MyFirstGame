package main;

import creature.human.AirForceSoldier;
import creature.human.ArmySoldier;

import java.util.LinkedList;
import java.util.List;

public class Military {
    private List<AirForceSoldier> airforce;
    private List<ArmySoldier> armies;
    private static final int INITIAL_ARMY_SIZE = 10;
    private int ArmyLevel;
    private int AirForceLevel;


    public Military(){
        this.airforce = new LinkedList<>();
        this.armies = new LinkedList<>();
        this.ArmyLevel = 0;
        this.AirForceLevel = 0;
        for(int i=0; i<INITIAL_ARMY_SIZE; i++){
            this.armies.add(new ArmySoldier(this.ArmyLevel));
        }
    }

    public void addArmy(int num){
        for(int i=0; i<num; i++){
            this.armies.add(new ArmySoldier(this.AirForceLevel));
        }
    }

    public void addAirForce(int num){//可以再加依據生產開始時間而有所不同的等級
        for(int i=0; i<num; i++){
            this.armies.add(new ArmySoldier(this.AirForceLevel));
        }
    }

    public void upgradeArmy(){
        this.ArmyLevel++;
    }

    public void upgradeAirForce(){
        this.AirForceLevel++;
    }

    public int armyValue(){
        int value = 0;
        for(ArmySoldier armySoldier : this.armies){
            value+= armySoldier.getValue();
        }
        return value;
    }

    public int airForceValue(){
        int value = 0;
        for(AirForceSoldier airForceSoldier : this.airforce){
            value += airForceSoldier.getValue();
        }
        return value;
    }


}
