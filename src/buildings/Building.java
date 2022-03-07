package buildings;
import main.Resource;///

public abstract class Building {
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
     * 建造的科技需求
     */
    private int techLevelNeedBuild;
    /**
     * 升級的科技需求
     */
    private int techLevelNeedUpgrade;
    /**
     * 是否在運轉
     */
    private boolean isWorking;
    /**
     * 是否正在升級中
     */
    private boolean isUpgrading;
    /**
     * 建築血量
     */
    private int hp;

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
     * 建造需要瓦斯
     */
    private int gasCostCreate;
    /**
     * 升級需要瓦斯
     */
    final int gasCostLevelUp;
    ///張文偉
    /**
     * 生產所需資源
     */
    protected int woodForProduction;
    protected int steelForProduction;
    protected int gasForProduction;
    /**
     * 已經從City拿取的資源
     */
    private int woodGeted;
    private int steelGeted;
    private int gasGeted;

    /**
     * 建構子
     * @param id 建築物ID  (1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠)
     * @param name 建築物名稱
     * @param buildStart 建築物開始時間
     * @param buildTime 建築物持續時間
     * @param upgradeTime 建築物升級時間
     * @param level 建築物等級 初始 0 建好 1 升級過2~n
     * @param techLevelNeedBuild 需要科技等級
     * @param hp 建築物血量
     * @param woodCostCreate 創建所需要的木頭量
     * @param steelCostCreate 創建所需要的鋼鐵量
     * @param woodCostLevelUp 升級所需要的木頭量
     * @param steelCostLevelUp 升級所需要的鋼鐵量
     * @param isWorking 建築物是否在運轉
     * @param gasCostCreate 創建所需要的瓦斯量
     * @param gasCostLevelUp 升級所需要的瓦斯量
     */
    public Building(int id, String name, int buildStart, int buildTime, int upgradeTime,
                    int level, int techLevelNeedBuild,int techLevelNeedUpgrade, int hp,
                    int woodCostCreate, int steelCostCreate, int woodCostLevelUp,
                    int steelCostLevelUp, boolean isWorking,
                    int gasCostCreate,int gasCostLevelUp){
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
        //需要科技等級建造
        this.techLevelNeedBuild = techLevelNeedBuild;
        //需要科技等級升級
        this.techLevelNeedUpgrade = techLevelNeedUpgrade;
        //建築物是否在建築，建築中 -> true
        this.readyToUpgrade=true;
        //血量
        this.hp = hp;
        //創建所需要的木頭量
        this.woodCostCreate=woodCostCreate;
        //創建所需要的鋼鐵量
        this.steelCostCreate=steelCostCreate;
        //升級所需要的木頭量
        this.woodCostLevelUp=woodCostLevelUp;
        //升級所需要的鋼鐵量
        this.steelCostLevelUp=steelCostLevelUp;
        //建築物是否在運轉
        this.isWorking=isWorking;
        //創建所需要的瓦斯量
        this.gasCostCreate=gasCostCreate;
        //升級所需要的瓦斯量
        this.gasCostLevelUp=gasCostLevelUp;
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
     * 取得升級所需要的瓦斯量
     * @return 升級所需要的瓦斯量
     */
    public int getGasCostLevelUp() {
        return gasCostLevelUp;
    }

    //    /**
//     * 可否升級用
//     * 安排建築去升級 如果該建築已安排 會回傳false
//     * 建築物level屬性( = 建築物升級的次數) ↓↓
//     *  level分三個階段 -1 (代表已經安排建造但還沒建造完成)  0還沒升級過已經建造完成()  >0 每升級一次多1
//     *
//     * 因為建築物、科技、士兵 升級的規則相同
//     * 所以用
//     * a.研究所升級 代表 科技升級
//     * b.兵工廠升級 代表 士兵升級
//     *
//     * @param wood  升級/建造 需要的木頭
//     * @param steel 升級/建造 需要的鋼鐵
//     * @param techLevel 科技等級
//     * @param gameTime 遊戲的標準時間
//     * @return 如果可以安排 / 升級 回傳true 不行回傳 false
//     */
//    public boolean setToUpgrade(int wood,int steel, int techLevel, int gameTime){
//        //如果已安排建築or升級
//        if(readyToUpgrade){
//            return false; //升級or建造中 return false
//        }else{
//            //為了方便擴充 所以使用switch
//            if(level == -1) { //尚未有此建築
//                //檢查物資 科技是否足夠
//                if(wood >= woodCostCreate && steel >= steelCostCreate && techLevel >= techLevelNeed){
//                    readyToUpgrade = true;
//                    //設定建造開始時間
//                    buildStart = gameTime;
//                    return true;
//                }else{
//                    return false;
//                }
//            }else { //已經有此建築
//                //lab建造後才能升級
//                if(isLabBuild){
//                    //研究所只能升級一次
//                    if(id==2 && level>=1){
//                        return false;
//                    }else if(wood >= woodCostLevelUp && steel >= steelCostLevelUp ){  //物資 科技足夠
//                        //該建築物是否準備好要升級
//                        readyToUpgrade = true;
//                        woodCostCreate = woodCostLevelUp; //第一次建造，第二次開始為升級
//                        steelCostCreate = steelCostLevelUp;
//                        //設定建造開始時間
//                        buildStart = gameTime;
//                            /*
//                                在isUpgradeFinish()裡面的運算，由於都是使用 buildStart + buildTime，所以才會做替換。
//                                替換原因:
//                                1.建造好之後 會把buildStart = gameTime(建造好的當下紀錄成目前遊戲總時間)
//                                2.把buildTime(建造所需時間) =(換成) upgradeTime(升級所需時間) [因為不需要建造所需時間了]
//                             */
//                        buildTime = upgradeTime;
//                        return true;
//                    //物資&&科技不足夠
//                    }else{
//                        return false;
//                    }
//                }else{
//                    //尚未建造lab不可升級
//                    return false;
//                }
//            }
//        }
//    }
//
//    /**
//     * 判斷 建築物/等級 是否已經 建造/升級完成
//     * @param gameTime 遊戲總時間
//     * @return true 建築物/等級 已經 建造/升級 完成 false 還沒完成
//     */
//    public boolean isUpgradeFinish(int gameTime){
//        //該建築有被安排才會開始建造
//        if(readyToUpgrade){
//            //建造(升級)完成 的條件為: [建造(升級)開始時間 + buildTime(建造所需時間/升級所需時間) <= 遊戲總時間]
//            if(buildStart + buildTime <= gameTime){
//                //如果研究所建立
//                if(id == 2){
//                    isLabBuild = true;
//                }
//                //如果兵工廠建立
//                if(id == 6){
//                    isArsenalBuild = true;
//                }
//                //建築物等級 + 1
//                level++;
//                //
//                readyToUpgrade = false;
//                //如果此建築物 一建造完 便記錄該建造時間
//                if(level == 0){
//                    createTime = gameTime;
//                }
//                return true;
//            }else{
//                //尚未建造完成
//                return false;
//            }
//        }else{
//            //尚未安排建造
//            return false;
//        }
//    }

    public boolean isEnoughProduction(){///是否已經拿取資源來生產
        return ( (woodForProduction == woodGeted) &&
                (steelForProduction == steelGeted) &&
                ((gasForProduction == gasGeted)) );
    }

    /**
     * 建築物生產
     * @param resource 建築的資源
     * @return 是否有拿取資源
     */
    public boolean takeResource(Resource resource){
        if(     resource.canTakeWood(woodForProduction)&&
                resource.canTakeSteel(steelForProduction) &&
                resource.canTakeGas(gasForProduction) )
        {
            resource.takeWood(woodForProduction);
            resource.takeSteel(steelForProduction);
            resource.takeGas(gasForProduction);
            woodGeted+=woodForProduction;
            steelGeted+=steelForProduction;
            gasGeted+=gasForProduction;
            return true;
        }
        return false;
    }

    public void consumeResource(){///生產完畢歸零
        woodGeted = 0;
        steelGeted = 0;
        gasGeted = 0;
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
    /**
     * @return 取得創建要的瓦斯數
     */
    public int getGasCostCreate() {
        return gasCostCreate;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public void setBuildStart(int buildStart) {
        this.buildStart = buildStart;
    }

    public void setBuildTime(int buildTime) {
        this.buildTime = buildTime;
    }

    public int getUpgradeTime() {
        return upgradeTime;
    }

    public void setUpgradeTime(int upgradeTime) {
        this.upgradeTime = upgradeTime;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTechLevelNeedBuild() {
        return techLevelNeedBuild;
    }

    public void setTechLevelNeedBuild(int techLevelNeedBuild) {
        this.techLevelNeedBuild = techLevelNeedBuild;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public void setReadyToUpgrade(boolean readyToUpgrade) {
        this.readyToUpgrade = readyToUpgrade;
    }

    public void setWoodCostCreate(int woodCostCreate) {
        this.woodCostCreate = woodCostCreate;
    }

    public void setSteelCostCreate(int steelCostCreate) {
        this.steelCostCreate = steelCostCreate;
    }

    public boolean isUpgrading() {
        return isUpgrading;
    }

    public void setUpgrading(boolean upgrading) {
        isUpgrading = upgrading;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }


}
