package main;

import buildings.*;

import java.util.ArrayList;
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
        ARSENAL(new Arsenal(), new LinkedList<>()),
        BARRACKS(new Barracks(), new LinkedList<>()),
        HOUSE(new House(), new LinkedList<>()),
        LAB(new Lab(), new LinkedList<>()),
        SAW_MILL(new SawMill(), new LinkedList<>()),
        STEEL_MILL(new SteelMill(), new LinkedList<>()),
        GAS_MILL(new GasMill(), new LinkedList<>()),
        AIRPLANE_MILL(new AirPlaneMill(), new LinkedList<>());

        private Building instance;
        private LinkedList<BuildingNode> list;

        BuildingType(Building building, LinkedList<BuildingNode> list) {
            instance = building;
            this.list = list;

        }

        public static BuildingType getBuildingTypeByInt(int option) {
            for (BuildingType type : values()) {
                if (type.instance.getId() == option) {
                    return type;
                }
            }
            return HOUSE;
        }

        public Building instance() {
            return instance;
        }
        public LinkedList<BuildingNode> getList(){
            return list;
        }
    }

    public class BuildingNode {
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
     * 閒置兵工廠數量
     */
    private int freeArsenalNum;
    /**
     * 建築數量
     */
    private int buildingNum;

    public BuildingsCollection() {
//        arsenalList = new LinkedList<>();
//        barracksList = new LinkedList<>();
//        houseList = new LinkedList<>();
//        labList = new LinkedList<>();
//        sawMillList = new LinkedList<>();
//        steelMillList = new LinkedList<>();
//        gasMillList = new LinkedList<>();
//        airplaneMillList = new LinkedList<>();
        techLevelStartUpgradeTime = 0;
        buildingNum = 0;
    }

    /**
     * 建造
     *
     * @param type     建築種類
     * @param resource 城市資源
     */
    public void build(BuildingType type, Resource resource) {
        BuildingNode newBuilding = new BuildingNode(new House());
        switch (type) {
            case ARSENAL: {
                newBuilding = new BuildingNode(new Arsenal());
                break;
            }
            case BARRACKS: {
                newBuilding = new BuildingNode(new Barracks());
                break;
            }
            case HOUSE: {
                break;
            }
            case LAB: {
                newBuilding = new BuildingNode(new Lab());
                break;
            }
            case SAW_MILL: {
                newBuilding = new BuildingNode(new SawMill());
                break;
            }
            case STEEL_MILL: {
                newBuilding = new BuildingNode(new SteelMill());
                break;
            }
            case GAS_MILL: {
                newBuilding = new BuildingNode(new GasMill());
                break;
            }
            case AIRPLANE_MILL: {
                newBuilding = new BuildingNode(new AirPlaneMill());
                break;
            }
        }
        alreadyTakeResource(resource,
                newBuilding.building.getWoodCostLevelUp(),
                newBuilding.building.getSteelCostLevelUp(),
                newBuilding.building.getGasCostLevelUp());
        newBuilding.buildStartTime = City.getGameTime();
        newBuilding.buildEndTime = City.getGameTime() + newBuilding.building.getBuildTime();
        newBuilding.building.setWorking(false);
        newBuilding.building.setUpgrading(true);
        newBuilding.building.setReadyToUpgrade(false);
        type.list.add(newBuilding);
    }

    /**
     * 所有建造完成的建築開始運作
     */
    public void showBuildCompleted() {
        System.out.printf("房屋 %d間完成建造",
                setBuildingStartWorkAndGetNum(HOUSE.list));

        System.out.printf("研究所 %d間完成建造",
                setBuildingStartWorkAndGetNum(LAB.list));

        System.out.printf("軍營 %d間完成建造",
                setBuildingStartWorkAndGetNum(BARRACKS.list));

        System.out.printf("伐木場 %d間完成建造",
                setBuildingStartWorkAndGetNum(SAW_MILL.list));

        System.out.printf("煉鋼廠 %d間完成建造",
                setBuildingStartWorkAndGetNum(STEEL_MILL.list));

        System.out.printf("兵工廠 %d間完成建造",
                setBuildingStartWorkAndGetNum(ARSENAL.list));

        System.out.printf("瓦斯廠 %d間完成建造",
                setBuildingStartWorkAndGetNum(GAS_MILL.list));

        System.out.printf("飛機工廠 %d間完成建造",
                setBuildingStartWorkAndGetNum(AIRPLANE_MILL.list));
    }

    /**
     * 建築開始運作，建築等級提升，關閉升級中狀態，開啟工作中狀態
     *
     * @param list
     * @return 完成建造的數量
     */
    private int setBuildingStartWorkAndGetNum(LinkedList<BuildingNode> list) {
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
        return sum;
    }

    /**
     * 能不能建造
     *
     * @param type
     */
    public boolean canBuild(BuildingType type, Resource resource) {
        switch (type) {
            case ARSENAL: {
                if (City.getTechLevel() < ARSENAL.instance.getTechLevelNeedBuild()) {
                    return false;
                }
            }
            case GAS_MILL: {
                if (City.getTechLevel() < GAS_MILL.instance.getTechLevelNeedBuild()) {
                    return false;
                }
            }
            case AIRPLANE_MILL: {
                if (City.getTechLevel() < AIRPLANE_MILL.instance.getTechLevelNeedBuild()) {
                    return false;
                }
            }
        }
        return enoughResourceBuild(resource,type);
    }

    /**
     * 判斷該建築夠不夠資源建造
     * @param resource 資源
     * @param type 建築種類
     * @return
     */
    private boolean enoughResourceBuild(Resource resource,BuildingType type){
        return resource.getTotalWood() >= type.instance.getWoodCostCreate()
                && resource.getTotalSteel() >= type.instance.getSteelCostCreate()
                && resource.getTotalGas() >= type.instance.getSteelCostCreate();
    }

    /**
     * 顯示每種建築物可以升級的數量
     */
    public void showCanUpgradeBuildingNum(Resource resource) {
        //若沒有研究所不可以升級
        if (freeLadNum == 0) {
            System.out.println("沒有研究所可以升級建築");
        }else {
            System.out.printf("%d.房屋：%d間可升級\n",
                    HOUSE.instance.getId(),
                    getCanUpgradeNum(HOUSE, resource));

            System.out.printf("%d.科技等級可升級", LAB.instance.getId());

            System.out.printf("%d.軍營：%d間可升級\n",
                    BARRACKS.instance.getId(),
                    getCanUpgradeNum(BARRACKS, resource));
            System.out.printf("%d.伐木場：%d間可升級\n",
                    SAW_MILL.instance.getId(),
                    getCanUpgradeNum(SAW_MILL, resource));
            System.out.printf("%d.煉鋼廠：%d間可升級\n",
                    STEEL_MILL.instance.getId(),
                    getCanUpgradeNum(STEEL_MILL, resource));

            System.out.printf("%d.瓦斯場：%d間可升級\n",
                    GAS_MILL.instance.getId(),
                    getCanUpgradeNum(GAS_MILL, resource));
            System.out.printf("%d.飛機工廠：%d可升級\n",
                    AIRPLANE_MILL.instance.getId(),
                    getCanUpgradeNum(AIRPLANE_MILL, resource));
        }
        if (freeArsenalNum == 0) {
            System.out.println("沒有兵工廠可以升級士兵/飛機");
        }else{
            System.out.printf("%d.士兵/飛機等級可升級", LAB.instance.getId());
        }
    }

    /**
     * 顯示該種類可以升級的建築細節
     * @param type 建築種類
     * @return 可以升級的建築陣列
     * 若選擇升級建築，但沒有閒置的研究所 -> null
     * 若選擇升級建築，但沒有閒置的研究所 -> null
     */
    public ArrayList<BuildingNode> showCanUpgradeTypeDetail(BuildingType type) {
        //若選擇升級建築，但沒有閒置的研究所
        if(type!=ARSENAL && freeLadNum==0){
            return null;
        }
        //若選擇升級建築，但沒有閒置的研究所
        if(type==ARSENAL && freeArsenalNum==0){
            return null;
        }
        ArrayList<BuildingNode> canUpgrade = new ArrayList<>(); //回傳可以升級的鏈表
        //若選擇研究所或兵工廠，沒有實際建築物可以升級，直接回傳
        if(type==LAB || type==ARSENAL){
            return canUpgrade;
        }
        int count = 1;
        for (int i = 0; i < type.list.size(); i++) {
            if(type.list.get(i).building.isReadyToUpgrade()){
                BuildingNode buildingNode = type.list.get(i);
                System.out.println( count++ + ". " + buildingNode.building.toString() + " 當前效果：" + buildingNode.building.buildingDetail()
                        + "\n升級後：" + buildingNode.building.buildingUpgradeDetail()
                        + "\n---------------------------");
                canUpgrade.add(type.list.get(i));
            }
        }
        return canUpgrade;
    }

    /**
     * 升級
     * @param node 指定要升級的建築
     * @param resource 城市資源
     */
    public void upgrade(BuildingNode node , Resource resource) {
        alreadyTakeResource(resource,
                node.building.getWoodCostLevelUp(),
                node.building.getSteelCostLevelUp(),
                node.building.getGasCostLevelUp());
        node.upgradeStartTime = City.getGameTime();
        node.upgradeEndTime = City.getGameTime() + node.building.getUpgradeTime();
        node.building.setUpgrading(true); //設為升級中
        node.building.setWorking(false); //停工
        node.building.setReadyToUpgrade(false); //非可升級狀態
        freeLadNum--; //占用研究所資源
        if (freeLadNum <= 0) {
            freeLadNum = 0;
        }
        return;
    }

    /**
     * 完成建造和升級的工作
     * 完成科技等級升級
     * 完成士兵/飛機等級升級
     */
    public void completeJob() {
        //建立完成
        showBuildCompleted();
        //升級完成
        showUpgradeCompleted();
        //科技等級升級
        if (City.getGameTime() - techLevelStartUpgradeTime - TECH_LEVEL_UPGRADE_TIME == 0) {
            City.addTechLevel();
            freeLadNum++;
        }
        //士兵等級升級
        if (City.getGameTime() - soldierLevelStartUpgradeTime - SOLDIER_LEVEL_UPGRADE_TIME == 0) {
            City.addTechLevel();
            freeArsenalNum++;
        }
        //飛機等級升級
        if (City.getGameTime() - planeLevelStartUpgradeTime - PLANE_LEVEL_UPGRADE_TIME == 0) {
            City.addTechLevel();
            freeArsenalNum++;
        }
    }

    /**
     * 所有完成升級的建築升級
     */
    public void showUpgradeCompleted() {
        System.out.printf("房屋 %d間完成升級",
                finishUpgradeBuilding(HOUSE.list));

        System.out.printf("研究所 %d間完成升級",
                finishUpgradeBuilding(LAB.list));

        System.out.printf("軍營 %d間完成升級",
                finishUpgradeBuilding(BARRACKS.list));

        System.out.printf("伐木場 %d間完成升級",
                finishUpgradeBuilding(SAW_MILL.list));

        System.out.printf("煉鋼廠 %d間完成升級",
                finishUpgradeBuilding(STEEL_MILL.list));

        System.out.printf("兵工廠 %d間完成升級",
                finishUpgradeBuilding(ARSENAL.list));

        System.out.printf("瓦斯廠 %d間完成升級",
                finishUpgradeBuilding(GAS_MILL.list));

        System.out.printf("飛機工廠 %d間完成升級",
                finishUpgradeBuilding(AIRPLANE_MILL.list));
    }

    /**
     * 將建築完成升級
     *
     * @param list
     */
    private int finishUpgradeBuilding(LinkedList<BuildingNode> list) {
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
        return sum;
    }

    /**
     * 取得各建築可以升級的數量
     *
     * @param type     建築種類
     * @param resource 都市的物資
     * @return 可以升級數量
     */
    public int getCanUpgradeNum(BuildingType type, Resource resource) {
        switch (type) {
            //兵工廠直接升級士兵/飛機等級
            case ARSENAL:{
                if(freeArsenalNum!=0){
                    return 1;
                }else{
                    return 0;
                }
            }
            case BARRACKS: {
                if (City.getTechLevel() < BARRACKS.instance.getTechLevelNeedBuild()) {
                    return 0;
                }
            }
            case HOUSE: {
                if (City.getTechLevel() < HOUSE.instance.getTechLevelNeedBuild()) {
                    return 0;
                }
            }
            //研究所直接升級科技等級
            case LAB:{
                if(freeLadNum!=0){
                    return 1;
                }else{
                    return 0;
                }
            }
            case SAW_MILL: {
                if (City.getTechLevel() < SAW_MILL.instance.getTechLevelNeedBuild()) {
                    return 0;
                }
            }
            case STEEL_MILL: {
                if (City.getTechLevel() < STEEL_MILL.instance.getTechLevelNeedBuild()) {
                    return 0;
                }
            }
            case GAS_MILL: {
                if (City.getTechLevel() < GAS_MILL.instance.getTechLevelNeedBuild()) {
                    return 0;
                }
            }
            case AIRPLANE_MILL: {
                if (City.getTechLevel() < AIRPLANE_MILL.instance.getTechLevelNeedBuild()) {
                    return 0;
                }
            }
        }
        int sum = 0;
        for (BuildingNode node : type.list) {
            //建築是否可升級 && 木頭夠 && 鋼鐵夠 && 瓦斯夠
            if (node.building.isReadyToUpgrade() && enoughResourceUpgrade(resource,node)) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * 判斷該建築夠不夠資源升級
     * @param resource 資源
     * @param buildingNode 建築
     * @return
     */
    private boolean enoughResourceUpgrade(Resource resource,BuildingNode buildingNode){
        return resource.getTotalWood() >= buildingNode.building.getGasCostLevelUp()
                && resource.getTotalSteel() >= buildingNode.building.getSteelCostLevelUp()
                && resource.getTotalGas() >= buildingNode.building.getGasCostLevelUp();
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
        freeLadNum--;
    }

    /**
     * 升級士兵等級
     */
    public void upgradeSoldier() {
        soldierLevelStartUpgradeTime = City.getGameTime();
        freeArsenalNum--;
    }

    /**
     * 升級飛機等級
     */
    public void upgradePlane() {
        planeLevelStartUpgradeTime = City.getGameTime();
        freeArsenalNum--;
    }

    /**
     * 生成的居民數量
     *
     * @return 生成的人數
     */
    public int getNewCitizenNum(Resource resource) {
        int newPeopleCount = 0;
        for (BuildingNode buildingNode : HOUSE.list) {
            //如果建築在運作
            if (buildingNode.building.isWorking() && buildingNode.building instanceof House) {
                //如果物資不夠自動生產
                if (!enoughResourceProduce(resource,buildingNode)){
                    buildingNode.building.setWorking(false); //將建築關閉
                    continue;
                }
                alreadyTakeResource(resource,
                        buildingNode.building.getWoodForProduction(),
                        buildingNode.building.getSteelForProduction(),
                        buildingNode.building.getGasForProduction());
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
    public int getNewSoldierNum(Resource resource) {
        int newSoldierCount = 0;
        for (BuildingNode buildingNode : BARRACKS.list) {
            if (buildingNode.building.isWorking() && buildingNode.building instanceof Barracks) {
                //如果物資不夠自動生產
                if (!enoughResourceProduce(resource,buildingNode)){
                    buildingNode.building.setWorking(false); //將建築關閉
                    continue;
                }
                alreadyTakeResource(resource,
                        buildingNode.building.getWoodForProduction(),
                        buildingNode.building.getSteelForProduction(),
                        buildingNode.building.getGasForProduction());
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
     * 生成的飛機數量
     *
     * @return 生成的飛機數
     */
    public int getNewPlaneNum(Resource resource) {
        int newPlaneCount = 0;
        for (BuildingNode buildingNode : AIRPLANE_MILL.list) {
            if (buildingNode.building.isWorking() && buildingNode.building instanceof AirPlaneMill) {
                //如果物資不夠自動生產
                if (!enoughResourceProduce(resource,buildingNode)){
                    buildingNode.building.setWorking(false); //將建築關閉
                    continue;
                }
                alreadyTakeResource(resource,
                        buildingNode.building.getWoodForProduction(),
                        buildingNode.building.getSteelForProduction(),
                        buildingNode.building.getGasForProduction());
                //當前時間-建造完成時間，若為3的倍數，產生士兵
                if ((City.getGameTime() - buildingNode.buildEndTime) % 3 == 0) {
                    Barracks barracks = (Barracks) buildingNode.building;
                    newPlaneCount += barracks.produceSoldier();
                }
            }
        }
        return newPlaneCount;
    }

    /**
     * 判斷該建築夠不夠資源自動生產
     * @param resource 資源
     * @param buildingNode 建築
     * @return
     */
    private boolean enoughResourceProduce(Resource resource,BuildingNode buildingNode){
        return resource.getTotalWood() >= buildingNode.building.getWoodForProduction()
                && resource.getTotalSteel() >= buildingNode.building.getSteelForProduction()
                && resource.getTotalGas() >= buildingNode.building.getGasForProduction();
    }

    /**
     * 取得木頭的生產速度
     *
     * @return 生產木頭的速度
     */
    public int getWoodSpeed() {
        if (SAW_MILL.list.size() != 0) {
            if (SAW_MILL.list.get(0).building instanceof SawMill) {
                //取最高等級的採集速度
                SawMill sawMill = (SawMill) SAW_MILL.list.get(0).building;
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
        if (STEEL_MILL.list.size() != 0) {
            if (STEEL_MILL.list.get(0).building instanceof SteelMill) {
                //取最高等級的採集速度
                SteelMill steelMill = (SteelMill) STEEL_MILL.list.get(0).building;
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
        for (BuildingNode node : GAS_MILL.list) {
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

        info.append("房屋：").append(countBuildingNum(HOUSE.list)).append("間\n");
        info.append("研究所：").append(countBuildingNum(LAB.list)).append("間\n");
        info.append("兵營：").append(countBuildingNum(BARRACKS.list)).append("間\n");
        info.append("兵工廠：").append(countBuildingNum(ARSENAL.list)).append("間\n");
        info.append("伐木場：").append(countBuildingNum(SAW_MILL.list)).append("間\n");
        info.append("煉鋼廠：").append(countBuildingNum(STEEL_MILL.list)).append("間\n");
        info.append("瓦斯廠：").append(countBuildingNum(GAS_MILL.list)).append("間\n");
        info.append("飛機工廠：").append(countBuildingNum(AIRPLANE_MILL.list)).append("間\n");

        info.append("=====正在進行中的工程=====\n");

        int[] record = new int[3];
        countStatus(HOUSE.list, record);
        info.append("房屋：").append(getStatusWord(record));

        countStatus(LAB.list, record);
        info.append("\n研究所：").append(getStatusWord(record));

        countStatus(BARRACKS.list, record);
        info.append("\n兵營：").append(getStatusWord(record));

        countStatus(ARSENAL.list, record);
        info.append("\n兵工廠：").append(getStatusWord(record));

        countStatus(SAW_MILL.list, record);
        info.append("\n伐木場：").append(getStatusWord(record));

        countStatus(STEEL_MILL.list, record);
        info.append("\n煉鋼廠：").append(getStatusWord(record));

        countStatus(GAS_MILL.list, record);
        info.append("\n瓦斯廠：").append(getStatusWord(record));

        countStatus(AIRPLANE_MILL.list, record);
        info.append("\n飛機工廠：").append(getStatusWord(record));

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
     * 各種建築的數量
     *
     * @param list 哪種建築
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

    /**
     * 取得閒置的研究所數量
     * @return 閒置的研究所數量
     */
    public int getFreeLadNum() {
        return freeLadNum;
    }

    /**
     * 取得閒置的兵工廠數量
     * @return 閒置的兵工廠數量
     */
    public int getFreeArsenalNum() {
        return freeArsenalNum;
    }


}
