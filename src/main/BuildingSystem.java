package main;

import buildings.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static main.BuildingSystem.BuildingType.*;

/**
 * 城市中的建築運作系統
 */
public class BuildingSystem {

    /**
     * 飛機升級時間
     */
    public static final int PLANE_LEVEL_UPGRADE_TIME = 48;
    /**
     * 士兵升級時間
     */
    public static final int SOLDIER_LEVEL_UPGRADE_TIME = 48;
    /**
     * 科技升級時間
     */
    public static final int TECH_LEVEL_UPGRADE_TIME = 24;
    /**
     * 此回合有升級科技
     */
    private boolean isRecentlyUpgradeTech;
    /**
     * 此回合有升級士兵
     */
    private boolean isRecentlyUpgradeArmySoldier;
    /**
     * 此回合有升級飛機
     */
    private boolean isRecentlyUpgradeAirForceSoldier;

    /**
     * 是否已在升級士兵
     */
    private boolean isUpgradingSoldier;
    /**
     * 是否已在升級飛機
     */
    private boolean isUpgradingPlane;
    /**
     * 是否已在升級科技
     */
    private boolean isUpgradingTech;
    /**
     * 紀錄此波攻擊被炸毀的建築
     */
    private ArrayList<Building> damageBuilding;

    /**
     * 建築的種類和實體與儲存鏈表
     */
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

        /**
         * 從int得到BuildingType
         * @param option 數字選擇
         * @return BuildingType
         */
        public static BuildingType getBuildingTypeByInt(int option) {
            for (BuildingType type : values()) {
                if (type.instance.getId() == option) {
                    return type;
                }
            }
            return null;
        }

        public Building instance() {
            return instance;
        }
    }

    /**
     * 儲存各個建築的建造/升級時間
     */
    public class BuildingNode {
        /**
         * 建築本身
         */
        private Building building;
        /**
         * 建造開始的時間
         */
        int buildStartTime;
        /**
         * 建造結束的時間
         */
        int buildEndTime;
        /**
         * 升級開始時間
         */
        int upgradeStartTime;
        /**
         * 升級結束的時間
         */
        int upgradeEndTime;
        /**
         * 上次開起運作的時間
         */
        int updateStartTime;

        public BuildingNode(Building building) {
            this.building = building;
        }

        /**
         * 取得這間建築
         *
         * @return 建築實體
         */
        public Building getBuilding() {
            return building;
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
    private int freeLabNum;
    /**
     * 閒置兵工廠數量
     */
    private int freeArsenalNum;
    /**
     * 建築數量
     */
    private int buildingNum;

    public BuildingSystem() {
        techLevelStartUpgradeTime = 0;
        buildingNum = 0;
        isUpgradingSoldier = false;
        isUpgradingPlane = false;
        isUpgradingTech = false;
    }

    /**
     * 建造
     *
     * @param type     建築種類
     * @param resource 城市資源
     */
    public void build(BuildingType type, Resource resource) {
        BuildingNode newBuilding;
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
                newBuilding = new BuildingNode(new House());
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
            default: {
                newBuilding = new BuildingNode(new House());
            }
        }
        //消耗資源
        newBuilding.building.takeResourceBuild(resource);
        //設置開始建造時間
        newBuilding.buildStartTime = City.getGameTime();
        //設定結束時間
        newBuilding.buildEndTime = City.getGameTime() + newBuilding.building.getBuildTime();
        //尚未開始運作
        newBuilding.building.setWorking(false);
        //設置為升級中
        newBuilding.building.setUpgrading(true);
        //設置為不可升級狀態
        newBuilding.building.setReadyToUpgrade(false);
        //添加進該分類的鏈表中
        type.list.add(newBuilding);
    }

    /**
     * 所有建造完成的建築開始運作
     */
    public void showBuildCompleted() {
        if (setBuildingStartWork(HOUSE.list) != 0) {
            System.out.printf("房屋 %d間完成建造\n",
                    setBuildingStartWork(HOUSE.list));
        }

        if (setBuildingStartWork(LAB.list) != 0) {
            System.out.printf("研究所 %d間完成建造\n",
                    setBuildingStartWork(LAB.list));
        }

        if (setBuildingStartWork(BARRACKS.list) != 0) {
            System.out.printf("軍營 %d間完成建造\n",
                    setBuildingStartWork(BARRACKS.list));
        }

        if (setBuildingStartWork(SAW_MILL.list) != 0) {
            System.out.printf("伐木場 %d間完成建造\n",
                    setBuildingStartWork(SAW_MILL.list));
        }

        if (setBuildingStartWork(STEEL_MILL.list) != 0) {
            System.out.printf("煉鋼廠 %d間完成建造\n",
                    setBuildingStartWork(STEEL_MILL.list));
        }

        if (setBuildingStartWork(ARSENAL.list) != 0) {
            System.out.printf("兵工廠 %d間完成建造\n",
                    setBuildingStartWork(ARSENAL.list));
        }

        if (setBuildingStartWork(GAS_MILL.list) != 0) {
            System.out.printf("瓦斯廠 %d間完成建造\n",
                    setBuildingStartWork(GAS_MILL.list));
        }

        if (setBuildingStartWork(AIRPLANE_MILL.list) != 0) {
            System.out.printf("飛機工廠 %d間完成建造\n",
                    setBuildingStartWork(AIRPLANE_MILL.list));
        }
    }

    /**
     * 該類建築統一完成建造->建築開始運作，建築等級提升，關閉升級中狀態，開啟工作中狀態
     *
     * @param list 該類建築的鏈表
     * @return 完成建造的數量
     */
    private int setBuildingStartWork(LinkedList<BuildingNode> list) {
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
                    freeLabNum++;
                }
                if ((node.building instanceof Arsenal)) {
                    freeArsenalNum++;
                }
            }
        }
        return sum;
    }

    /**
     * 能不能建造
     *
     * @param type 要檢查能不能建造的建築種類
     */
    public boolean canBuild(BuildingType type, Resource resource) {
        if (City.getTechLevel() < type.instance.getTechLevelNeedBuild()) {
            return false;
        }
        return type.instance.isEnoughBuild(resource);
    }

    /**
     * 顯示每種建築物可以升級的數量
     *
     * @param resource 城市資源
     */
    public void showCanUpgradeBuildingNum(Resource resource) {
        //若沒有研究所不可以升級
        if (freeLabNum == 0) {
            System.out.println("沒有研究所可以升級建築");
        } else {
            System.out.printf("%d.房屋：%d間可升級，資源需求：木材:%d, 鋼鐵:%d, 瓦斯:%d，科技等級需求：%d\n",
                    HOUSE.instance.getId(),
                    getCanUpgradeNum(HOUSE, resource),
                    HOUSE.instance.getWoodCostLevelUp(),
                    HOUSE.instance.getSteelCostLevelUp(),
                    HOUSE.instance.getGasCostLevelUp(),
                    HOUSE.instance.getTechLevelNeedUpgrade());

            System.out.printf("%d.科技等級升級，資源需求：木材:%d, 鋼鐵:%d, 瓦斯:%d\n",
                    LAB.instance.getId(),
                    LAB.instance.getWoodCostLevelUp(),
                    LAB.instance.getSteelCostLevelUp(),
                    LAB.instance.getGasCostLevelUp());

            System.out.printf("%d.軍營：%d間可升級，資源需求：木材:%d, 鋼鐵:%d, 瓦斯:%d，科技等級需求：%d\n",
                    BARRACKS.instance.getId(),
                    getCanUpgradeNum(BARRACKS, resource),
                    BARRACKS.instance.getWoodCostLevelUp(),
                    BARRACKS.instance.getSteelCostLevelUp(),
                    BARRACKS.instance.getGasCostLevelUp(),
                    BARRACKS.instance.getTechLevelNeedUpgrade());
            System.out.printf("%d.伐木場：%d間可升級，資源需求：木材:%d, 鋼鐵:%d, 瓦斯:%d，科技等級需求：%d\n",
                    SAW_MILL.instance.getId(),
                    getCanUpgradeNum(SAW_MILL, resource),
                    SAW_MILL.instance.getWoodCostLevelUp(),
                    SAW_MILL.instance.getSteelCostLevelUp(),
                    SAW_MILL.instance.getGasCostLevelUp(),
                    SAW_MILL.instance.getTechLevelNeedUpgrade());
            System.out.printf("%d.煉鋼廠：%d間可升級，資源需求：木材:%d, 鋼鐵:%d, 瓦斯:%d，科技等級需求：%d\n",
                    STEEL_MILL.instance.getId(),
                    getCanUpgradeNum(STEEL_MILL, resource),
                    STEEL_MILL.instance.getWoodCostLevelUp(),
                    STEEL_MILL.instance.getSteelCostLevelUp(),
                    STEEL_MILL.instance.getGasCostLevelUp(),
                    STEEL_MILL.instance.getTechLevelNeedUpgrade());
            System.out.printf("%d.瓦斯廠：%d間可升級，資源需求：木材:%d, 鋼鐵:%d, 瓦斯:%d，科技等級需求：%d\n",
                    GAS_MILL.instance.getId(),
                    getCanUpgradeNum(GAS_MILL, resource),
                    GAS_MILL.instance.getWoodCostLevelUp(),
                    GAS_MILL.instance.getSteelCostLevelUp(),
                    GAS_MILL.instance.getGasCostLevelUp(),
                    GAS_MILL.instance.getTechLevelNeedUpgrade());
            System.out.printf("%d.飛機工廠：%d間可升級，資源需求：木材:%d, 鋼鐵:%d, 瓦斯:%d，科技等級需求：%d\n",
                    AIRPLANE_MILL.instance.getId(),
                    getCanUpgradeNum(AIRPLANE_MILL, resource),
                    AIRPLANE_MILL.instance.getWoodCostLevelUp(),
                    AIRPLANE_MILL.instance.getSteelCostLevelUp(),
                    AIRPLANE_MILL.instance.getGasCostLevelUp(),
                    AIRPLANE_MILL.instance.getTechLevelNeedUpgrade());
        }
        if (freeArsenalNum == 0) {
            System.out.println("沒有兵工廠可以升級士兵/飛機");
        } else {
            System.out.printf("%d.士兵/飛機等級可升級，資源需求：木材:%d, 鋼鐵:%d, 瓦斯:%d\n",
                    ARSENAL.instance.getId(),
                    ARSENAL.instance.getWoodCostLevelUp(),
                    ARSENAL.instance.getSteelCostLevelUp(),
                    ARSENAL.instance.getGasCostLevelUp());
        }
    }

    /**
     * 顯示該種類可以升級的建築細節
     *
     * @param type 建築種類
     * @return 可以升級的建築陣列
     * 若選擇升級建築，但沒有閒置的研究所 -> null
     * 若選擇升級軍力，但沒有閒置的兵工廠 -> null
     */
    public ArrayList<BuildingNode> showAndGetCanUpgradeTypeDetail(BuildingType type) {
        //若選擇升級建築，但沒有閒置的研究所
        if (type != ARSENAL && freeLabNum == 0) {
            return null;
        }
        //若選擇升級武力，但沒有閒置的兵工廠
        if (type == ARSENAL && freeArsenalNum == 0) {
            return null;
        }

        ArrayList<BuildingNode> canUpgrade = new ArrayList<>(); //回傳可以升級的鏈表
        //若選擇研究所或兵工廠，沒有實際建築物可以升級，直接回傳
        if (type == LAB || type == ARSENAL) {
            return canUpgrade;
        }
        int count = 1;
        for (int i = 0; i < type.list.size(); i++) {
            if (type.list.get(i).building.isReadyToUpgrade()) {
                BuildingNode buildingNode = type.list.get(i);
                System.out.println(count++ + ". " + buildingNode.building.toString() + " 當前效果：" + buildingNode.building.buildingDetail(buildingNode.building.getLevel())
                        + "\n升級後：" + buildingNode.building.buildingDetail(buildingNode.building.getLevel() + 1)
                        + "\n---------------------------");
                canUpgrade.add(type.list.get(i));
            }
        }
        return canUpgrade;
    }

    /**
     * 升級
     *
     * @param node     指定要升級的建築
     * @param resource 城市資源
     */
    public void upgrade(BuildingNode node, Resource resource) {
        node.building.takeResourceUpgrade(resource);
        node.upgradeStartTime = City.getGameTime();
        node.upgradeEndTime = City.getGameTime() + node.building.getUpgradeTime();
        node.building.setUpgrading(true); //設為升級中
        node.building.setWorking(false); //停工
        node.building.setReadyToUpgrade(false); //非可升級狀態
        freeLabNum--; //占用研究所資源
        if (freeLabNum <= 0) {
            freeLabNum = 0;
        }
    }

    /**
     * 完成建造和升級的工作
     * 完成科技等級升級
     * 完成士兵/飛機等級升級
     */
    public void completeJob() {
        isRecentlyUpgradeTech = false;
        isRecentlyUpgradeArmySoldier = false;
        isRecentlyUpgradeAirForceSoldier = false;
        //建立完成
        showBuildCompleted();
        //升級完成
        showUpgradeCompleted();
        //科技等級升級
        if (isUpgradingTech && City.getGameTime() - techLevelStartUpgradeTime - TECH_LEVEL_UPGRADE_TIME == 0) {
            if (LAB.instance instanceof Lab) {
                Lab lab = (Lab) LAB.instance;
                lab.levelUpTechResource(lab.getLevel() + 1);
            }
            isRecentlyUpgradeTech = true;
            City.addTechLevel();
            freeLabNum++;
            isUpgradingTech = false;
        }
        //士兵等級升級
        if (isUpgradingSoldier && City.getGameTime() - soldierLevelStartUpgradeTime - SOLDIER_LEVEL_UPGRADE_TIME == 0) {
            freeArsenalNum++;
            isUpgradingSoldier = false;
        }
        //飛機等級升級
        if (isUpgradingPlane && City.getGameTime() - planeLevelStartUpgradeTime - PLANE_LEVEL_UPGRADE_TIME == 0) {
            freeArsenalNum++;
            isUpgradingPlane = false;
        }
    }

    /**
     * 所有完成升級的建築升級
     */
    public void showUpgradeCompleted() {
        if (finishUpgradeBuilding(HOUSE.list) != 0) {
            System.out.printf("房屋 %d間完成升級\n",
                    finishUpgradeBuilding(HOUSE.list));
        }

        if (finishUpgradeBuilding(LAB.list) != 0) {
            System.out.printf("研究所 %d間完成升級\n",
                    finishUpgradeBuilding(LAB.list));
        }

        if (finishUpgradeBuilding(BARRACKS.list) != 0) {
            System.out.printf("軍營 %d間完成升級\n",
                    finishUpgradeBuilding(BARRACKS.list));
        }

        if (finishUpgradeBuilding(SAW_MILL.list) != 0) {
            System.out.printf("伐木場 %d間完成升級\n",
                    finishUpgradeBuilding(SAW_MILL.list));
        }

        if (finishUpgradeBuilding(STEEL_MILL.list) != 0) {
            System.out.printf("煉鋼廠 %d間完成升級\n",
                    finishUpgradeBuilding(STEEL_MILL.list));
        }

        if (finishUpgradeBuilding(ARSENAL.list) != 0) {
            System.out.printf("兵工廠 %d間完成升級\n",
                    finishUpgradeBuilding(ARSENAL.list));
        }

        if (finishUpgradeBuilding(GAS_MILL.list) != 0) {
            System.out.printf("瓦斯廠 %d間完成升級\n",
                    finishUpgradeBuilding(GAS_MILL.list));
        }

        if (finishUpgradeBuilding(AIRPLANE_MILL.list) != 0) {
            System.out.printf("飛機工廠 %d間完成升級\n",
                    finishUpgradeBuilding(AIRPLANE_MILL.list));
        }
    }

    /**
     * 該類建築統一完成升級
     *
     * @param list 該類建築的鏈表
     * @return 回傳有幾間完成升級
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
                freeLabNum++; //釋放研究所資源
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
            case ARSENAL: {
                if (freeArsenalNum != 0 && ARSENAL.instance.isEnoughUpgrade(resource)) {
                    return 1;
                } else {
                    return 0;
                }
            }
            //研究所直接升級科技等級
            case LAB: {
                if (freeLabNum != 0 && LAB.instance.isEnoughUpgrade(resource)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
        if (City.getTechLevel() < type.instance.getTechLevelNeedUpgrade()) {
            return 0;
        }
        int sum = 0;
        for (BuildingNode node : type.list) {
            //建築是否可升級 && 木頭夠 && 鋼鐵夠 && 瓦斯夠
            if (node.building.isReadyToUpgrade() && node.building.isEnoughUpgrade(resource)) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * 取得當前建築數量
     *
     * @return 當前建築數量
     */
    public int getBuildingNum() {
        return buildingNum;
    }

    /**
     * 升級科技等級
     */
    public void upgradeTechLevel(Resource resource) {
        LAB.instance.takeResourceUpgrade(resource);
        //紀錄開始升級的時間點
        techLevelStartUpgradeTime = City.getGameTime();
        freeLabNum--;
        isUpgradingTech = true;
    }

    /**
     * 升級士兵等級
     */
    public void upgradeSoldier(Resource resource) {
        ARSENAL.instance.takeResourceUpgrade(resource);
        soldierLevelStartUpgradeTime = City.getGameTime();
        freeArsenalNum--;
        isUpgradingSoldier = true;
    }

    /**
     * 升級飛機等級
     */
    public void upgradePlane(Resource resource) {
        ARSENAL.instance.takeResourceUpgrade(resource);
        planeLevelStartUpgradeTime = City.getGameTime();
        freeArsenalNum--;
        isUpgradingPlane = false;
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
                if ((City.getGameTime() - buildingNode.updateStartTime) % 24 == 0) {
                    House house = (House) buildingNode.building;
                    newPeopleCount += house.produceCitizen();
                    if (!buildingNode.building.isEnoughProduction(resource)) {
                        buildingNode.building.setWorking(false); //將建築關閉
                    } else {
                        buildingNode.updateStartTime = City.getGameTime();
                        buildingNode.building.takeResourceProduce(resource);
                    }
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
    public int getNewArmyNum(Resource resource) {
        int newSoldierCount = 0;
        for (BuildingNode buildingNode : BARRACKS.list) {
            //如果建築在運作
            if (buildingNode.building.isWorking() && buildingNode.building instanceof House) {
                //當前時間-上次啟動的時間 滿3小時->生產士兵
                if ((City.getGameTime() - buildingNode.updateStartTime) % 3 == 0) {
                    Barracks barracks = (Barracks) buildingNode.building;
                    newSoldierCount += barracks.produceSoldier();
                    if (!buildingNode.building.isEnoughProduction(resource)) {
                        buildingNode.building.setWorking(false); //將建築關閉
                    } else {
                        buildingNode.updateStartTime = City.getGameTime();
                        buildingNode.building.takeResourceProduce(resource);
                    }
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
            //如果建築在運作
            if (buildingNode.building.isWorking() && buildingNode.building instanceof House) {
                if ((City.getGameTime() - buildingNode.updateStartTime) % 3 == 0) {
                    AirPlaneMill airPlaneMill = (AirPlaneMill) buildingNode.building;
                    newPlaneCount += airPlaneMill.produceAirPlane();
                    if (!buildingNode.building.isEnoughProduction(resource)) {
                        buildingNode.building.setWorking(false); //將建築關閉
                    } else {
                        buildingNode.updateStartTime = City.getGameTime();
                        buildingNode.building.takeResourceProduce(resource);
                    }
                }
            }
        }
        return newPlaneCount;
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
     * @return 建築資訊文字
     */
    public String showInfo() {
        StringBuilder info = new StringBuilder();

        info.append("房屋：").append("\t" ).append( countBuildingNum(HOUSE.list)).append("間\n");
        info.append("研究所：").append("\t" ).append( countBuildingNum(LAB.list)).append("間\n");
        info.append("軍營：").append("\t" ).append( countBuildingNum(BARRACKS.list)).append("間\n");
        info.append("兵工廠：").append("\t" ).append( countBuildingNum(ARSENAL.list)).append("間\n");
        info.append("伐木場：").append("\t" ).append( countBuildingNum(SAW_MILL.list)).append("間\n");
        info.append("煉鋼廠：").append("\t" ).append( countBuildingNum(STEEL_MILL.list)).append("間\n");
        info.append("瓦斯廠：").append("\t" ).append( countBuildingNum(GAS_MILL.list)).append("間\n");
        info.append("飛機工廠：").append(countBuildingNum(AIRPLANE_MILL.list)).append("間\n");

        info.append("=====正在進行中的工程=====\n");

        int[] record = new int[3];
        countStatus(HOUSE.list, record);
        info.append("房屋：").append("\t").append( getStatusWord(record));

        countStatus(LAB.list, record);
        info.append("\n研究所：").append("\t").append( getStatusWord(record));

        countStatus(BARRACKS.list, record);
        info.append("\n軍營：").append("\t" ).append( getStatusWord(record));

        countStatus(ARSENAL.list, record);
        info.append("\n兵工廠：").append("\t" ).append( getStatusWord(record));

        countStatus(SAW_MILL.list, record);
        info.append("\n伐木場：").append("\t" ).append( getStatusWord(record));

        countStatus(STEEL_MILL.list, record);
        info.append("\n煉鋼廠：").append("\t").append( getStatusWord(record));

        countStatus(GAS_MILL.list, record);
        info.append("\n瓦斯廠：").append("\t" ).append( getStatusWord(record));

        countStatus(AIRPLANE_MILL.list, record);
        info.append("\n飛機工廠：").append("\t" ).append( getStatusWord(record));

        if (isUpgradingTech) {
            info.append("\n科技升級中，尚餘：").append(techLevelStartUpgradeTime + TECH_LEVEL_UPGRADE_TIME - City.getGameTime()).append("小時");
        }
        if (isUpgradingSoldier) {
            info.append("\n士兵升級中，尚餘：").append(soldierLevelStartUpgradeTime + SOLDIER_LEVEL_UPGRADE_TIME - City.getGameTime()).append("小時");
        }
        if (isUpgradingPlane) {
            info.append("\n飛機升級中，尚餘：").append(planeLevelStartUpgradeTime + PLANE_LEVEL_UPGRADE_TIME - City.getGameTime()).append("小時");
        }

        return info.toString();
    }

    /**
     * 取得建築狀態數量敘述
     *
     * @param record 傳入獲取數量陣列
     * @return 文字敘述
     */
    private String getStatusWord(int[] record) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(record[0]).append("間 工作中");
        if (record[1] != 0) {
            stringBuilder.append(" / ").append(record[1]).append("間 建造中");
        }
        if (record[2] != 0) {
            stringBuilder.append(" / ").append(record[2]).append("間 升級中");
        }
        return stringBuilder.toString();
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
     */
    private void countStatus(LinkedList<BuildingNode> list, int[] record) {
        record[0] = 0;
        record[1] = 0;
        record[2] = 0;
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
     *
     * @return 閒置的研究所數量
     */
    public int getFreeLabNum() {
        return freeLabNum;
    }

    /**
     * 取得閒置的兵工廠數量
     *
     * @return 閒置的兵工廠數量
     */
    public int getFreeArsenalNum() {
        return freeArsenalNum;
    }

    /**
     * 取得停工中的非升級中建築
     *
     * @return 停工中的非升級中建築陣列
     */
    public ArrayList<BuildingNode> getNotWorkingBuildingList() {
        ArrayList<BuildingNode> notWorking = new ArrayList<>();
        for (int i = 0; i < HOUSE.list.size(); i++) {
            if (!HOUSE.list.get(i).building.isWorking() && !HOUSE.list.get(i).building.isUpgrading()) {
                notWorking.add(HOUSE.list.get(i));
            }
        }
        for (int i = 0; i < BARRACKS.list.size(); i++) {
            if (!BARRACKS.list.get(i).building.isWorking() && !BARRACKS.list.get(i).building.isUpgrading()) {
                notWorking.add(BARRACKS.list.get(i));
            }
        }
        for (int i = 0; i < AIRPLANE_MILL.list.size(); i++) {
            if (!AIRPLANE_MILL.list.get(i).building.isWorking() && !AIRPLANE_MILL.list.get(i).building.isUpgrading()) {
                notWorking.add(AIRPLANE_MILL.list.get(i));
            }
        }
        return notWorking;
    }

    /**
     * 取得工作中建築
     *
     * @return 工作中建築陣列
     */
    public ArrayList<BuildingNode> getWorkingBuildingList() {
        ArrayList<BuildingNode> notWorking = new ArrayList<>();
        for (int i = 0; i < HOUSE.list.size(); i++) {
            if (HOUSE.list.get(i).building.isWorking()) {
                notWorking.add(HOUSE.list.get(i));
            }
        }
        for (int i = 0; i < BARRACKS.list.size(); i++) {
            if (BARRACKS.list.get(i).building.isWorking()) {
                notWorking.add(BARRACKS.list.get(i));
            }
        }
        for (int i = 0; i < AIRPLANE_MILL.list.size(); i++) {
            if (AIRPLANE_MILL.list.get(i).building.isWorking()) {
                notWorking.add(AIRPLANE_MILL.list.get(i));
            }
        }
        return notWorking;
    }

    /**
     * 讓指定建築物停工
     *
     * @param buildingNode 指定建築物
     */
    public void setStop(BuildingNode buildingNode) {
        if (buildingNode.building instanceof Lab) {
            freeLabNum--;
        }
        if (buildingNode.building instanceof Arsenal) {
            freeArsenalNum--;
        }
        buildingNode.building.setWorking(false);
    }

    /**
     * 讓指定建築物開始運作
     *
     * @param buildingNode 指定開始運作
     */
    public void setStart(BuildingNode buildingNode) {
        if (buildingNode.building instanceof Lab) {
            freeLabNum++;
        }
        if (buildingNode.building instanceof Arsenal) {
            freeArsenalNum++;
        }
        buildingNode.building.setWorking(true);
        //紀錄更新時間
        buildingNode.updateStartTime = City.getGameTime();
    }

    /**
     * @return 是否有升級科技
     */
    public boolean isRecentlyUpgradeTech() {
        return isRecentlyUpgradeTech;
    }

    /**
     * @return 是否有升級士兵
     */
    public boolean isRecentlyUpgradeArmySoldier() {
        return isRecentlyUpgradeArmySoldier;
    }

    /**
     * @return 是否有升級飛機
     */
    public boolean isRecentlyUpgradeAirForceSoldier() {
        return isRecentlyUpgradeAirForceSoldier;
    }

    /**
     * 是否正在升級士兵
     *
     * @return 是否正在升級士兵
     */
    public boolean isUpgradingSoldier() {
        return isUpgradingSoldier;
    }

    /**
     * 是否正在升級飛機
     *
     * @return 是否正在升級飛機
     */
    public boolean isUpgradingPlane() {
        return isUpgradingPlane;
    }

    /**
     * 是否正在升級科技
     *
     * @return 是否正在升級科技
     */
    public boolean isUpgradingTech() {
        return isUpgradingTech;
    }

    /**
     * 建築受到傷害
     *
     * @param damage 傷害值
     */
    public void getDamage(int damage) {
        ArrayList<Building> hpZeroBuilding = new ArrayList<>();

        //走訪每個BuildingType
        for (int i = 0; i < values().length; i++) {
            BuildingType type = values()[i];
            //如果type的鏈表的size不為0，依序開始攻擊建築
            if (type.list.size() != 0) {
                //走訪建築類別的鏈表
                for (int k = 0; k < type.list.size(); k++) {
                    Building building = type.list.get(k).building;
                    //讓建築受到傷害
                    building.getDamage(damage);
                    //建築hp為0就從鏈表刪除
                    if (building.getHp() <= 0) {
                        //將爆掉的建築進列表中紀錄
                        hpZeroBuilding.add(building);
                        type.list.remove(k--);
                    }
                }
            }
        }
        damageBuilding = hpZeroBuilding;
    }

    /**
     * 被炸毀的建築總數字串
     *
     * @return 各類建築被炸毀的總數字串
     */
    public String sumDamageBuilding() {
        if(damageBuilding==null){
            return "";
        }
        Map<BuildingType, Integer> sum = new HashMap<>();
        //走訪列表
        for (int i = 0; i < damageBuilding.size(); i++) {
            Building building = damageBuilding.get(i);
            //比對此建築是哪一個分類
            for (int k = 0; k < values().length; k++) {
                if (building.getId() == values()[k].instance().getId()) {
                    //如果sum中已存在此分類，總數+1
                    if (sum.containsKey(values()[k])) {
                        sum.put(values()[k], sum.get(values()[k]) + 1);
                    } else {
                        //還不存在的話，新增1
                        sum.put(values()[k], 1);
                    }
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        //轉成字串
        for (Map.Entry<BuildingType, Integer> entry : sum.entrySet()) {
            switch (entry.getKey()) {
                case HOUSE: {
                    builder.append("房屋");
                    break;
                }
                case LAB: {
                    builder.append("研究所");
                    break;
                }
                case BARRACKS: {
                    builder.append("軍營");
                    break;
                }
                case SAW_MILL: {
                    builder.append("伐木廠");
                    break;
                }
                case STEEL_MILL: {
                    builder.append("煉鋼廠");
                    break;
                }
                case ARSENAL: {
                    builder.append("兵工廠");
                    break;
                }
                case GAS_MILL: {
                    builder.append("瓦斯廠");
                    break;
                }
                case AIRPLANE_MILL: {
                    builder.append("飛機工廠");
                    break;
                }
            }
            if(entry.getValue()!=0){
                builder.append(entry.getValue());
            }
            builder.append("間被炸掉了\n");
        }

        return builder.toString();
    }

    /**
     * 沒有研究所
     */
    public boolean isNoLab() {
        return freeLabNum == 0;
    }

    /**
     * 沒有兵工廠
     */
    public boolean isNoArsenal() {
        return freeArsenalNum == 0;
    }

    public boolean isAllDestroyed(){
        for(BuildingType buildingType : BuildingType.values()){
            if(!buildingType.list.isEmpty()){
                return false;
            }
        }
        return true;
    }
}
