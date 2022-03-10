package buildings;
import main.Resource;

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
     * 被創造的時間
     */
    private int createTime;
    /**
     * 建造開始的時間
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
    private int woodCostLevelUp;
    /**
     * 升級需要鋼
     */
    private int steelCostLevelUp;

    /**
     * 建造需要瓦斯
     */
    private int gasCostCreate;
    /**
     * 升級需要瓦斯
     */
    private int gasCostLevelUp;
    /**
     * 生產所需資源
     */
    protected int woodForProduction;
    protected int steelForProduction;
    protected int gasForProduction;
    /**
     * 已經從City拿取的資源
     */
    private int woodGot;
    private int steelGot;
    private int gasGot;

    /**
     * 建構子
     * @param id 建築物ID  (1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠 7.瓦斯場 8.飛機工場)
     * @param name 建築物名稱
     * @param buildTime 建築物持續時間
     * @param upgradeTime 建築物升級時間
     * @param level 建築物等級 預設-1  建好 0 升級過 1~2,147,483,647
     * @param techLevelNeed 需要科技等級
     * @param isUpgrading 建築物是否在建築，建築中 -> true
     * @param hp 建築物血量
     * @param woodCostCreate 創建所需要的木頭量
     * @param steelCostCreate 創建所需要的鋼鐵量
     * @param woodCostLevelUp 升級所需要的木頭量
     * @param steelCostLevelUp 升級所需要的鋼鐵量
     * @param isWorking 建築物是否在運轉
     * @param gasCostCreate 創建所需要的瓦斯量
     * @param gasCostLevelUp 升級所需要的瓦斯量
     */
    public Building(int id, String name,int buildTime, int upgradeTime,
                    int level, int techLevelNeed, boolean isUpgrading, int hp,
                    int woodCostCreate, int steelCostCreate, int woodCostLevelUp,
                    int steelCostLevelUp, boolean isWorking,
                    int gasCostCreate,int gasCostLevelUp){
        //建築物ID  (1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠)
        this.id=id;
        //建築物名稱
        this.name=name;
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
        this.createTime = -1;
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

    /**
     * 是否有足夠的資源建造
     * @param resource 資源
     * @return
     */
    public boolean isEnoughBuild(Resource resource){
        return ( (woodCostCreate <= resource.getTotalWood()) &&
                (steelCostCreate <= resource.getTotalSteel()) &&
                ((gasCostCreate <= resource.getTotalGas())) );
    }

    /**
     * 消耗資源，建築物建造
     * @param resource 建築的資源
     */
    public void takeResourceBuild(Resource resource) {
        resource.takeWood(woodCostCreate);
        resource.takeSteel(steelCostCreate);
        resource.takeGas(gasCostCreate);
    }

    /**
     * 是否有足夠的資源升級
     * @param resource
     * @return
     */
    public boolean isEnoughUpgrade(Resource resource){
        return ( (woodCostLevelUp <= resource.getTotalWood()) &&
                (steelCostLevelUp <= resource.getTotalSteel()) &&
                ((gasCostLevelUp <= resource.getTotalGas())) );
    }

    /**
     * 消耗資源，建築物升級
     * @param resource 建築的資源
     */
    public void takeResourceUpgrade(Resource resource) {
        resource.takeWood(woodCostLevelUp);
        resource.takeSteel(steelCostLevelUp);
        resource.takeGas(gasCostLevelUp);
    }

    /**
     * 是否有足夠的資源生產
     * @param resource
     * @return
     */
    public boolean isEnoughProduction(Resource resource){
        return ( (woodForProduction <= resource.getTotalWood()) &&
                (steelForProduction <= resource.getTotalSteel()) &&
                ((gasForProduction <= resource.getTotalGas())) );
    }

    /**
     * 消耗資源，建築物生產
     * @param resource 建築的資源
     */
    public void takeResourceProduce(Resource resource) {
        resource.takeWood(woodForProduction);
        resource.takeSteel(steelForProduction);
        resource.takeGas(gasForProduction);
    }

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

    public int getUpgradeTime() {
        return upgradeTime;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTechLevelNeedBuild() {
        return techLevelNeedBuild;
    }

    public int getTechLevelNeedUpgrade() {
        return techLevelNeedUpgrade;
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

    public boolean isUpgrading() {
        return isUpgrading;
    }

    public void setUpgrading(boolean upgrading) {
        isUpgrading = upgrading;
    }

    public int getHp() {
        return hp;
    }

    public void setWoodCostLevelUp(int woodCostLevelUp){
        this.woodCostLevelUp = woodCostLevelUp;
    }

    public void setSteelCostLevelUp(int steelCostLevelUp){
        this.steelCostLevelUp = steelCostLevelUp;
    }

    public void setGasCostLevelUp(int gasCostLevelUp){
        this.gasCostLevelUp = gasCostLevelUp;
    }

    public void getDamage(int damage) {
        this.hp-=damage;
        if(this.hp<=0){
            this.hp=0;
        }
    }

    /**
     * 建築的效果說明
     * @level 幾等時的作用
     * @return 建築的效果說明
     */
    public abstract String buildingDetail(int level);


}
