package oldMain;

import company.Global;
import static company.Global.*;

import company.gameobj.BuildingController;
import company.gameobj.buildings.Building;

import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.GameKernel;


import company.gameobj.creature.human.*;
import company.gameobj.creature.enemy.zombies.*;
import company.gameobj.BuildingController.*;

import company.gametest9th.utils.GameKernel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static company.gameobj.BuildingController.BuildingType.*;
import static company.gameobj.BuildingController.*;

public class City implements GameKernel.GameInterface, CommandSolver.MouseCommandListener {
    /**
     * 預設一個城市有幾個人民
     * 預設一個城市有幾個市民
     * 有幾種不同建築
     * 總共有幾種殭屍
     */
    public final int DEFAULT_CITIZEN = 20;
    public final int MAX_CAN_BUILD = 100;
    public final int ZOMBIE_TYPE = 6;

    /**
     * 遊戲的建築物
     */
    private BuildingController buildings;
    /**
     * 由於整個遊戲 只有一個文明等級 所以設定在 City全域變數
     */
    private static int techLevel = 1;
    /**
     * 整個遊戲的時間軸 (小時為單位)
     */
    private static int gameTime = 0;
    /**
     * 遊戲的人類單位 1.士兵 2.市民
     * 建立一Human的陣列 以存放人類
     */
    private Citizens citizens;
    /**
     * 城市的軍隊
     */
    public final Military military;
    /**
     * 城市的殭屍群
     */
    private ZombieKingdom zombies;
    /**
     * 全部的資源 類別
     */
    private Resource resource;

    /**
     * 城市的建構子 建構初始的 人民/殭屍/建築物陣列
     */
    public City() {
        resource = new Resource();
        buildings = new BuildingController();
        zombies = new ZombieKingdom();
        citizens = null;//TODO
        military = new Military();
    }

    /**
     * 取得所有閒人總數
     *
     * @return 所有閒人數
     */
    public int getFreeCitizen() {
        return citizens.getNumOfFreeCitizens();
    }

    /**
     * 工作相關方法  像是 派幾個人去採 木頭 / 鋼鐵
     */
    public void assignWork(int numOfCitizensToAssign, OldMain.Command workType) {
        citizens.assignCitizenToWork(numOfCitizensToAssign, workType);
    }

    /**
     * 取得這一座程式的總資源
     *
     * @return 目前程式的資源
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * 取得遊戲目前進行多久了
     *
     * @return 取得遊戲時間
     */
    public static int getGameTime() {
        return gameTime;
    }

    /**
     * 時間流動一小時
     */
    public void timePass() {
        gameTime++;
    }

    /**
     * 每一次時間流動之後 計算市民採集的物資，加進resource中
     */
    public void gainResource() {
        resource.addWood(buildings.getWoodSpeed() * citizens.getNumOfLoggingCitizens());
        resource.addSteel(buildings.getSteelSpeed() * citizens.getNumOfMiningCitizens());
        resource.addGas(buildings.getGasProduceNum());
    }

    /**
     * 視覺化的放資源
     * @param num 資源數量
     * @param resourceType 資源種類
     */
    public void gainResource(int num, Citizen.Resource resourceType) {

        // 根據村民現在身上的 資源數量 && 種類 放到總資源堆
        if(resourceType == Citizen.Resource.WOOD){
            resource.addWood(num);
        } else if(resourceType == Citizen.Resource.STEEL){
            resource.addSteel(num);
        }


        // 瓦斯還沒有寫// TODO: 2022/3/29 瓦斯添加的流程
        resource.addGas(buildings.getGasProduceNum());
    }

    /**
     * 黨指令下達完畢
     * 會依造指令而去執行每個單位時間索賄發生的事情
     * 1.因人而產出的物資
     * 2.因建築而產出的人
     * 3.因時間到而升級的建築 & 文明等級 & 士兵等級
     * <p>
     * <p>
     * 處理 這個時間流動前的所有指令
     *
     * @param thisRoundTimePass 這一輪指令的時間
     */
    //TODO system -> toaster 辰
    public void doCityWorkAndTimePass(int thisRoundTimePass) {
        //把時間流動前做的指令 && 決定流動的時間跑完
        for (int time = 0; time < thisRoundTimePass; time++) {
            //遊戲時間 流動 1小時
            timePass();
            //生產 木&鋼
            gainResource();
            //建物.生成人()
            int numOfNewCitizens = buildings.getNewCitizenNum(resource);
            if (numOfNewCitizens != 0) {
                this.citizens.add(numOfNewCitizens);
                //System.out.printf("第%d回合 有新市民出生 ,閒置人數:%d\n", getGameTime() + 1, citizens.getNumOfFreeCitizens());
            }
            int numOfNewArmySoldiers = buildings.getNewArmyNum(resource);
            if (numOfNewArmySoldiers != 0) {
                this.military.addArmy(numOfNewArmySoldiers);
                //System.out.printf("第%d回合 有 %d 個新戰士出生,目前一共有%d個戰士\n", getGameTime() + 1, numOfNewArmySoldiers, military.getNumOfArmySoldier());
            }
            int numOfNewAirMen = buildings.getNewPlaneNum(resource);
            if (numOfNewAirMen != 0) {
                this.military.addAirForce(numOfNewAirMen);
                //System.out.printf("第%d回合 有 %d 架新飛機產生,目前一共有%d架飛機\n", getGameTime() + 1, numOfNewAirMen, military.getNumOfAirmen());
            }
            //完成建築的升級和建造，科技等級提升
            buildings.completeJob();

            if (buildings.isRecentlyUpgradeTech()) {
                this.upgradeTechLevel();
            }
            if (buildings.isRecentlyUpgradeArmySoldier()) {
                this.military.upgradeArmy();
            }
            if (buildings.isRecentlyUpgradeAirForceSoldier()) {
                this.military.upgradeAirForce();
            }

            zombies.timePass();

            //殭屍來襲時間( 每16小時來一次 )
            if (zombies.isAttacking()) {
                //建築物升級測試會被毀掉，先註解掉
//                System.out.println("殭屍來襲：" + this.zombies.getZombieTroop());
//                System.out.println("你的部隊：空軍攻擊力：" + this.military.getAirForceValue()+"，陸軍攻擊力：" + this.military.getArmyValue());
//                //用來 計算抵擋完殭屍後的人口狀況 和 算完後遊戲是否結束
//                this.fightZombies(this.zombies.getZombieTroop());
//                //顯示被破壞的建築
//                System.out.println(buildings.sumDamageBuilding());
//                if (!this.isAlive()) {
//                    System.out.println("這裡已經什麼都沒有了....");
//                    System.out.println("Game Over");
//                }
            }
        }
    }

    /**
     * 顯示可以蓋的建築物
     */
    public void showCanBuildBuilding() {
        System.out.printf("木材: %d , 鋼鐵: %d, 瓦斯: %d\n", resource.getTotalWood(), resource.getTotalSteel(), resource.getTotalGas());
        System.out.print(buildingSelectString(HOUSE.instance()) + "\n" +
                buildingSelectString(LAB.instance()) + "\n" +
                buildingSelectString(BARRACKS.instance()) + "\n" +
                buildingSelectString(SAW_MILL.instance()) + "\n" +
                buildingSelectString(STEEL_MILL.instance()) + "\n" +
                buildingSelectString(ARSENAL.instance()) + "\n" +
                buildingSelectString(GAS_MILL.instance()) + "\n" +
                buildingSelectString(AIRPLANE_MILL.instance()) + "\n");
    }

    /**
     * 建造需求字串
     *
     * @param building 建築
     * @return 建造需求字串
     */
    private String buildingSelectString(Building building) {
        return (building.getId() + ". " + building +
                "\n\t資源需求： 木材：" + building.getWoodCostCreate() + " 鋼鐵：" + building.getSteelCostCreate() + " 瓦斯：" + building.getGasCostCreate() +
                " 科技等級需求： " + building.getTechLevelNeedBuild() + "\n");
    }

    /**
     * 建築物可不可以建造
     *
     * @param type 建築物類型
     * @return 可否建造
     */
    public boolean canBuildBuilding(BuildingType type) {
        return buildings.canBuild(type, resource);
    }

    /**
     * 開始建造
     *
     * @param type 要建造的建築種類
     */
    public void build(BuildingType type,int x, int y) {
        buildings.build(type, resource,x,y);
    }

    /**
     * 顯示每種建築物可以升級的數量
     */
    public void showCanUpgradeBuilding() {
        System.out.printf("木材: %d , 鋼鐵: %d, 瓦斯: %d\n", resource.getTotalWood(), resource.getTotalSteel(), resource.getTotalGas());
        buildings.showCanUpgradeBuildingNum(resource);
    }

    /**
     * 判斷該類建築的鏈表中有沒有可以升級的建築數量，沒有代表不能升級
     *
     * @param type 建築物類型
     * @return 可否升級
     */
    public boolean canUpgradeBuilding(BuildingType type) {
        return buildings.getCanUpgradeNum(type, resource) != 0;
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
        return buildings.showAndGetCanUpgradeTypeDetail(type);
    }

    /**
     * 升級指定index的建築
     *
     * @param node 指定建築
     */
    public void upgrade(BuildingNode node) {
        buildings.upgrade(node, resource);
    }

    /**
     * 升級科技等級
     */
    public void upgradeTechLevel() {
        buildings.upgradeTechLevel(resource);
    }

    /**
     * 升級士兵等級
     */
    public void upgradeSoldier() {
        buildings.upgradeSoldier(resource);
    }

    /**
     * 升級飛機等級
     */
    public void upgradePlane() {
        buildings.upgradePlane(resource);
    }

    /**
     * 只派軍隊去防禦殭屍
     *
     * @param zombieTroop 所要防禦的殭屍群
     */
    public void fightZombies(ZombieKingdom.ZombieTroop zombieTroop) {
        //這個數值是 最終結果 也就是 是否能夠抵擋這一波殭屍潮的判斷數 >0 死亡  <=0 存活

        //將空中與地面的殭屍部隊攻擊分開看
        int landAttack = zombieTroop.getLandAttack();
        int airAttack = zombieTroop.getAirAttack();
        //空中殭屍攻擊空中部隊
        if (airAttack>0 && airAttack >= military.getAirForceValue()) {
            airAttack -= military.getAirForceValue();
            military.getAirForceWipedOut();
        } else {
            military.getAirForceHarmed(airAttack);
            airAttack = 0;
        }
        //空中殭屍攻擊平民
        if (airAttack>0 && airAttack >= citizens.getValueOfCitizens()) {
            airAttack -= citizens.getValueOfCitizens();
            citizens.getWipedOut();
        } else {
            citizens.getHarmed(airAttack);
            airAttack= 0;
        }
        //空中殭屍攻擊建築
        if (airAttack > 0) {
            buildings.getDamage(airAttack);
            airAttack = 0;
        }
        //地面殭屍攻擊地面部隊
        if (landAttack>0 && landAttack >= military.getArmyValue()) {
            landAttack -= military.getArmyValue();
            military.getArmyWipedOut();
        } else {
            military.getArmyHarmed(landAttack);
            landAttack = 0;
        }
        //地面殭屍攻擊平民
        if (landAttack>0 && landAttack >= citizens.getValueOfCitizens()) {
            landAttack -= citizens.getValueOfCitizens();
            citizens.getWipedOut();
        } else {
            citizens.getHarmed(landAttack);
            landAttack = 0;
        }
        //地面殭屍攻擊建築
        if (landAttack > 0) {
            buildings.getDamage(landAttack);
            //landAttack = 0;
        }

        if (this.isAlive()) {//還有人活著 遊戲繼續
            if (military.isAllDead()) {
                System.out.println("不平靜的夜晚過去了\n你活了下來 但是\n戰士們為了拯救你 流光了鮮血\n");
            } else {
                System.out.println("不平靜的夜晚過去了\n你也活下來了\n");
            }
        } else {//全死 遊戲結束
            System.out.println("不平靜的夜晚終於過去了 但你再也撐不住了，這裡只剩廢墟...\n");
        }
    }

    /**
     * 顯示目前城市的所有資訊
     */
    public void showInfo() {

        //所有資源的資訊
        System.out.printf("第%d小時\n目前總資源量如下:\n" +
                "木材: %d , 鋼鐵: %d, 瓦斯: %d\n", getGameTime() + 1, resource.getTotalWood(), resource.getTotalSteel(), resource.getTotalGas());

        //所有人力資源的資訊
        System.out.printf("目前人力資源如下:\n" +
                "總市民人數: %d \n" +
                "採木人: %d , 採鋼人: %d, 閒人: %d\n",
                citizens.getNumOfCitizens(), citizens.getNumOfLoggingCitizens(), citizens.getNumOfMiningCitizens(), citizens.getNumOfFreeCitizens());

        //所有人民的資訊
        System.out.printf("目前士兵量如下:\n" +
                "士兵: %d名, 飛機 %d架\n", military.getNumOfArmySoldier(), military.getNumOfAirmen());

        //顯示科技等級
        System.out.println("目前科技等級為:" + techLevel);

        //顯示下波攻擊倒數
        System.out.println("距下波攻擊還有 " + (16 - getGameTime() % 16) + " 小時");
        System.out.println("---------------------------------------------------");

        System.out.println(buildings.showInfo());
    }

    /**
     * @return 文明等級
     */
    public static int getTechLevel() {
        return techLevel;
    }

    /**
     * 科技等級提升
     */
    public static void addTechLevel() {
        techLevel++;
    }

    /**
     * 獲取目前市民總數
     *
     * @return 目前市民總數
     */
    public int getTotalCitizen() {
        return this.citizens.getNumOfCitizens();
    }

    public int getBuildingsNum(int id){
        return buildings.countBuildingNum(id);
    }
    /**
     * 城市中的建築數量
     *
     * @return 城市中的建築數量
     */
    public int getBuildingNum() {
        return buildings.getBuildingNum();
    }

    /**
     * 取得閒置的研究所數量
     *
     * @return 閒置的研究所數量
     */
    public int getFreeLabNum() {
        return buildings.getFreeLabNum();
    }

    /**
     * 取得閒置的兵工廠數量
     *
     * @return 閒置的兵工廠數量
     */
    public int getFreeArsenalNum() {
        return buildings.getFreeArsenalNum();
    }


    public boolean isAlive() {
        return (!this.citizens.isAllDead() || !this.military.isAllDead() || !buildings.isAllDestroyed());
    }


    /**
     * 是否正在升級士兵
     *
     * @return 是否正在升級士兵
     */
    public boolean isUpgradingSoldier() {
        return buildings.isUpgradingSoldier();
    }

    /**
     * 是否正在升級飛機
     *
     * @return 是否正在升級飛機
     */
    public boolean isUpgradingPlane() {
        return buildings.isUpgradingPlane();
    }

    /**
     * 是否正在升級科技
     *
     * @return 是否正在升級科技
     */
    public boolean isUpgradingTech(BuildingNode buildingNode) {
        return buildings.isUpgradingTech(buildingNode);
    }

    /**
     * 取得停工中的非升級中建築
     *
     * @return 停工中的非升級中建築陣列
     */
    public ArrayList<BuildingNode> getNotWorkingBuildingList() {
        return buildings.getNotWorkingBuildingList();
    }

    /**
     * 取得工作中建築
     *
     * @return 工作中建築陣列
     */
    public ArrayList<BuildingNode> getWorkingBuildingList() {
        return buildings.getWorkingBuildingList();
    }

    /**
     * 讓指定建築物停工
     *
     * @param buildingNode 指定建築物
     */
    public void setStop(BuildingNode buildingNode) {
        buildings.setStop(buildingNode);
    }

    /**
     * 讓指定建築物開始運作
     *
     * @param buildingNode 指定開始運作
     */
    public void setStart(BuildingNode buildingNode) {
        buildings.setStart(buildingNode);
    }

    /**
     * 沒有研究所
     */
    public boolean isNoLab() {
        return buildings.isNoLab();
    }

    /**
     * 沒有兵工廠
     */
    public boolean isNoArsenal() {
        return buildings.isNoArsenal();
    }

    @Override
    public void paint(Graphics g) {
        buildings.paint(g);
    }

    @Override
    public void update() {
        buildings.update();
    }

    private BuildingNode currentBuildingNode;

    public BuildingNode selectBuildingNode(int x,int y){
        return buildings.selectionBuildingNode(x,y);
    }

    public BuildingNode getCurrentBuildingNode(){
        return currentBuildingNode;
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        buildings.mouseTrig(e,state,trigTime);
        currentBuildingNode=selectBuildingNode(e.getX(),e.getY());
    }


    public void setCitizens(Citizens citizens){

        this.citizens = citizens;
    }

    public Citizens getCitizens(){
        return citizens;
    }

}
