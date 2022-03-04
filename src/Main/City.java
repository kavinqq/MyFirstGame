package Main;

import Buildings.*;
import Humans.*;
import Zombies.*;

import java.util.ArrayList;

public class City {
    /**
     * 預設一個城市有幾個人民
     * 預設一個城市有幾個市民
     * 有幾種不同建築
     * 總共有幾種殭屍
     */
    final int DEFAULT_PEOPLE = 30;
    final int DEFAULT_CITIZEN = 20;
    final int MAX_CAN_BUILD = 100;
    final int ZOMBIE_TYPE = 6;
    private int buildingCount;
    // 目前已建造的研究所的數量
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
    private int techLevel;
    /**
     * 整個遊戲的時間軸 (小時為單位)
     */
    private static int gameTime = 0;
    /**
     * 市民產生時間(每24小時 從房屋產生一波 )
     */
    final int citizenBornTime=24;
    /**
     * 士兵產生時間(每3小時 從軍營產生一波 )
     */
    final int soldierBornTime=3;
    /**
     * 遊戲的人類單位 1.士兵 2.市民
     * 建立一Human的陣列 以存放人類
     */
    private ArrayList<Human> humans = new ArrayList<>();
    /**
     * 遊戲的建築物  1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠
     * 建立一Building 的陣列以存放建築物
     */
    private Building[] buildings;
    /**
     * 殭屍單位
     * 共只有 ZOMBIE_TYPE 種 每一種殭屍只會在特定的陣列格子內 例如普通殭屍只會在zombies[0]
     */
    private final Zombie[] zombies;
    /**
     * 全部的資源 類別
     */
    private Resource resource;
    /**
     * 城市的建構子 建構初始的 人民/殭屍/建築物陣列
     */
    public City()
    {
        //
        buildingsInLab = 0;
        //研究所數量
        numOfLab = 0;
        //已經建造的建築物的數量
        buildingCount = 0;
        techLevel = 1;
        freeCitizen = 0;
        woodMan = 0;
        steelMan = 0;
        resource = new Resource();
        buildings = new Building[MAX_CAN_BUILD];
        zombies = new Zombie[ZOMBIE_TYPE];
        /*
          用來所有士兵new出來 放在 人類的ArrayList
        */
        for(int i = 0; i < DEFAULT_PEOPLE; i++){
            //這些是預設市民物件
            if(i < DEFAULT_CITIZEN){
                humans.add(i, new Citizen());
                addFreeCitizen(1);
            }else{   //這些是預設士兵物件
                humans.add(i, new Soldier());
            }
        }
        //創建所有殭屍的物件
        zombies[0] = new ZombieNormal();
        zombies[1] = new ZombieBigger();
        zombies[2] = new ZombieTypeI();
        zombies[3] = new ZombieTypeII();
        zombies[4] = new ZombieKing();
        zombies[5] = new ZombieLichKing();
    }

    /**
     *研究所數量
     * @return 研究所數量
     */
    public int getNumOfLab(){
        return numOfLab;
    }

    /**
     * 選擇階段 預先建立建築  (為了後續判斷方便使用)
     * @param buildingID 輸入建築物代號
     */
    public void preBuild(int buildingID){
        if(buildingID == 1){ //代號1  房屋
            buildings[buildingCount] = new House();
        }else if(buildingID == 2){ //代號2 研究所
            buildings[buildingCount] = new Lab();
        }else if(buildingID == 3){  //代號3 軍營
            buildings[buildingCount] = new Barracks();
        }else if(buildingID == 4){  //代號4 伐木場
            buildings[buildingCount] = new SawMill();
        }else if(buildingID == 5){  //代號5 煉鋼廠
            buildings[buildingCount] = new SteelMill();
        }else{
            buildings[buildingCount] = new Arsenal();  //代號6  兵工廠
        }
    }
    /**
     *獲得該建築
     * @param index 輸入第幾個建築
     * @return 回傳指定位置的建築
     */
    public Building getBuilding(int index){
        return buildings[index];
    }
    /**
     * 回傳目前City建築物是否已經蓋滿了
     * @return true 蓋滿 false 還沒蓋滿
     */
    public boolean isFull(){
        return buildingCount >= MAX_CAN_BUILD;
    }

    /**
     * 回傳目前City中 總共蓋了幾棟建築物 (類似流水號的概念)
     * @return 目前City中總共蓋了幾棟建築物
     */
    public int getBuildingCount(){
        return buildingCount;
    }

    /**
     * 設定目前伐木人數
     * @param humanNum 伐木人數
     */
    public void addWoodCitizen(int humanNum){
        woodMan += humanNum;
    }
    /**
     * 取得所有閒人總數
     * @return 所有閒人數
     */
    public int getFreeCitizen(){
        return freeCitizen;
    }

    /**
     * 設定 閒人數量 增減
     * @param humanNum 要增減的量
     */
    public void addFreeCitizen(int humanNum){
        freeCitizen += humanNum;
    }

    /**
     * 設定 鋼鐵數量 增減
     * @param humanNum 要增減的量
     */
    public void addSteelCitizen(int humanNum){
        steelMan += humanNum;
    }
    /**
     * 工作相關方法  像是 派幾個人去採 木頭 / 鋼鐵
     */
    public void assignWork(int humansNum, Main.Command workType)
    {
        //預設他是去伐木
        boolean isWorkedForWood = true;
        //如果 這個指令是 去採鋼 那麼把把isWorkForWood 改為 false
        if(workType == Main.Command.Steel){
            isWorkedForWood = false;
        }
        for (Human human : humans) {
            // 如果他不是士兵 && 他是一個閒人 派遣工作 ( 捷徑運算 )
            if (!human.getIsSoldier() && human.getState().equals("Free")) {
                if (isWorkedForWood) {
                    //把該人類物件 狀態 設定為 伐木
                    human.setStateToWood();
                    //全部伐木人數 + 1
                    addWoodCitizen(1);
                    //全部閒人數 -1
                } else {
                    //把該人類物件 狀態 設定為 採鋼
                    human.setStateToSteel();
                    //為什麼不用紀錄採鐵人數? 因為把 全部人humans.size() -士兵 - 全部伐木 - 全部閒人 = 採鐵 (算得出來)....我還是做出來了
                    //全部煉鋼人數 + 1
                    addSteelCitizen(1);
                    //全部閒人數 -1
                }
                //由於有分配了工作，閒人-1
                addFreeCitizen(-1);
                //指派的工作量 - 1
                humansNum -= 1;
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
    public Resource getResource(){
        return resource;
    }
    /**
     * 取得遊戲目前進行多久了
     * @return 取得遊戲時間
     */
    public int getGameTime(){
        return gameTime;
    }
    /**
     * 時間流動一小時
     */
    public void timePass(){
        gameTime ++;
    }

    /**
     * 每一次時間流動之後 計算 市民採集的成果 (取名叫獲得的資源)
     */
    public void gainResource(){
        for (Human human : humans) {
            // 他不是戰士
            if (!human.getIsSoldier()) {
                if (human.getState().equals("Wood")) {
                    resource.addWood();
                }else if (human.getState().equals("Steel")) {
                    resource.addSteel();
                }
            }
        }
    }

    /**
     * 生產市民
     * @return 如果生產成功 回傳true 失敗 回傳 false
     */
    private boolean productCitizen(int index){
        if(buildings[index].getId()==1){
            // 房屋是否被建造 && (遊戲時間 減去 此建築物剛建造完成的時間) % 24 → 經過24小時會生產市民
            if(buildings[index].getLevel() >= 0 && (gameTime - buildings[index].getCreateTime()) % citizenBornTime == 0){
                int numCitizenToBorn = buildings[index].getLevel() * 2 + 1;
                for(int i = 0;i < numCitizenToBorn; i++){
                    addCitizen();
                    addFreeCitizen(1);
                }
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * 生產士兵
     * @return 如果生產成功 回傳true 失敗 回傳 false
     */
    private boolean productSoldier(int index){
        if(buildings[index].getId()==3){
            // 房屋是否被建造 && (遊戲時間 減去 此建築物剛建造完成的時間) % 3 → 經過3小時會生產士兵
            if(buildings[index].getLevel() >= 0 && (gameTime -buildings[index].getCreateTime()) % soldierBornTime == 0){
                int numSoldierToBorn = buildings[index].getLevel() * 2 + 1;
                for(int i = 0;i < numSoldierToBorn;i++){
                    addSolider();
                }
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * 增加市民
     */
    public void addCitizen(){
        humans.add(new Citizen());
    }

    /**
     * 增加士兵
     */
    public void addSolider(){
        //Human.Human 建構子 (數值,是否為士兵)
        humans.add(new Soldier());
    }

    /**
     * 檢查所有的建築物
     * 如果符合建造中 且 建造時間完成
     * 就會讓該建築物 成為建造完成/功能升級完成 的建築物
     *
     * 文明用研究所的等級來取代
     * 士兵等級用兵工廠的等級來取代
     *
     * 筏木場.煉鋼廠 會因為升級而使產量增加
     */
    private void buildingsUpgrade(){

        for(int i = 0; i < buildingCount; i++){
            //如果isUpgradeFinish 回傳 true 表示該建物已經建造完成 false表示 還在建造中或是還沒建造
            if(buildings[i]!=null){
                if(buildings[i].isUpgradeFinish(gameTime)){
                    //如果該建築物是研究所 && 該建築物等級 > 0 (PS: 等級== -1 : 已安排建造但尚未建造 / = 0:建造好了 / >0:建造好且升級好了 )
                    if(buildings[i].getId() == 2 && buildings[i].getLevel() > 0){ //文明升級到2
                        System.out.println("文明已升級");
                        //文明等級++
                        techLevel ++;
                        //釋出可升級的空間
                        buildingsInLab--;
                        //如果該建築物是研究所 && 該建築物等級 == 0 (PS: 等級== -1 : 已安排建造但尚未建造 / = 0:建造好了 / >0:建造好且升級好了 )
                    }else if(buildings[i].getId() == 2 && buildings[i].getLevel() == 0){  //研究所建造完成
                        System.out.println("研究所已建造");     //新增顯示文明等級
                        //研究所總數量++
                        numOfLab++;
                        //如果該建築物是兵工廠 && 該建築物等級 == 0 (PS: 等級== -1 : 已安排建造但尚未建造 / = 0:建造好了 / >0:建造好且升級好了 )
                    }else if(buildings[i].getId() == 6 && buildings[i].getLevel() > 0){
                        System.out.println("士兵等級已升級為" + buildings[i].getLevel() + " 等");
                        for (Human human : humans) {
                            //因為levelUP()裡面已經有寫判斷是否是士兵，所以可以直接跑完整個arrayList沒問題
                            human.levelUP();
                        }
                    }else{
                        System.out.println(buildings[i].getName() + " 建造完成 建築等級為" + (buildings[i].getLevel()+1));
                        if(buildings[i].getLevel()>0 && buildings[i].getId()!=6){   //為升級狀態 && 非兵工廠
                            buildingsInLab--;   //釋出可升級的空間
                        }
                        if(buildings[i].getId() == 4){
                            if(buildings[i].getLevel() == 0){
                                resource.upgradeWoodSpeed(3);
                            }else{
                                resource.upgradeWoodSpeed(2);
                            }
                            System.out.println("木材產量效率增加");
                        }
                        if(buildings[i].getId() == 5){
                            resource.upgradeSteelSpeed(1);
                            System.out.println("鋼鐵產量效率增加");
                        }
                    }
                }
            }else{
                break;
            }
        }
    }

    /**
     * 黨指令下達完畢
     * 會依造指令而去執行每個單位時間索賄發生的事情
     *  1.因人而產出的物資
     *  2.因建築而產出的人
     *  3.因時間到而升級的建築 & 文明等級 & 士兵等級
     *
     *
     * 處理 這個時間流動前的所有指令
     * @param thisRoundTimePass 這一輪指令的時間
     * @return 如果 回傳值為true 繼續遊戲  false 結束遊戲
     */
    public boolean doCityWorkAndTimePass(int thisRoundTimePass){
        //把時間流動前做的指令 && 決定流動的時間跑完
        while(thisRoundTimePass != 0){
            //遊戲時間 流動 1小時
            timePass();
            //生產 木&鋼
            gainResource();
            //建物.生成人()
            for (int i = 0; i < buildingCount; i++) {
                if(productCitizen(i)){
                    System.out.printf("第%d回合 有新市民出生,目前一共有%d個市民 ,閒置人數:%d\n",getGameTime()+1, getTotalCitizen(), freeCitizen);    //getTotalFreeMan()
                }
            }
            for (int i = 0; i < buildingCount; i++) {
                if(productSoldier(i)){
                    System.out.printf("第%d回合 有新戰士出生,目前一共有%d個戰士\n",getGameTime()+1, getTotalSolider());
                }
            }
            //建築.升級() +升級文明() + 升級士兵()
            buildingsUpgrade();

            //這是殭屍來襲時間( 每16小時來一次 )
            if(getGameTime() % 16 == 0){
                //用來 計算抵擋完殭屍後的人口狀況 和 算完後遊戲是否結束
                if(!liveOrDead()){
                    System.out.println("城鎮人口皆被消滅!");
                    System.out.println("Game Over");
                    return false;
                }
            }
            thisRoundTimePass--;
        }
        return true;
    }

    /**
     * 單純檢查是否可以升級or建造
     * @param index 在buildings[] 的位置
     * @return  可以升級/建造 true
     */
    public boolean checkOnlyForCanUpgrade(int index){
        if(buildings[index]!=null){
            if(!buildings[index].isReadyToUpgrade() ){  //非安排佇列中
                if(buildings[index].getLevel()==-1){    //鋼建造
                    if(buildings[index].getWoodCostCreate() <= resource.getTotalWood() && buildings[index].getSteelCostCreate() <= resource.getTotalSteel()){
                        return true;
                    }else {
                        return false;
                    }
                }else{
                    if(buildings[index].getWoodCostLevelUp() <= resource.getTotalWood() && buildings[index].getSteelCostLevelUp() <= resource.getTotalSteel()){
                        return true;
                    }else{
                        return false;
                    }
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * 檢查所有建築
     * 確認每一個建築物依照條件可不可以被建造
     * 條件有物資.文明等級
     *
     * @param index 要建造的建築物在buildings陣列裡的 索引
     * @return 如果回傳true 表示該建築物可以建造 且安排建造 回傳 false 代表該建築物沒有達到建造的條件
     */
    public boolean canUpgradeBuilding(int index){
        //文明等級==2時   同時建築代號為2 (研究所)   以及該研究所已經被建立  ---> 不給升級
        if(techLevel == 2 && buildings[index].getId()==2 &&  buildings[index].getLevel()>=0){
            //文明等級已經到2  不需要再升級一次
            return  false;
        }else{
            //該建築物已經安排建造但尚未建造好 (level = -1)
            if(buildings[index].getLevel() == -1){
                // 如果可以建造則安排他去建造 (可不可以 →判斷: 文明等級 / 所需要資源)， 傳入遊戲時間是用來設定建造完成時間用
                if (buildings[index].setToUpgrade(resource.getTotalWood(), resource.getTotalSteel(), techLevel, gameTime)) {
                    //消耗木頭
                    resource.takeWood(buildings[index].getWoodCostCreate());
                    //消耗鋼鐵
                    resource.takeSteel(buildings[index].getSteelCostCreate());
                    //總建築物數量 + 1
                    buildingCount++;
                    //可以建造新建築
                    return true;
                } else {
                    //沒有足夠資源去建造
                    return false;
                }
            }else if(buildingsInLab < numOfLab){ //先檢查有沒有空間 在去設定去升級
                 //如果建築物可以建造的話 安排建築物建造
                 if (buildings[index].setToUpgrade(resource.getTotalWood(), resource.getTotalSteel(), techLevel, gameTime)) {
                    //消耗的木頭
                    resource.takeWood(buildings[index].getWoodCostCreate());
                    //消耗的鋼鐵
                    resource.takeSteel(buildings[index].getSteelCostCreate());
                    //如果他是升級狀態(已經建造出來的話getLevel()會>=0 如果建築物還沒建造會等於-1) && 他不是兵工廠 (PS:兵工廠 ID = 6) 不會增加buildingsInLab
                    if(buildings[index].getLevel() >=0 && buildings[index].getId() != 6){
                        buildingsInLab++;
                    }
                    //可以建造或升級
                    return true;
                } else {
                    //沒有足夠的資源去升級 或 建造
                    return false;
                }
            }else{
                //目前沒有足夠的研究所，去支援升級
                return false;
            }
        }
    }

    /**
     * 檢查一下 這一波殭屍來襲 擋不擋得住
     * @return 回傳true 活著  false 死去
     */
    public boolean liveOrDead(){
        //這個數值是 最終結果 也就是 是否能夠抵擋這一波殭屍潮的判斷數 >0 死亡  <=0 存活
        int result = 0;
        //首先計算 所有殭屍 攻擊力總和
        for(int i = 0; i < ZOMBIE_TYPE; i ++){
            result += zombies[i].getAttack(getGameTime() / 16);
        }
        //走訪 humans 陣列，找尋每一個士兵出來戰鬥
        int totalHumans = humans.size();
        //由於目前humans 內有士兵以及市民  遇到市民跳過　→　index++  但若為士兵 他被消滅了  下一個human會補到原來位置上 因此不index++
        int index = 0;
        // 總共要跑 humans.size() 次
        for(int i = index; i < totalHumans; i++){
            if(result>0){
                if(humans.get(index).getIsSoldier()){
                    if(result >= humans.get(index).getValue()){  //判斷士兵是否還有身體沒被消滅
                        result -= humans.get(index).getValue(); //殭屍傷害扣掉士兵值
                        humans.remove(index);
                    }else {
                        result -= humans.get(index).getValue(); //殭屍傷害扣掉士兵值
                    }
                }else{
                    index++;
                }
            }else{
                break;
            }
        }
        // 用來記錄戰役結束後 士兵剩下人數
        int soldierRemain=0;
        for(int i=0 ; i<humans.size() ; i++){
            if(humans.get(i).getIsSoldier())
                soldierRemain++;
        }

        // 殭屍還沒殺完
        // 士兵全死
        totalHumans = humans.size();
        if(result > 0){
            // 再次走訪 humans 陣列，找尋每一個市民
            for(int i = 0; i < totalHumans ; i++){
                // 殭屍攻擊力還有剩下
                if(result > 0){
                    //殭屍攻擊力 - 市民攻擊力
                    result = result - humans.get(0).getValue(); //剩下都是市民 刪第0個就可以了
                    //該格市民死亡移除他
                    humans.remove(0);
                    /*
                    1.由於ArrayList的remove() 是直接把該格移除，後面往前移動。
                    2.所以如果直接一直往後刪除會造成跳號
                        (remove(index0) 之後 後面會馬上往前移動一格 → index1 = index0 index2 = index1)。
                        (在直接i++然後remove()， 會刪除到原本的index2)
                    */
                }else{
                    break;
                }
            }
            //由於市民要出來戰鬥，所以要更改所有市民工作計數 (伐木人 / 採鐵人)歸0
            woodMan=0;
            steelMan=0;
            //走訪人類的 ArrayList
            for (int i = 0; i < humans.size(); i++) {
                //遇到不是士兵的人(他是市民)
                if(!humans.get(i).getIsSoldier() && humans.get(i).getState().equals("Free")) {
                    //把他的狀態改成閒人
                    humans.get(i).setStateToFree();
                    //閒人數量+1
                    addFreeCitizen(1);
                }
            }
            //如果這個時候閒人數量 >0
            if(freeCitizen>0){
                System.out.println("這裡是誰? 我是哪裡? \n市民受到驚嚇 都忘記自己要做甚麼了\n");
            }
        }


        //還有人活著 遊戲繼續
        if(humans.size()!=0){
            //
            if(soldierRemain>0){
                System.out.println("不平靜的夜晚過去了\n你也活下來了\n");
            }else{
                System.out.println("不平靜的夜晚過去了\n你活了下來 但是\n戰士們為了拯救你 流光了鮮血\n");
            }
            return true;    //全死 遊戲結束
        }else{
            System.out.println("不平靜的夜晚終於過去了 但是你再也撐不住了\n");
            return false;
        }
    }

    /**
     * 顯示目前城市的所有資訊
    */
    public void showInfo(){
        //所有資源的資訊
        System.out.printf("第%d回合\n目前總資源量如下:\n" +
                "木材: %d , 鋼鐵: %d\n",getGameTime()+1, resource.getTotalWood(),resource.getTotalSteel());
        //所有人力資源的資訊
        System.out.printf("目前人力資源如下:\n" +
                "採木人: %d , 採鋼人: %d, 閒人: %d\n", woodMan,steelMan,freeCitizen);
        //所有人民的資訊
        System.out.printf("目前士兵量如下:\n" +
                "士兵: %d名, 市民: %d名\n", getTotalSolider(), getTotalCitizen());
        //顯示科技等級
        System.out.println("目前科技等級為:"+techLevel);
        //顯示下波攻擊倒數
        System.out.println("距下波攻擊還有 "+(16-getGameTime()%16)+" 小時");
        System.out.println("---------------------------------------------------");

        //所有建築物的數量
        int count = 0;
        //房屋數量
        int countBuilding1=0;
        //研究所數量
        int countBuilding2=0;
        //軍營數量
        int countBuilding3=0;
        //伐木場數量
        int countBuilding4=0;
        //煉鋼廠數量
        int countBuilding5=0;
        //兵工廠數量
        int countBuilding6=0;
        for(int i = 0; i < buildingCount; i ++){
            //若建築物已經被建造出來
            if(buildings[i].getLevel() >= 0){
                //總建築物數量+1
                count ++;
                if(buildings[i].getId()==1){
                    countBuilding1++;
                }else if(buildings[i].getId()==2){
                    countBuilding2++;
                }else if(buildings[i].getId()==3){
                    countBuilding3++;
                }else if(buildings[i].getId()==4){
                    countBuilding4++;
                }else if(buildings[i].getId()==5){
                    countBuilding5++;
                }else if(buildings[i].getId()==6){
                    countBuilding6++;
                }
            }
        }
        if(count == 0){
            System.out.println("目前沒有建好的建築!\n");
        }else{
            System.out.println("目前已經建造好的建築物有:");
            System.out.println("房屋:"+countBuilding1+" 研究所:"+countBuilding2+" 軍營:"+countBuilding3+
                    " 伐木場:"+countBuilding4+" 煉鋼廠:"+countBuilding5+" 兵工廠:"+countBuilding6+" \n還可建造建築物數量:"+(MAX_CAN_BUILD-getBuildingCount()));
        }

        System.out.println("目前尚可升級的建築物數量:"+(numOfLab - buildingsInLab)); //目前研究所數量  - 目前正在研究中的研究所
        System.out.println("------------------<以下為作業中建築>------------------");

        for(int i = 0; i < buildingCount; i ++){
            if(buildings[i].isReadyToUpgrade() && buildings[i].getLevel()==-1){ //建築物已經有被指定建造  以及該建築物等級為-1 (代表還沒被造出來)
                System.out.println(buildings[i].getName()+" 正在建造 剩餘時間:"+(buildings[i].getBuildTime()-(gameTime)+buildings[i].getBuildStart())+" 小時");
            }
        }
        for(int i = 0; i < buildingCount; i ++){
            if(buildings[i].isReadyToUpgrade() && buildings[i].getLevel()>-1){  //建築物已經有被指定建造  以及該建築物等級大於-1 (代表該建築物已經被造出來)
                System.out.println(buildings[i].getName()+" 正在升級 剩餘時間:"+(buildings[i].getBuildTime()-(gameTime)+buildings[i].getBuildStart())+" 小時");
            }
        }
        System.out.println("---------------------------------------------------");
    }
    /**
     * @return 文明等級
     */
    public int getTechLevel(){
        return techLevel;
        }

    /**
     * 獲取目前士兵總數
     * @return 目前士兵總數
     */
    public int getTotalSolider() {
        int soliderNum = 0;
        for (Human human : humans) {
            if (human.getIsSoldier()) {
                soliderNum += 1;
            }
        }
        return soliderNum;
    }

    /**
     * 獲取目前市民總數
     * @return 目前市民總數
     */
    public int getTotalCitizen(){
        int citizenNum = 0;
        for (Human human : humans) {
            if (!human.getIsSoldier()) {
                citizenNum += 1;
            }
        }
        return citizenNum;
    }

    /**
     * 把所有建築物陣列中的建築物 根據建築物ID 由小到大排
     */
    public void sortBuildingArrByID(){
        for(int i = 0; i < buildingCount - i; i ++){
            for(int j = 0; j < buildingCount - i - 1; j ++){
                if(buildings[j].getId() > buildings[j + 1].getId()){
                    Building tmpBuilding = buildings[j + 1];
                    buildings[j + 1] = buildings[j];
                    buildings[j] = tmpBuilding;
                }
            }
        }
    }
}
