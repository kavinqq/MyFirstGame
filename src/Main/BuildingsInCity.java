package Main;

import Buildings.*;

import java.util.LinkedList;

/**
 * @author Lillian
 * @Date 2022/3/3
 * @Description
 */
public class BuildingsInCity {
    private MyCity city;

    public enum BuildingType {
        ARSENAL,
        BARRACKS,
        HOUSE,
        LAB,
        SAW_MILL,
        STEEL_MILL;
    }

    private class BuildingNode {
        Building building;
        int buildStartTime;
        int buildEndTime;
        int upgradeStartTime;
        int upgradeEndTime;

        public BuildingNode(Building building) {
            this.building = building;
        }
    }

    private LinkedList<BuildingNode> arsenalList;
    private LinkedList<BuildingNode> barracksList;
    private LinkedList<BuildingNode> houseList;
    private LinkedList<BuildingNode> labList;
    private LinkedList<BuildingNode> sawMillList;
    private LinkedList<BuildingNode> steelMillList;

    public BuildingsInCity(MyCity city) {
        this.city = city;
        arsenalList = new LinkedList<>();
        barracksList = new LinkedList<>();
        houseList = new LinkedList<>();
        labList = new LinkedList<>();
        sawMillList = new LinkedList<>();
        steelMillList = new LinkedList<>();
    }

    /**
     * 建造
     *
     * @param type
     */
    public void build(BuildingType type) {
        BuildingNode newBuilding;
        switch (type) {
            case ARSENAL: {
                newBuilding = new BuildingNode(new Arsenal());
                setBuildingBuilding(newBuilding);
                arsenalList.add(newBuilding);
                break;
            }
            case BARRACKS: {
                newBuilding = new BuildingNode(new Barracks());
                setBuildingBuilding(newBuilding);
                barracksList.add(newBuilding);
                break;
            }
            case HOUSE: {
                newBuilding = new BuildingNode(new House());
                setBuildingBuilding(newBuilding);
                houseList.add(newBuilding);
                break;
            }
            case LAB: {
                newBuilding = new BuildingNode(new Lab());
                setBuildingBuilding(newBuilding);
                labList.add(newBuilding);
                break;
            }
            case SAW_MILL: {
                newBuilding = new BuildingNode(new SawMill());
                setBuildingBuilding(newBuilding);
                sawMillList.add(newBuilding);
                break;
            }
            case STEEL_MILL: {
                newBuilding = new BuildingNode(new SteelMill());
                setBuildingBuilding(newBuilding);
                steelMillList.add(newBuilding);
                break;
            }
        }

    }

    /**
     * 將建築設為建造中
     *
     * @param newBuilding
     */
    private void setBuildingBuilding(BuildingNode newBuilding) {
        newBuilding.buildStartTime = MyCity.getGameTime();
        newBuilding.buildEndTime = MyCity.getGameTime() + newBuilding.building.getBuildTime();
        newBuilding.building.setWorking(false);
        newBuilding.building.setUpgrading(true);
    }

    /**
     * 升級
     *
     * @param type
     * @param fromLevel
     * @return
     */
    public boolean upgrade(BuildingType type, int fromLevel) {
        switch (type) {
            case ARSENAL: {
                for (BuildingNode node : arsenalList) {
                    //目標等級且沒有在升級中
                    if (node.building.getLevel() == fromLevel && !node.building.isUpgrading()) {
                        setBuildingUpgrading(node);
                        arsenalList.add(node);
                        return true;
                    }
                }
                break;
            }
            case BARRACKS: {
                for (BuildingNode node : barracksList) {
                    if (node.building.getLevel() == fromLevel && !node.building.isUpgrading()) {
                        setBuildingUpgrading(node);
                        barracksList.add(node);
                        return true;
                    }
                }
                break;
            }
            case HOUSE: {
                for (BuildingNode node : houseList) {
                    if (node.building.getLevel() == fromLevel && !node.building.isUpgrading()) {
                        setBuildingUpgrading(node);
                        houseList.add(node);
                        return true;
                    }
                }
                break;
            }
            case LAB: {
                for (BuildingNode node : labList) {
                    if (node.building.getLevel() == fromLevel && !node.building.isUpgrading()) {
                        setBuildingUpgrading(node);
                        labList.add(node);
                        return true;
                    }
                }
                break;
            }
            case SAW_MILL: {
                for (BuildingNode node : sawMillList) {
                    if (node.building.getLevel() == fromLevel && !node.building.isUpgrading()) {
                        setBuildingUpgrading(node);
                        sawMillList.add(node);
                        return true;
                    }
                }
                break;
            }
            case STEEL_MILL: {
                for (BuildingNode node : steelMillList) {
                    if (node.building.getLevel() == fromLevel && !node.building.isUpgrading()) {
                        setBuildingUpgrading(node);
                        steelMillList.add(node);
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

    /**
     * 將建築設為升級中
     *
     * @param node
     */
    private void setBuildingUpgrading(BuildingNode node) {
        node.upgradeStartTime = MyCity.getGameTime();
        node.upgradeEndTime = MyCity.getGameTime() + node.building.getUpgradeTime();
        node.building.setUpgrading(true);
        node.building.setWorking(false);
    }

    /**
     * 完成建造和升級的工作
     */
    public void completeBuildingJob() {
        //建立完成
        showBuildCompleted();
        //升級完成
        showUpgradeCompleted();
    }

    /**
     * 物資蒐集
     */
    public void addResource() {
        //房屋
        addCitizen();
        //軍營
        addSoldier();
        //伐木場
        addWood();
        //煉鋼廠
        addSteel();
    }

    /**
     * 所有建造完成的建築開始運作
     */
    public void showBuildCompleted() {
        System.out.print("房屋 ");
        setBuildingStartWork(houseList);

        System.out.print("研究所 ");
        setBuildingStartWork(labList);

        System.out.print("軍營 ");
        setBuildingStartWork(barracksList);

        System.out.print("伐木場 ");
        setBuildingStartWork(sawMillList);

        System.out.print("煉鋼廠 ");
        setBuildingStartWork(steelMillList);

        System.out.print("兵工廠 ");
        setBuildingStartWork(arsenalList);
    }

    /**
     * 建築開始運作
     *
     * @param list
     */
    private void setBuildingStartWork(LinkedList<BuildingNode> list) {
        int sum = 0;
        for (BuildingNode node : list) {
            if (node.buildEndTime == MyCity.getGameTime()) {
                node.building.setLevel(node.building.getLevel()+1);
                node.building.setUpgrading(false);
                node.building.setWorking(true);
                city.setBuildingCount(city.getBuildingCount() + 1);
                sum++;
            }
        }
        System.out.println(sum + "間完成建造");
    }

    /**
     * 所有完成升級的建築升級
     */
    public void showUpgradeCompleted() {
        System.out.print("房屋 ");
        finishUpgradeBuilding(houseList);

        System.out.print("研究所 ");
        finishUpgradeBuilding(labList);

        System.out.print("軍營 ");
        finishUpgradeBuilding(barracksList);

        System.out.print("伐木場 ");
        finishUpgradeBuilding(sawMillList);

        System.out.print("煉鋼廠 ");
        finishUpgradeBuilding(steelMillList);

        System.out.print("兵工廠 ");
        finishUpgradeBuilding(arsenalList);
    }

    /**
     * 將建築完成升級
     *
     * @param list
     */
    private void finishUpgradeBuilding(LinkedList<BuildingNode> list) {
        int sum = 0;
        for (BuildingNode node : list) {
            if (node.upgradeEndTime == MyCity.getGameTime()) {
                node.building.setLevel(node.building.getLevel()+1);
                node.building.setUpgrading(false);
                node.building.setWorking(true);
                sum++;
            }
        }
        System.out.println(sum + "間完成升級");
    }

    /**
     * 增加居民
     */
    public void addCitizen() {
        int newPeopleCount = city.getFreeCitizen();
        for (BuildingNode node : houseList) {
            if (node.building.isWorking()) {
                if (node.building instanceof House) {
                    House house = (House) node.building;
                    newPeopleCount += house.produceCitizen();
                }
            }
        }
        city.setFreeCitizen(newPeopleCount);
    }

    /**
     * 增加士兵
     */
    public void addSoldier() {
        int newSoldierCount = city.getSoldierNum();
        for (BuildingNode node : barracksList) {
            if (node.building.isWorking()) {
                if (node.building instanceof Barracks) {
                    Barracks barracks = (Barracks) node.building;
                    newSoldierCount += barracks.produceSoldier();
                }
            }
        }
        city.setSoldierNum(newSoldierCount);
    }

    /**
     * 增加木頭
     */
    public void addWood() {
        if (sawMillList.size() == 0) {
            city.getResource().setTotalWood(city.getWoodMan() * Resource.WOOD_SPEED);
        } else {
            if (sawMillList.get(0).building instanceof SawMill) {
                SawMill sawMill = (SawMill) sawMillList.get(0).building;
                city.getResource().setTotalWood(city.getWoodMan() * sawMill.woodSpeed());
            }
        }
    }

    /**
     * 增加鋼鐵
     */
    public void addSteel() {
        if (steelMillList.size() == 0) {
            city.getResource().setTotalSteel(city.getSteelMan() * Resource.STEEL_SPEED);
        } else {
            if (steelMillList.get(0).building instanceof SteelMill) {
                SteelMill steelMill = (SteelMill) steelMillList.get(0).building;
                city.getResource().setTotalSteel(city.getSteelMan() * steelMill.steelSpeed());
            }
        }
    }

    /**
     * 獲取建築資訊
     * @return
     */
    public String getInformation() {
        StringBuilder info = new StringBuilder();

        info.append("房屋：").append(countBuildingNum(houseList)).append("間\n");
        info.append("研究所：").append(countBuildingNum(labList)).append("間\n");
        info.append("兵營：").append(countBuildingNum(barracksList)).append("間\n");
        info.append("兵工廠：").append(countBuildingNum(arsenalList)).append("間\n");
        info.append("伐木場：").append(countBuildingNum(sawMillList)).append("間\n");
        info.append("煉鋼廠：").append(countBuildingNum(steelMillList)).append("間\n");

        info.append("=====正在進行中的工程=====\n");

        int[] record = new int[3];
        countStatus(houseList,record);
        info.append("房屋：").append(getStatusWord(record));

        countStatus(labList,record);
        info.append("研究所：").append(getStatusWord(record));

        countStatus(barracksList,record);
        info.append("兵營：").append(getStatusWord(record));

        countStatus(arsenalList,record);
        info.append("兵工廠：").append(getStatusWord(record));

        countStatus(sawMillList,record);
        info.append("伐木場：").append(getStatusWord(record));

        countStatus(steelMillList,record);
        info.append("煉鋼廠：").append(getStatusWord(record));

        return info.toString();
    }

    private String getStatusWord(int[] record){
        return record[0] + "間 工作中 / " + record[1] + "間 建造中 / " + record[2] + "間 升級中";
    }

    /**
     * 各種房屋的數量
     *
     * @param list 哪種房屋
     * @return 總數
     */
    private int countBuildingNum(LinkedList<BuildingNode> list) {
        return list.size();
    }

    /**
     * 建築：工作中、建造中、升級中的數量
     * @param list 建築種類
     * @param record 傳入陣列以獲取資料
     * @return [0]工作中數量 [1]建造中數量 [2]升級中數量
     */
    private void countStatus(LinkedList<BuildingNode> list,int[] record) {
        for(BuildingNode node : list){
            if (node.building.isWorking()){
                record[0]++;
            }
            if(node.building.getLevel()==0 && node.building.isUpgrading()){
                record[1]++;
            }
            if(node.building.getLevel()!=0 && node.building.isUpgrading()){
                record[2]++;
            }
        }
    }


}
