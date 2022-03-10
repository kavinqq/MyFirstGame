package creature.human;

import main.Main;

import java.util.LinkedList;
import java.util.List;

public class Citizens {
    private List<Citizen> citizens = new LinkedList<>();
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

    public Citizens(int defaultNumOfCitizens){
        this.valueOfCitizens = 0;
        this.numOfFreeCitizens = defaultNumOfCitizens;
        this.numOfLoggingCitizens = 0;
        this.numOfMiningCitizens = 0;
        this.add(defaultNumOfCitizens);
    }

    public void add(int num){
        Citizen citizen;
        this.numOfFreeCitizens += num;
        for(int i=0; i<num; i++){
            citizen = new Citizen();
            this.valueOfCitizens += citizen.getValue();
            this.citizens.add(citizen);
        }
    }

    public void getHarmed(int value){
        this.valueOfCitizens -= value;
        Citizen citizen;
        for(int i=0; value>0&&i<this.citizens.size(); i++){
            citizen = this.citizens.get(i);
            if(value>=citizen.getValue()){
                value-=citizen.getValue();
                this.citizens.remove(i);
            }
            else{
                citizen.setValue(citizen.getValue()-value);
            }
        }
    }

    public void getWipedOut(){
        this.citizens.clear();
        this.valueOfCitizens = 0;
    }

    public boolean isAlive(){
        return (!this.citizens.isEmpty());
    }

    public int getNumOfLoggingCitizens() {
        return numOfLoggingCitizens;
    }

    public int getNumOfMiningCitizens() {
        return numOfMiningCitizens;
    }

    public int getValueOfCitizens() {
        return valueOfCitizens;
    }

    public int getNumOfFreeCitizens() {
        return numOfFreeCitizens;
    }

    public void assignCitizenToWork(int num, Main.Command work){
        this.numOfFreeCitizens -= num;
        if(work == Main.Command.WOOD){
           this.numOfLoggingCitizens += num;
        }
        else if(work == Main.Command.STEEL){
            this.numOfMiningCitizens += num;
        }

        Citizen citizen;
        for(int i=0; num>0; i++){
            citizen = citizens.get(i);
            if(citizen.isFree()){
                if(work == Main.Command.WOOD){
                    citizen.startToLog();
                }
                else if(work == Main.Command.STEEL){
                    citizen.startToMine();
                }
            }
        }
    }

    public boolean isAllDead(){
        return this.citizens.isEmpty();
    }

    public int getNumOfCitizens(){
        return this.citizens.size();
    }
}
