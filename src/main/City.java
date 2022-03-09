package main;

import buildings.*;
import creature.human.*;
import creature.Zombies.*;
import main.BuildingsCollection.*;
import java.util.ArrayList;
import static main.BuildingsCollection.BuildingType.*;

public class City {
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
    private BuildingsCollection buildings;
    private int buildingCount;
    // 目前已建造的研究所的數量
    private int numOfLab;
    // 正在升級技術中的研究所的數量
    private int buildingsInLab;
    /**
     * 目前在伐木的人
     */
    private int woodMan;
    /**
     * 目前在挖鋼的人
     */
    private int steelMan;
    /**
     * 目前的閒人(可只派工作之村民總共有幾個)
     */
    private int freeCitizen;
    /**
     * 由於整個遊戲 只有一個文明等級 所以設定在 City全域變數
     */
    private static int techLevel = 1;
    /**
     * 整個遊戲的時間軸 (小時為單位)
     */
    private static int gameTime = 0;
    /**
     * 市民產生時間(每24小時 從房屋產生一波 )
     */
    final int citizenBornTime = 24;
    /**
     * 士兵產生時間(每3小時 從軍營產生一波 )
     */
    final int soldierBornTime = 3;
    /**
     * 遊戲的人類單位 1.士兵 2.市民
     * 建立一Human的陣列 以存放人類
     */
    public static final ArrayList<Citizen> citizens = new ArrayList<>();
    public static final Military military = new Military();

    /**
     * 遊戲的建築物  1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠
     * 建立一Building 的陣列以存放建築物
     */
//    private Building[] buildings;
    /**
     * 殭屍單位
     */
//    private final Zombie[] zombies;
    private ZombieKingdom zombies;
    /**
     * 全部的資源 類別
     */
    private Resource resource;

    /**
     * 城市的建構子 建構初始的 人民/殭屍/建築物陣列
     */
    public City() {
        //
        buildingsInLab = 0;
        //研究所數量
        numOfLab = 0;
        //已經建造的建築物的數量
        buildingCount = 0;
        freeCitizen = 0;
        woodMan = 0;
        steelMan = 0;
        resource = new Resource();
        buildings = new BuildingsCollection();
        zombies = new ZombieKingdom();
        /*
          用來所有士兵new出來 放在 人類的ArrayList
        */
        for (int i = 0; i < DEFAULT_CITIZEN; i++) {
            //這些是預設市民物件
            if (i < DEFAULT_CITIZEN) {
                citizens.add(i, new Citizen());
                addFreeCitizen(1);
            }
        }
        //創建所有殭屍的物件
//        zombies[0] = new ZombieNormal();
//        zombies[1] = new ZombieBigger();
//        zombies[2] = new ZombieTypeI();
//        zombies[3] = new ZombieTypeII();
//        zombies[4] = new ZombieKing();
//        zombies[5] = new ZombieLichKing();
    }

    /**
     * 設定目前伐木人數
     *
     * @param humanNum 伐木人數
     */
    public void addWoodCitizen(int humanNum) {
        woodMan += humanNum;
    }

    /**
     * 取得所有閒人總數
     *
     * @return 所有閒人數
     */
    public int getFreeCitizen() {
        return freeCitizen;
    }

    /**
     * 設定 閒人數量 增減
     *
     * @param humanNum 要增減的量
     */
    public void addFreeCitizen(int humanNum) {
        freeCitizen += humanNum;
    }

    /**
     * 設定 鋼鐵數量 增減
     *
     * @param humanNum 要增減的量
     */
    public void addSteelCitizen(int humanNum) {
        steelMan += humanNum;
    }

    /**
     * 工作相關方法  像是 派幾個人去採 木頭 / 鋼鐵
     */
    public void assignWork(int humansNum, Main.Command workType) {
        //預設他是去伐木
        boolean isWorkedForWood = true;
        //如果 這個指令是 去採鋼 那麼把把isWorkForWood 改為 false
        if (workType == Main.Command.STEEL) {
            isWorkedForWood = false;
        }
        for (Human human : citizens) {
            // 如果他不是士兵 && 他是一個閒人 派遣工作
            if(human.isCitizen()){
                Citizen citizen = (Citizen) human;
                if(citizen.isFree()){
                    if (isWorkedForWood) {
                        citizen.staratToLog();//把該人類物件 狀態 設定為 伐木
                        addWoodCitizen(1);//全部伐木人數 + 1
                    } else {
                        citizen.startToMine();//把該人類物件 狀態 設定為 採鋼
                        //為什麼不用紀錄採鐵人數? 因為把 全部人humans.size() -士兵 - 全部伐木 - 全部閒人 = 採鐵 (算得出來)....我還是做出來了
                        //全部煉鋼人數 + 1
                        addSteelCitizen(1);
                    }
                    //由於有分配了工作，閒人-1
                    addFreeCitizen(-1);
                    //指派的工作量 - 1
                    humansNum -= 1;
                }
            }
            //如果指派量 ==0 表示都指派完了 跳出
            if (humansNum == 0) {
                break;
            }
        }
    }

    /**
     * 取得這一座程式的總資源
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
        resource.addWood(buildings.getWoodSpeed() * woodMan);
        resource.addSteel(buildings.getSteelSpeed()* steelMan);
        resource.addGas(buildings.getGasProduceNum());
    }

    /**
     * 生產市民
     * @return 生成數量
     */
    private int productCitizen() {
        return buildings.getNewCitizenNum(resource);
    }

    /**
     * 生產士兵
     * @return 生成數量
     */
    private int productSoldier() {
        return buildings.getNewArmyNum(resource);
    }

    /**
     * 生產飛機
     * @return 生成數量
     */
    private int productPlane() {
        return buildings.getNewPlaneNum(resource);
    }

    /**
     * 增加市民
     */
    public void addCitizen() {
        citizens.add(new Citizen());
    }

    /**
     * 建築的建造與升級完成
     */
    private void finishBuildAndUpgrade() {
        buildings.showBuildCompleted();
        buildings.showUpgradeCompleted();
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
     * @return 如果 回傳值為true 繼續遊戲  false 結束遊戲
     */
    public void doCityWorkAndTimePass(int thisRoundTimePass) {
        //把時間流動前做的指令 && 決定流動的時間跑完
       for(int time = 0; time<thisRoundTimePass; time++) {
            //遊戲時間 流動 1小時
            timePass();
            //生產 木&鋼
            gainResource();
            //建物.生成人()
            int numOfNewCitizens = buildings.getNewCitizenNum(resource);
            if(numOfNewCitizens!=0){
                System.out.printf("第%d回合 有新市民出生,目前一共有%d個市民 ,閒置人數:%d\n", getGameTime() + 1, getTotalCitizen(), freeCitizen);
            }
            int numOfNewArmySoldiers = buildings.getNewArmyNum(resource);
            if(numOfNewArmySoldiers!=0){
                this.military.addArmy(numOfNewArmySoldiers);
                System.out.printf("第%d回合 有 %d 個新戰士出生,目前一共有%d個戰士\n", getGameTime() + 1, numOfNewArmySoldiers, military.getNumOfArmySoldier());
            }
            int numOfNewAirMen = buildings.getNewPlaneNum(resource);
            if(numOfNewAirMen!=0){
                this.military.addArmy(numOfNewAirMen);
                System.out.printf("第%d回合 有 %d 架新飛機產生,目前一共有%d架飛機\n", getGameTime() + 1, numOfNewAirMen, military.getNumOfAirmen());
            }
            //完成建築的升級和建造，科技等級提升
            buildings.completeJob();


            //???先update看大家Ｏ不ＯＫ
            zombies.timePass();
            //殭屍來襲時間( 每16小時來一次 )
           if(zombies.isAttacking()){
               //用來 計算抵擋完殭屍後的人口狀況 和 算完後遊戲是否結束
               this.fightZombies(this.zombies.getZombieTroop());
               if (!this.isAlive()) {
                   System.out.println("城鎮人口皆被消滅!");
                   System.out.println("Game Over");
               }
           }
        }
    }

    /**
     * 顯示可以蓋的建築物
     */
    public void showCanBuildBuilding(){
        System.out.println(buildingSelectString(HOUSE.instance()));
        System.out.println(buildingSelectString(LAB.instance()));
        System.out.println(buildingSelectString(BARRACKS.instance()));
        System.out.println(buildingSelectString(SAW_MILL.instance()));
        System.out.println(buildingSelectString(STEEL_MILL.instance()));
        System.out.println(buildingSelectString(ARSENAL.instance()));
        System.out.println(buildingSelectString(GAS_MILL.instance()));
        System.out.println(buildingSelectString(AIRPLANE_MILL.instance()));
    }

    /**
     * 建造需求字串
     * @param building 建築
     * @return 建造需求字串
     */
    private String buildingSelectString(Building building){
        return (building.getId() + ". " + building +
                "資源需求： " + building.getWoodCostCreate() + building.getSteelCostCreate() + building.getGasCostCreate() +
                "科技等級需求： " + building.getTechLevelNeedBuild());
    }

    /**
     * 建築物可不可以建造
     * @param type 建築物類型
     * @return 可否建造
     */
    public boolean canBuildBuilding(BuildingType type){
        return buildings.canBuild(type,resource);
    }

    /**
     * 開始建造
     * @param type
     */
    public void build(BuildingType type){
        buildings.build(type,resource);
    }

    /**
     * 顯示每種建築物可以升級的數量
     */
    public void showCanUpgradeBuilding() {
       buildings.showCanUpgradeBuildingNum(resource);
    }

    /**
     * 判斷該類建築的鏈表中可以升級的建築數量==0? ==0代表不能升級
     * @param type 建築物類型
     * @return 可否升級
     */
    public boolean canUpgradeBuilding(BuildingsCollection.BuildingType type) {
        return buildings.getCanUpgradeNum(type,resource)!=0;
    }

    /**
     * 顯示該種類可以升級的建築細節
     * @param type 建築種類
     * @return 可以升級的建築陣列
     * 若選擇升級建築，但沒有閒置的研究所 -> null
     * 若選擇升級軍力，但沒有閒置的兵工廠 -> null
     */
    public ArrayList<BuildingNode> showCanUpgradeTypeDetail(BuildingType type){
        return buildings.showCanUpgradeTypeDetail(type);
    }

    /**
     * 升級指定index的建築
     * @param node 指定建築
     */
    public void upgrade(BuildingNode node){
        buildings.upgrade(node,resource);
    }

    /**
     * 升級科技等級
     */
    public void upgradeTechLevel() {
        buildings.upgradeTechLevel();
    }

    /**
     * 升級士兵等級
     */
    public void upgradeSoldier() {
       buildings.upgradeSoldier();
    }

    /**
     * 升級飛機等級
     */
    public void upgradePlane() {
        buildings.upgradePlane();
    }

    /**
     * 檢查一下 這一波殭屍來襲 擋不擋得住
     *
     * @return 回傳true 活著  false 死去
     */
    public void fightZombies(ZombieKingdom.ZombieTroop zombieTroop) {
        //這個數值是 最終結果 也就是 是否能夠抵擋這一波殭屍潮的判斷數 >0 死亡  <=0 存活

        int landAttack = zombieTroop.getLandAttack();
        int airAttack = zombieTroop.getAirAttack();


       if(airAttack >= military.getAirForceValue()){
           airAttack -= military.getAirForceValue();
           military.getAirForceWipedOut();
           Citizen citizen;
           for(int i=0; airAttack>0 && i<citizens.size(); i++){
               citizen = citizens.get(i);
               if(airAttack>=citizen.getValue()){
                   airAttack-=citizen.getValue();
                   citizens.remove(i);
                   i--;
               }
               else{
                   citizen.getAttacked(airAttack);
                   airAttack = 0;
               }
           }
       }
       else{
           military.getAirForceHarmed(airAttack);
       }


       if(landAttack >= military.getArmyValue()){
           landAttack -= military.getArmyValue();
           military.getArmyWipedOut();
           Citizen citizen;
           for(int i=0; airAttack>0 && i<citizens.size(); i++){
               citizen = citizens.get(i);
               if(airAttack>=citizen.getValue()){
                   airAttack-=citizen.getValue();
                   citizens.remove(i);
                   i--;
               }
               else{
                   citizen.getAttacked(airAttack);
                   airAttack = 0;
               }
           }
       }
       else{
           military.getArmyHarmed(landAttack);
       }

       if(citizens.size()>0){//還有人活著 遊戲繼續
           if (military.isAllDied()) {
               System.out.println("不平靜的夜晚過去了\n你活了下來 但是\n戰士們為了拯救你 流光了鮮血\n");
           } else {
               System.out.println("不平靜的夜晚過去了\n你也活下來了\n");
           }
       }
        else {//全死 遊戲結束
            System.out.println("不平靜的夜晚終於過去了 但是你再也撐不住了\n");
        }
    }

    /**
     * 顯示目前城市的所有資訊
     */
    public void showInfo() {
        //所有資源的資訊
        System.out.printf("第%d小時\n目前總資源量如下:\n" +
                "木材: %d , 鋼鐵: %d\n", getGameTime() + 1, resource.getTotalWood(), resource.getTotalSteel());
        //所有人力資源的資訊
        System.out.printf("目前人力資源如下:\n" +
                "採木人: %d , 採鋼人: %d, 閒人: %d\n", woodMan, steelMan, freeCitizen);
        //所有人民的資訊
        System.out.printf("目前士兵量如下:\n" +
                "士兵: %d名, 飛機 %d架, 市民: %d名\n", military.getNumOfArmySoldier(), military.getNumOfAirmen(), getTotalCitizen());
        //顯示科技等級
        System.out.println("目前科技等級為:" + techLevel);
        //顯示下波攻擊倒數
        System.out.println("距下波攻擊還有 " + (16 - getGameTime() % 16) + " 小時");
        System.out.println("---------------------------------------------------");

        buildings.getCurrentInformation();
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
    public static void addTechLevel(){
        techLevel++;
    }


    public int getTotalAirMen() {
        int soliderNum = 0;
        for (Human human : citizens) {
            if (human.isSoldier()) {
                soliderNum += 1;
            }
        }
        return soliderNum;
    }

    /**
     * 獲取目前市民總數
     *
     * @return 目前市民總數
     */
    public int getTotalCitizen() {
        return this.citizens.size();
    }

//    /**
//     * 把所有建築物陣列中的建築物 根據建築物ID 由小到大排
//     */
//    public void sortBuildingArrByID() {
//        for (int i = 0; i < buildingCount - i; i++) {
//            for (int j = 0; j < buildingCount - i - 1; j++) {
//                if (buildings[j].getId() > buildings[j + 1].getId()) {
//                    Building tmpBuilding = buildings[j + 1];
//                    buildings[j + 1] = buildings[j];
//                    buildings[j] = tmpBuilding;
//                }
//            }
//        }
//    }

    /**
     * 城市中的建築數量
     * @return 城市中的建築數量
     */
    public int getBuildingNum(){
        return buildings.getBuildingNum();
    }

    /**
     * 取得閒置的研究所數量
     * @return 閒置的研究所數量
     */
    public int getFreeLabNum(){
        return buildings.getFreeLabNum();
    }

    /**
     * 取得閒置的兵工廠數量
     * @return 閒置的兵工廠數量
     */
    public int getFreeArsenalNum(){
        return buildings.getFreeArsenalNum();
    }

    public boolean isAlive(){
        return (this.citizens.size()>0);
    }
}
