package main;

import buildings.*;

import java.util.LinkedList;

/**
 * @author Lillian
 * @Date 2022/3/3
 * @Description
 */
public class BuildingsCollection {
//    private City city;

    public enum BuildingType {
        ARSENAL,
        BARRACKS,
        HOUSE,
        LAB,
        SAW_MILL,
        STEEL_MILL,
        GAS_MILL,
        AIRPLANE_MILL;
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

    public BuildingsCollection() {
//        this.city = city;
        arsenalList = new LinkedList<>();
        barracksList = new LinkedList<>();
        houseList = new LinkedList<>();
        labList = new LinkedList<>();
        sawMillList = new LinkedList<>();
        steelMillList = new LinkedList<>();
        gasMillList = new LinkedList<>();
        airplaneMillList = new LinkedList<>();
    }

    /**
     * 建造
     *
     * @param type
     */
    public void buildProcess(BuildingType type) {
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
            case GAS_MILL: {
                newBuilding = new BuildingNode(new GasMill());
                setBuildingBuilding(newBuilding);
                gasMillList.add(newBuilding);
                break;
            }
            case AIRPLANE_MILL: {
                newBuilding = new BuildingNode(new AirPlaneMill());
                setBuildingBuilding(newBuilding);
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
    private void setBuildingBuilding(BuildingNode newBuilding) {
        newBuilding.buildStartTime = City.getGameTime();
        newBuilding.buildEndTime = City.getGameTime() + newBuilding.building.getBuildTime();
        newBuilding.building.setWorking(false);
        newBuilding.building.setUpgrading(true);
        newBuilding.building.setReadyToUpgrade(false);
    }

    /**
     * 升級
     *
     * @param type
     * @param fromLevel
     * @return 是否已安排升級
     */
    private void upgradeProcess(BuildingType type, int fromLevel) {
        switch (type) {
            case ARSENAL: {
                findUpgradeTargetFrom(arsenalList);
                break;
            }
            case BARRACKS: {
                findUpgradeTargetFrom(barracksList);
                break;
            }
            case HOUSE: {
                findUpgradeTargetFrom(houseList);
                break;
            }
            case LAB: {
                findUpgradeTargetFrom(labList);
                break;
            }
            case SAW_MILL: {
                findUpgradeTargetFrom(sawMillList);
                break;
            }
            case STEEL_MILL: {
                findUpgradeTargetFrom(steelMillList);
                break;
            }
            case GAS_MILL: {
                findUpgradeTargetFrom(gasMillList);
                break;
            }
            case AIRPLANE_MILL: {
                findUpgradeTargetFrom(airplaneMillList);
                break;
            }
        }
    }

    /**
     * 從list中找到要升級的目標
     *
     * @param list
     */
    public void findUpgradeTargetFrom(LinkedList<BuildingNode> list) {
        for (BuildingNode node : list) {
            if (!node.building.isUpgrading()) {
                setBuildingUpgrading(node);
            }
        }
    }

    /**
     * 將建築設為升級中
     *
     * @param node
     */
    private void setBuildingUpgrading(BuildingNode node) {
        node.upgradeStartTime = City.getGameTime();
        node.upgradeEndTime = City.getGameTime() + node.building.getUpgradeTime();
        node.building.setUpgrading(true);
        node.building.setWorking(false);
        node.building.setReadyToUpgrade(false);
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
     * 建築開始運作，建築等級提升，關閉升級中狀態，開啟工作中狀態
     *
     * @param list
     */
    private void setBuildingStartWork(LinkedList<BuildingNode> list) {
        int sum = 0;
        for (BuildingNode node : list) {
            if (node.buildEndTime == City.getGameTime()) {
                node.building.setLevel(node.building.getLevel() + 1);
                node.building.setUpgrading(false);
                node.building.setWorking(true);
                node.building.setReadyToUpgrade(true);
//                city.setBuildingCount(city.getBuildingCount() + 1);
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
            if (node.upgradeEndTime == City.getGameTime()) {
                node.building.setLevel(node.building.getLevel() + 1);
                node.building.setUpgrading(false);
                node.building.setWorking(true);
                node.building.setReadyToUpgrade(true);
                sum++;
            }
        }
        System.out.println(sum + "間完成升級");
    }

    /**
     * 生成的居民數量
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
        return list.size();
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
     * 對外升級接口，回傳可以升級的數量
     *
     * @param cityTechLevel 建築等級
     * @param type          建築種類
     * @param resource      都市的物資
     * @return 可以升級的數量
     */
    public int upgradeNum(int cityTechLevel, BuildingType type, Resource resource) {
        //若沒有研究所不可以升級
        if (labList.size() == 0) {
            return 0;
        }
        return getUpgradeNumInType(cityTechLevel, type, resource);
    }

    /**
     * 取得各建築可以升級的數量
     *
     * @param type     建築種類
     * @param resource 都市的物資
     * @return 可以升級數量
     */
    public int getUpgradeNumInType(int techLevel, BuildingType type, Resource resource) {
        switch (type) {
            case ARSENAL: {
                if (techLevel < 2) {
                    return 0;
                }
                return countCanUpgradeNumFrom(arsenalList, resource);
            }
            case BARRACKS: {
                return countCanUpgradeNumFrom(barracksList, resource);
            }
            case HOUSE: {
                return countCanUpgradeNumFrom(houseList, resource);
            }
            case LAB: {
                return countCanUpgradeNumFrom(labList, resource);
            }
            case SAW_MILL: {
                return countCanUpgradeNumFrom(sawMillList, resource);
            }
            case STEEL_MILL: {
                return countCanUpgradeNumFrom(steelMillList, resource);
            }
            case GAS_MILL: {
                if (techLevel < 2) {
                    return 0;
                }
                return countCanUpgradeNumFrom(gasMillList, resource);
            }
            case AIRPLANE_MILL: {
                if (techLevel < 2) {
                    return 0;
                }
                return countCanUpgradeNumFrom(airplaneMillList, resource);
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
    private int countCanUpgradeNumFrom(LinkedList<BuildingNode> list, Resource resource) {
        int sum = 0;
        for (BuildingNode node : list) {
            if (node.building.isReadyToUpgrade() && takeResource(resource)) {
                sum++;
            }
        }
        return sum;
    }


}
