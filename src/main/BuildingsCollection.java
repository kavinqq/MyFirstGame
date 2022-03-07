package main;

import buildings.*;

import java.util.LinkedList;
import static main.BuildingsCollection.BuildingType.*;

/**
 * @author Lillian
 * @Date 2022/3/3
 * @Description
 */
public class BuildingsCollection {

    public static final int PLANE_LEVEL_UPGRADE_TIME = 48;
    public static final int SOLDIER_LEVEL_UPGRADE_TIME = 48;
    public static final int TECH_LEVEL_UPGRADE_TIME = 24;

    public enum BuildingType {
        ARSENAL(6,new Arsenal()),
        BARRACKS(3,new Barracks()),
        HOUSE(1,new House()),
        LAB(2,new Lab()),
        SAW_MILL(4,new SawMill()),
        STEEL_MILL(5,new SteelMill()),
        GAS_MILL(7,new GasMill()),
        AIRPLANE_MILL(8,new AirPlaneMill());

        private int id;
        private Building instance;
        BuildingType(int id,Building building){
            this.id = id;
            instance = building;
        }
        public static BuildingType getBuildingTypeByInt(int option){
            for(BuildingType type: values()){
                if(type.id == option){
                    return type;
                }
            }
            return HOUSE;
        }
        public Building getInstance(){
            return instance;
        }
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

        /**
         * 建造完成的時間
         *
         * @return 建造完成的時間
         */
        public int getBuildEndTime() {
            return buildEndTime;
        }
    }

    private LinkedList<BuildingNode> arsenalList;
    private LinkedList<BuildingNode> barracksList;
    private LinkedList<BuildingNode> houseList;
    private LinkedList<BuildingNode> labList;
    private LinkedList<BuildingNode> sawMillList;
    private LinkedList<BuildingNode> steelMillList;
    private LinkedList<BuildingNode> gasMillList;
    private LinkedList<BuildingNode> airplaneMillList;
    /**
     * 科技等級開始升級時間
     */
    private int techLevelStartUpgradeTime;
    /**
     * 士兵等級開始升級時間
     */
    private int soldierLevelStartUpgradeTime;
    /**
     * 飛機等級開始升級時間
     */
    private int planeLevelStartUpgradeTime;
    /**
     * 閒置驗究所數量
     */
    private int freeLadNum;
    /**
     * 建築數量
     */
    private int buildingNum;

    public BuildingsCollection() {
        arsenalList = new LinkedList<>();
        barracksList = new LinkedList<>();
        houseList = new LinkedList<>();
        labList = new LinkedList<>();
        sawMillList = new LinkedList<>();
        steelMillList = new LinkedList<>();
        gasMillList = new LinkedList<>();
        airplaneMillList = new LinkedList<>();
        techLevelStartUpgradeTime = 0;
        buildingNum = 0;
    }

    /**
     * 建造
     * @param type 建築種類
     * @param resource 城市資源
     */
    public void build(BuildingType type,Resource resource) {
        BuildingNode newBuilding;
        switch (type) {
            case ARSENAL: {
                newBuilding = new BuildingNode(new Arsenal());
                setBuildingBuilding(newBuilding,resource);
                arsenalList.add(newBuilding);
                break;
            }
            case BARRACKS: {
                newBuilding = new BuildingNode(new Barracks());
                setBuildingBuilding(newBuilding,resource);
                barracksList.add(newBuilding);
                break;
            }
            case HOUSE: {
                newBuilding = new BuildingNode(new House());
                setBuildingBuilding(newBuilding,resource);
                houseList.add(newBuilding);
                break;
            }
            case LAB: {
                newBuilding = new BuildingNode(new Lab());
                setBuildingBuilding(newBuilding,resource);
                labList.add(newBuilding);
                break;
            }
            case SAW_MILL: {
                newBuilding = new BuildingNode(new SawMill());
                setBuildingBuilding(newBuilding,resource);
                sawMillList.add(newBuilding);
                break;
            }
            case STEEL_MILL: {
                newBuilding = new BuildingNode(new SteelMill());
                setBuildingBuilding(newBuilding,resource);
                steelMillList.add(newBuilding);
                break;
            }
            case GAS_MILL: {
                newBuilding = new BuildingNode(new GasMill());
                setBuildingBuilding(newBuilding,resource);
                gasMillList.add(newBuilding);
                break;
            }
            case AIRPLANE_MILL: {
                newBuilding = new BuildingNode(new AirPlaneMill());
                setBuildingBuilding(newBuilding,resource);
                airplaneMillList.add(newBuilding);
                break;
            }
        }
    }

    /**
     * 將建築設為建造中
     *
     * @param newBuilding
     */
    private void setBuildingBuilding(BuildingNode newBuilding,Resource resource) {
        alreadyTakeResource(resource,
                newBuilding.building.getWoodCostLevelUp(),
                newBuilding.building.getSteelCostLevelUp(),
                newBuilding.building.getGasCostLevelUp());
        newBuilding.buildStartTime = City.getGameTime();
        newBuilding.buildEndTime = City.getGameTime() + newBuilding.building.getBuildTime();
        newBuilding.building.setWorking(false);
        newBuilding.building.setUpgrading(true);
        newBuilding.building.setReadyToUpgrade(false);
    }

    /**
     * 建築開始運作，建築等級提升，關閉升級中狀態，開啟工作中狀態
     *
     * @param list
     */
    private void setBuildingStartWork(LinkedList<BuildingNode> list) {
        int sum = 0;
        for (BuildingNode node : list) {
            //若建造完成時間==當前時間->建造完成
            if (node.buildEndTime == City.getGameTime()) {
                node.building.setLevel(node.building.getLevel() + 1);
                node.building.setUpgrading(false);
                node.building.setWorking(true);
                node.building.setReadyToUpgrade(true);
                buildingNum++; //建造完成，建築數+1
                sum++;
                //若建造的是研究所，閒置數+1
                if (node.building instanceof Lab) {
                    freeLadNum++;
                }
            }
        }
        System.out.println(sum + "間建造完成");
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
     * 能不能建造
     * @param type
     */
    public boolean canBuild(BuildingType type,Resource resource){
        switch (type) {
            case ARSENAL:{
                if (City.getTechLevel() < ARSENAL.getInstance().getTechLevelNeedBuild()) {
                    return false;
                }
                return hasEnoughResourceToBuild(ARSENAL.getInstance(),resource);
            }
            case BARRACKS: {
                return hasEnoughResourceToBuild(BARRACKS.getInstance(),resource);
            }
            case HOUSE: {
                return hasEnoughResourceToBuild(HOUSE.getInstance(),resource);
            }
            case LAB: {
                return hasEnoughResourceToBuild(LAB.getInstance(),resource);
            }
            case SAW_MILL: {
                return hasEnoughResourceToBuild(SAW_MILL.getInstance(),resource);
            }
            case STEEL_MILL: {
                return hasEnoughResourceToBuild(STEEL_MILL.getInstance(),resource);
            }
            case GAS_MILL: {
                if(City.getTechLevel()<GAS_MILL.getInstance().getTechLevelNeedBuild()){
                    return false;
                }
                return hasEnoughResourceToBuild(GAS_MILL.getInstance(),resource);
            }
            case AIRPLANE_MILL: {
                if(City.getTechLevel()<AIRPLANE_MILL.getInstance().getTechLevelNeedBuild()){
                    return false;
                }
                return hasEnoughResourceToBuild(AIRPLANE_MILL.getInstance(),resource);
            }
        }
        return false;
    }

    /**
     * 判斷可否建造
     * @param building 要蓋的建築
     * @param resource
     * @return
     */
    private boolean hasEnoughResourceToBuild(Building building,Resource resource){
        return resource.getTotalWood()>=building.getWoodCostCreate()
            && resource.getTotalSteel()>=building.getSteelCostCreate()
            && resource.getTotalGas()>=building.getSteelCostCreate();
    }

    /**
     * 升級
     *
     * @param type
     * @return 是否已安排升級
     */
    public boolean upgrade(BuildingType type,Resource resource) {
        switch (type) {
            case BARRACKS: {
                setBuildingUpgrade(barracksList,resource);
                return true;
            }
            case HOUSE: {
                setBuildingUpgrade(houseList,resource);
                return true;
            }
            case LAB: {
                setBuildingUpgrade(labList,resource);
                return true;
            }
            case SAW_MILL: {
                setBuildingUpgrade(sawMillList,resource);
                return true;
            }
            case STEEL_MILL: {
                setBuildingUpgrade(steelMillList,resource);
                return true;
            }
            case GAS_MILL: {
                setBuildingUpgrade(gasMillList,resource);
                return true;
            }
            case AIRPLANE_MILL: {
                setBuildingUpgrade(airplaneMillList,resource);
                return true;
            }
        }
        return false;
    }

    /**
     * 將建築設為升級中
     *
     * @param list
     */
    private void setBuildingUpgrade(LinkedList<BuildingNode> list,Resource resource) {
        for (BuildingNode node : list) {
            if (!node.building.isUpgrading()) {
                alreadyTakeResource(resource,
                        node.building.getWoodCostLevelUp(),
                        node.building.getSteelCostLevelUp(),
                        node.building.getGasCostLevelUp());
                node.upgradeStartTime = City.getGameTime();
                node.upgradeEndTime = City.getGameTime() + node.building.getUpgradeTime();
                node.building.setUpgrading(true);
                node.building.setWorking(false);
                node.building.setReadyToUpgrade(false);
                freeLadNum--; //占用研究所資源
                if (freeLadNum <= 0) {
                    freeLadNum = 0;
                }
                return;
            }
        }
    }

    /**
     * 將建築完成升級
     *
     * @param list
     */
    private void finishUpgradeBuilding(LinkedList<BuildingNode> list) {
        int sum = 0;
        for (BuildingNode node : list) {
            //若建築的升級完成時間==當前時間->升級完成
            if (node.upgradeEndTime == City.getGameTime()) {
                node.building.setLevel(node.building.getLevel() + 1);
                node.building.setUpgrading(false);
                node.building.setWorking(true);
                node.building.setReadyToUpgrade(true);
                freeLadNum++; //釋放研究所資源
                sum++;
            }
        }
        System.out.println(sum + "間完成升級");
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
     * 完成建造和升級的工作
     */
    public void completeBuildingJob() {
        //建立完成
        showBuildCompleted();
        //升級完成
        showUpgradeCompleted();
        //科技等級升級
        if (City.getGameTime() - techLevelStartUpgradeTime - TECH_LEVEL_UPGRADE_TIME == 0) {
            City.addTechLevel();
        }
        //士兵等級升級
        if (City.getGameTime() - soldierLevelStartUpgradeTime - SOLDIER_LEVEL_UPGRADE_TIME == 0) {
            City.addTechLevel();
        }
        //飛機等級升級
        if (City.getGameTime() - planeLevelStartUpgradeTime - PLANE_LEVEL_UPGRADE_TIME == 0) {
            City.addTechLevel();
        }
    }

    /**
     * 取得各建築可以升級的數量
     * @param type     建築種類
     * @param resource 都市的物資
     * @return 可以升級數量
     */
    public int getCanUpgradeNum(BuildingType type, Resource resource) {
        //若沒有研究所不可以升級
        if (freeLadNum == 0) {
            return 0;
        }
        switch (type) {
            case BARRACKS: {
                if(City.getTechLevel()<BARRACKS.getInstance().getTechLevelNeedBuild()){
                    return 0;
                }
                return countCanUpgradeNum(barracksList, resource);
            }
            case HOUSE: {
                if(City.getTechLevel()<HOUSE.getInstance().getTechLevelNeedBuild()){
                    return 0;
                }
                return countCanUpgradeNum(houseList, resource);
            }
            case LAB: {
                return countCanUpgradeNum(labList, resource);
            }
            case SAW_MILL: {
                if(City.getTechLevel()<SAW_MILL.getInstance().getTechLevelNeedBuild()){
                    return 0;
                }
                return countCanUpgradeNum(sawMillList, resource);
            }
            case STEEL_MILL: {
                return countCanUpgradeNum(steelMillList, resource);
            }
            case GAS_MILL: {
                if (City.getTechLevel() < GAS_MILL.getInstance().getTechLevelNeedBuild()) {
                    return 0;
                }
                return countCanUpgradeNum(gasMillList, resource);
            }
            case AIRPLANE_MILL: {
                if (City.getTechLevel() < AIRPLANE_MILL.getInstance().getTechLevelNeedBuild()) {
                    return 0;
                }
                return countCanUpgradeNum(airplaneMillList, resource);
            }
        }
        return 0;
    }

    /**
     * 計算某種類的建築有多少可以升級
     *
     * @param list     建築鏈表
     * @param resource 都市的物資
     * @return 可以升級的數量
     */
    private int countCanUpgradeNum(LinkedList<BuildingNode> list, Resource resource) {
        int sum = 0;
        for (BuildingNode node : list) {
            //建築是否可升級 && 木頭夠 && 鋼鐵夠 && 瓦斯夠
            if (node.building.isReadyToUpgrade() && resource.getTotalWood()>=node.building.getGasCostLevelUp()
                && resource.getTotalSteel()>=node.building.getSteelCostLevelUp()
                && resource.getTotalGas()>=node.building.getGasCostLevelUp()) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * 取得當前建築數量
     *
     * @return
     */
    public int getBuildingNum() {
        return buildingNum;
    }

    /**
     * 升級科技等級
     */
    public void upgradeTechLevel() {
        //紀錄開始升級的時間點
        techLevelStartUpgradeTime = City.getGameTime();
    }

    /**
     * 升級士兵等級
     */
    public void upgradeSoldier(){
        soldierLevelStartUpgradeTime = City.getGameTime();
    }

    /**
     * 升級飛機等級
     */
    public void upgradePlane(){
        planeLevelStartUpgradeTime = City.getGameTime();
    }

    /**
     * 生成的居民數量
     *
     * @return 生成的人數
     */
    public int getNewCitizenNum() {
        int newPeopleCount = 0;
        for (BuildingNode buildingNode : houseList) {
            if (buildingNode.building.isWorking() && buildingNode.building instanceof House) {
                //當前時間-建造完成時間，若為24的倍數，產生市民
                if ((City.getGameTime() - buildingNode.buildEndTime) % 24 == 0) {
                    House house = (House) buildingNode.building;
                    newPeopleCount += house.produceCitizen();
                }
            }
        }
        return newPeopleCount;
    }

    /**
     * 生成的士兵數量
     *
     * @return 生成的士兵數
     */
    public int getNewSoldierNum() {
        int newSoldierCount = 0;
        for (BuildingNode buildingNode : barracksList) {
            if (buildingNode.building.isWorking() && buildingNode.building instanceof Barracks) {
                //當前時間-建造完成時間，若為3的倍數，產生士兵
                if ((City.getGameTime() - buildingNode.buildEndTime) % 3 == 0) {
                    Barracks barracks = (Barracks) buildingNode.building;
                    newSoldierCount += barracks.produceSoldier();
                }
            }
        }
        return newSoldierCount;
    }

    /**
     * 取得木頭的生產速度
     *
     * @return 生產木頭的速度
     */
    public int getWoodSpeed() {
        if (sawMillList.size() != 0) {
            if (sawMillList.get(0).building instanceof SawMill) {
                //取最高等級的採集速度
                SawMill sawMill = (SawMill) sawMillList.get(0).building;
                return sawMill.woodSpeed();
            }
        }
        //沒有伐木場就回傳初始採木速度
        return Resource.DEFAULT_WOOD_SPEED;
    }

    /**
     * 取得鋼鐵生產速度
     *
     * @return 生產鋼鐵的速度
     */
    public int getSteelSpeed() {
        if (steelMillList.size() != 0) {
            if (steelMillList.get(0).building instanceof SteelMill) {
                //取最高等級的採集速度
                SteelMill steelMill = (SteelMill) steelMillList.get(0).building;
                return steelMill.steelSpeed();
            }
        }
        return Resource.DEFAULT_STEEL_SPEED;
    }

    /**
     * 取得瓦斯生產量
     *
     * @return 生產瓦斯的速度
     */
    public int getGasProduceNum() {
        int gasNum = 0;
        for (BuildingNode node : gasMillList) {
            if (node.building.isWorking() && node.building instanceof GasMill) {
                GasMill gasMill = (GasMill) node.building;
                gasNum += gasMill.getProduceGasNum();
            }
        }
        return gasNum;
    }

    /**
     * 獲取建築資訊
     *
     * @return
     */
    public String getCurrentInformation() {
        StringBuilder info = new StringBuilder();

        info.append("房屋：").append(countBuildingNum(houseList)).append("間\n");
        info.append("研究所：").append(countBuildingNum(labList)).append("間\n");
        info.append("兵營：").append(countBuildingNum(barracksList)).append("間\n");
        info.append("兵工廠：").append(countBuildingNum(arsenalList)).append("間\n");
        info.append("伐木場：").append(countBuildingNum(sawMillList)).append("間\n");
        info.append("煉鋼廠：").append(countBuildingNum(steelMillList)).append("間\n");

        info.append("=====正在進行中的工程=====\n");

        int[] record = new int[3];
        countStatus(houseList, record);
        info.append("房屋：").append(getStatusWord(record));

        countStatus(labList, record);
        info.append("研究所：").append(getStatusWord(record));

        countStatus(barracksList, record);
        info.append("兵營：").append(getStatusWord(record));

        countStatus(arsenalList, record);
        info.append("兵工廠：").append(getStatusWord(record));

        countStatus(sawMillList, record);
        info.append("伐木場：").append(getStatusWord(record));

        countStatus(steelMillList, record);
        info.append("煉鋼廠：").append(getStatusWord(record));

        return info.toString();
    }

    /**
     * 取得建築狀態數量敘述
     *
     * @param record 傳入獲取數量陣列
     * @return 文字敘述
     */
    private String getStatusWord(int[] record) {
        return record[0] + "間 工作中 / " + record[1] + "間 建造中 / " + record[2] + "間 升級中";
    }

    /**
     * 各種房屋的數量
     *
     * @param list 哪種房屋
     * @return 總數
     */
    private int countBuildingNum(LinkedList<BuildingNode> list) {
        int sum = 0;
        for (BuildingNode node : list) {
            if (node.building.getLevel() != 0) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * 建築：工作中、建造中、升級中的數量
     *
     * @param list   建築種類
     * @param record 傳入陣列以獲取資料
     * @return [0]工作中數量 [1]建造中數量 [2]升級中數量
     */
    private void countStatus(LinkedList<BuildingNode> list, int[] record) {
        for (BuildingNode node : list) {
            if (node.building.isWorking()) {
                record[0]++;
            }
            if (node.building.getLevel() == 0 && node.building.isUpgrading()) {
                record[1]++;
            }
            if (node.building.getLevel() != 0 && node.building.isUpgrading()) {
                record[2]++;
            }
        }
    }

}
