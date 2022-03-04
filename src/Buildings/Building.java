package Buildings;

public class Building {
    /**
     * 編號
     */
    private final int id;
    /**
     * 建築名稱
     */
    private final String name;
    /**
     * 開始 建築物 剛建造完的時間
     */
    private int createTime;
    /**
     * 開始 建造時間
     */
    private int buildStart;
    /**
     * 建造 需要的時間
     */
    private int buildTime;
    /**
     * 升級需要的時間
     */
    private int upgradeTime;
    /**
     * 該建築的等級  -1:未建造 0:已建造 1~n:升級次數
     */
    private int level;
    /**
     * 建造的文明需求
     */
    private int techLevelNeed;
    /**
     * 研究所建了嗎
     */
    private static boolean isLabBuild = false;
    /**
     * 兵工廠建造了嗎
     */
    private static boolean isArsenalBuild = false;
    /**
     * 是否要準備要升級了
     */
    private boolean readyToUpgrade;
    /**
     * 建造需要木
     */
    private int woodCostCreate;
    /**
     * 建造需要鋼
     */
    private int steelCostCreate;
    /**
     * 升級需要木
     */
    final int woodCostLevelUp;
    /**
     * 升級需要鋼
     */
    final int steelCostLevelUp;

    /**
     * 建構子
     * @param id 建築物ID  (1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠)
     * @param name 建築物名稱
     * @param buildStart 建築物開始時間
     * @param buildTime 建築物持續時間
     * @param upgradeTime 建築物升級時間
     * @param level 建築物等級 預設-1  建好 0 升級過 1~2,147,483,647
     * @param techLevelNeed 需要文明等級
     * @param readyToUpgrade 建築物是否在建築，建築中 -> true
     * @param woodCostCreate 創建所需要的木頭量
     * @param steelCostCreate 創建所需要的鋼鐵量
     * @param woodCostLevelUp 升級所需要的木頭量
     * @param steelCostLevelUp 升級所需要的鋼鐵量
     */
    public Building(int id , String name , int buildStart, int buildTime, int upgradeTime ,
                    int level, int techLevelNeed, boolean readyToUpgrade,
                    int woodCostCreate, int steelCostCreate, int woodCostLevelUp, int steelCostLevelUp){
        //建築物ID  (1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠)
        this.id=id;
        //建築物名稱
        this.name=name;
        //建築物開始時間
        this.buildStart=buildStart;
        //建築物持續時間
        this.buildTime=buildTime;
        //建築物升級時間
        this.upgradeTime=upgradeTime;
        //建築物等級 預設-1  建好 0 升級過 1~2,147,483,647
        this.level=level;
        //需要文明等級
        this.techLevelNeed = techLevelNeed;
        //建築物是否在建築，建築中 -> true
        this.readyToUpgrade=readyToUpgrade;
        //創建所需要的木頭量
        this.woodCostCreate=woodCostCreate;
        //創建所需要的鋼鐵量
        this.steelCostCreate=steelCostCreate;
        //升級所需要的木頭量
        this.woodCostLevelUp=woodCostLevelUp;
        //升級所需要的鋼鐵量
        this.steelCostLevelUp=steelCostLevelUp;
        //建築物 剛建造完的時間 (那一個moment)，用來計算建築生產(和buildTime gameTime去做計算)
        createTime = -1;
    }

    /**
     * 建築物編號 1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠
     * @return 建築物編號
     */
    public int getId(){
        return id;
    }

    /**
     * 總共有(房屋/研究所/軍營/伐木場/煉鋼廠/兵工廠)
     * @return 建築物名稱
     */
    public String getName(){
        return name;
    }

    /**
     * 獲得建築物初次建造完成的時間 (CreateTime: 只會在第一次建好的時候，被設定一次)
     * @return 建築物初次建造完成的時間
     */
    public int getCreateTime(){
        return createTime;
    }


    /**
     * @return 獲得 建築物被設定去建造 或 升級時 的開始時間(設定的那一個瞬間 記錄下來 = 世紀帝國按下 升級帝王時代的時候)
     */
    public int getBuildStart(){
        return buildStart;
    }


    /** 取得建築物 1.建造時間 2.升級時間 (建造完成後 升級時間會取代建造時間 成為新的建造時間 一起用這樣)
     * @return 建築物的 建造時間/升級時間
     */
    public int getBuildTime(){
        return buildTime;
    }

    /**
     * 取得建築物等級
     * @return 建築物等級
     */
    public int getLevel(){
        return level;
    }

    /**
     * 取得升級所需要的木頭量
     * @return 升級所需要的木頭量
     */
    public int getWoodCostLevelUp(){
        return woodCostLevelUp;
    }

    /**
     * 取得升級所需要的鋼鐵量
     * @return 升級所需要的鋼鐵量
     */
    public int getSteelCostLevelUp(){
        return steelCostLevelUp;
    }

    /**
     * 可否升級用
     * 安排建築去升級 如果該建築已安排 會回傳false
     * @param wood  升級/建造 需要的木頭
     * @param steel 升級/建造 需要的鋼鐵
     * @param techLevel 文明等級
     * @param gameTime 遊戲的標準時間
     * @return 如果可以安排 / 升級 回傳true 不行回傳 false
     */
    public boolean setToUpgrade(int wood,int steel, int techLevel, int gameTime){
        //如果已安排建築or升級
        if(readyToUpgrade){
            return false; //升級or建造中 return false
        }else{
            //為了方便擴充 所以使用switch
            if(level == -1) {
                //檢查物資 文明是否足夠
                if(wood >= woodCostCreate && steel >= steelCostCreate && techLevel >= techLevelNeed){
                    readyToUpgrade = true;
                    //設定建造開始時間
                    buildStart = gameTime;
                    return true;
                }else{
                    return false;
                }
            }else {
                //lab建造後才能升級
                if(isLabBuild){
                    //研究所只能升級一次
                    if(id==2 && level>=1){
                        return false;
                    }else if(wood >= woodCostLevelUp && steel >= steelCostLevelUp ){  //檢查物資 文明是否足夠
                        //該建築物是否準備好要升級
                        readyToUpgrade = true;
                        woodCostCreate = woodCostLevelUp;
                        steelCostCreate = steelCostLevelUp;
                        //設定建造開始時間
                        buildStart = gameTime;
                            /*
                                在isUpgradeFinish()裡面的運算，由於都是使用 buildStart + buildTime，所以才會做替換。
                                替換原因:
                                1.建造好之後 會把buildStart = gameTime(建造好的當下紀錄成目前遊戲總時間)
                                2.把buildTime(建造所需時間) =(換成) upgradeTime(升級所需時間) [因為不需要建造所需時間了]
                             */
                        buildTime = upgradeTime;
                        return true;
                    //物資&&文明 是否足夠
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }
    }

    /**
     * 判斷 建築物/等級 是否已經 建造/升級完成
     * @param gameTime 遊戲總時間
     * @return true 建築物/等級 已經 建造/升級 完成 false 還沒完成
     */
    public boolean isUpgradeFinish(int gameTime){
        //該建築有被安排才會開始建造
        if(readyToUpgrade){
            //建造(升級)完成 的條件為: [建造(升級)開始時間 + buildTime(建造所需時間/升級所需時間) <= 遊戲總時間]
            if(buildStart + buildTime <= gameTime){
                //如果研究所建立
                if(id == 2){
                    isLabBuild = true;
                }
                //如果兵工廠建立
                if(id == 6){
                    isArsenalBuild = true;
                }
                //建築物等級 + 1
                level++;
                //
                readyToUpgrade = false;
                //如果此建築物 一建造完 便記錄該建造時間
                if(level == 0){
                    createTime = gameTime;
                }
                return true;
            }else{
                //尚未建造完成
                return false;
            }
        }else{
            //尚未安排建造
            return false;
        }
    }

    /**
     * @return 是否已經準備好要升級
     */
    public boolean isReadyToUpgrade(){
        return readyToUpgrade;
    }

    /**
     * @return  取得 創建要的木頭數
     */
    public int getWoodCostCreate(){
        return woodCostCreate;
    }

    /**
     * @return 取得創建要的鋼鐵數
     */
    public int getSteelCostCreate(){
        return steelCostCreate;
    }


}
