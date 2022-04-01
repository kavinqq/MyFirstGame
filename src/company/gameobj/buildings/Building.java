package company.gameobj.buildings;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gameobj.message.HintDialog;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;
import oldMain.Resource;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class Building extends GameObject implements CommandSolver.MouseCommandListener {

    public enum State {
        UNDER_CONSTRUCT,
        COMPLETED,
        UPGRADING;
    }

//    public Image getUnderConstructionImg(){
//        return underConstructionImg;
//    }
//
//    public Image getImg(){
//        return img;
//    }
    //建築物上的控制升級或開啟關閉的物件
    public class Icon extends GameObject {
        //建築物顯示提示框
        private HintDialog hintDialog;
        //是否顯示提示框
        private boolean isShowMessage;
        //是否被點擊
        private boolean isPressed;


        public Icon(int x, int y, String message) {

            super(x, y, Global.BUILDING_ICON_WIDTH, Global.BUILDING_ICON_HEIGHT);
            hintDialog = new HintDialog();
            hintDialog.setHintMessage(message);
            isPressed = false;
        }
        //取得點擊
        public boolean getPressed() {
            return isPressed;
        }
        //關閉點擊狀態
        public void setPressed(boolean bool) {
            isPressed = bool;
        }
        //設置訊息內容
        protected void setMessage(String message) {
            hintDialog.setHintMessage(message);
        }
        //取得是否顯示訊息
        protected boolean isShowMessage() {
            return isShowMessage;
        }
        //得到對話框類
        protected HintDialog getHintDialog() {
            return hintDialog;
        }

        @Override
        public void paintComponent(Graphics g) {
            //畫文字訊息
            if (isShowMessage) {
                hintDialog.paint(g);
            }

        }

        @Override
        public void update() {

        }

        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long TrigTime) {

            if (state == CommandSolver.MouseState.MOVED) {
                if (isEntered(e.getX(), e.getY())) {
                    hintDialog.mouseTrig(e, state, TrigTime);
                    isShowMessage = true;
                    return;
                }
            }
            isShowMessage = false;
        }
    }

    protected class UpGradeIcon extends Icon {

        private Image upGradeIcon;
        public boolean isUpgrade;

        public UpGradeIcon(int x, int y, String message) {
            super(x, y, message);
            upGradeIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().upGradeIcon());
        }

        public boolean isUpgrade() {
            return isUpgrade;
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(upGradeIcon, painter().left(), painter().top(), Global.BUILDING_ICON_WIDTH, Global.BUILDING_ICON_HEIGHT, null);
            if (isShowMessage()) {
                getHintDialog().paint(g);
            }
        }

        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
            super.mouseTrig(e, state, trigTime);
            switch (state) {
                case PRESSED: {
                    if (isEntered(e.getX(), e.getY())) {
                        setPressed(true);
                        break;
                    }
                }
                default: {
                    setPressed(false);
                }
            }
        }

    }

    protected class WorkingIcon extends Icon {

        private Image noWorkingIcon;
        private Image workingIcon;
        private String message;

        public WorkingIcon(int x, int y, String message) {
            super(x, y, message);
            this.message = message;
            noWorkingIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().noWorkingIcon());
            workingIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().workingIcon());
        }

        @Override
        public void paintComponent(Graphics g) {
            //運作中
            if (isWorking) {
                g.drawImage(workingIcon, painter().left(), painter().top(), Global.BUILDING_ICON_WIDTH, Global.BUILDING_ICON_HEIGHT, null);

            }
            //非運作中
            else {
                g.drawImage(noWorkingIcon, painter().left(), painter().top(), Global.BUILDING_ICON_WIDTH, Global.BUILDING_ICON_HEIGHT, null);

            }
            if (isShowMessage()) {
                getHintDialog().paint(g);
            }
        }

        @Override
        public void update() {


            if (isWorking) {
                setMessage("正在生產" + message + "中");
            } else {
                setMessage("停止生產" + message + "中");
            }
        }

        public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
            super.mouseTrig(e, state, trigTime);
            switch (state) {
                case PRESSED : {
                    if (isEntered(e.getX(), e.getY())) {
                        isWorking = !isWorking;
                    }
                }
            }
        }
    }

    private ArrayList<Icon> icons;
    private Image img;

    private int woodRequired;
    private int steelRequired;
    private int gasRequired;

    public ArrayList<Icon> getIcons() {
        return icons;
    }

    private boolean canCatchBuilding;
    //圖片路徑
    private String imgPath;
    /**
     * 編號
     */
    private int id;
    /**
     * 建築名稱
     */
    private String name;
    /**
     * 被創造的時間
     */
    private int createTime;
    /**
     * 建造開始的時間
     */
    private int buildStartTime;
    /**
     * 建造 需要的時間
     */
    private int buildTime;
    /**
     * 升級需要的時間
     */
    private int upgradeTime;

    //升級開始時間
    private int upgradeStartTime;
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
    //科技等級是否在升級
    private boolean isTechUpgrading;

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
     * 建造需要瓦斯P
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
    //建造中的圖
    private Image underConstructionImg;

    //public static Building SelectBuilding;
    //是否顯示Icon
    private boolean isShowIcon;
    /**
     * 已經從City拿取的資源
     */
    private int woodGot;
    private int steelGot;
    private int gasGot;


    public Building(int x, int y, int width, int height) {
        super(x, y, width, height);
        buildingInit(x, y);
    }

    /**
     * 視覺化後都用這個
     * @param x 建築物的left
     * @param y 建築物的top
     */

    public Building(int x, int y) {

        super(x, y, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);

        //訂出 初始鏡頭 他要回到的位置
        setBuildingOriginalX(Global.SUM_OF_CAMERA_MOVE_VX);
        setBuildingOriginalY(Global.SUM_OF_CAMERA_MOVE_VY);

        buildingInit(x, y);
    }

    public Building() {
        super(0, 0, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);
        buildingInit(0, 0);
    }

    //建築物初始化(){
    protected void buildingInit(int x, int y) {
        //建築物是否在建築，建築中 -> true
        this.readyToUpgrade = true;
        //建築物是否在運轉
        this.isWorking = false;

        this.isUpgrading = false;
        //建築物 剛建造完的時間 (那一個moment)，用來計算建築生產(和buildTime gameTime去做計算)
        this.createTime = -1;

        underConstructionImg = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().ingBuild());

        icons = new ArrayList<>();
    }

    protected void imgInit() {
        img = SceneController.getInstance().imageController().tryGetImage(imgPath);
    }

    //設定是否升級
    public void setTechUpgrading(boolean isTechUpgrading){
        this.isTechUpgrading=isTechUpgrading;
    }

    public boolean getIsTechUpgrading(){
        return isTechUpgrading;
    }

    /**
     * set給子類建構用
     * @param id
     * @return
     */
    protected Building setId(int id) {
        Building.this.id = id;
        return this;
    }
    //設定給子類用
    protected Building setName(String name) {
        Building.this.name = name;
        return this;
    }
    //設定給子類用
    protected Building setBuildTime(int buildTime) {
        Building.this.buildTime = buildTime;
        return this;
    }
    //設定給子類用
    protected Building setTechLevelNeedBuild(int buildTime) {
        Building.this.techLevelNeedBuild = buildTime;
        return this;
    }
    //設定給子類用
    protected Building setUpgradeTime(int upgradeTime) {
        Building.this.upgradeTime = upgradeTime;
        return this;
    }
    //設定給子類用
    protected Building setTechLevelNeedUpgrade(int needTechLevel) {
        Building.this.techLevelNeedUpgrade = needTechLevel;
        return this;
    }

    //設定給子類用
    protected Building setLevelC(int level) {
        Building.this.level = level;
        return this;
    }
    //設定給子類用
    protected Building setHp(int hp) {
        Building.this.hp = hp;
        return this;
    }
    //設定給子類用
    protected Building setWoodCostCreate(int woodCostCreate) {
        Building.this.woodCostCreate = woodCostCreate;
        return this;
    }
    //設定給子類用
    protected Building setSteelCostCreate(int steelCostCreate) {
        Building.this.steelCostCreate = steelCostCreate;
        return this;
    }
    //設定給子類用
    protected Building setWoodCostLevelUpC(int woodCostLevelUp) {
        Building.this.woodCostLevelUp = woodCostLevelUp;
        return this;
    }
    //設定給子類用
    protected Building setSteelCostLevelUpC(int steelCostLevelUp) {
        Building.this.steelCostLevelUp = steelCostLevelUp;
        return this;
    }
    //設定給子類用
    protected Building setGasCostCreate(int gasCostCreate) {
        Building.this.gasCostCreate = gasCostCreate;
        return this;
    }
    //設定給子類用
    protected Building setGasCostLevelup(int gasCostLevelup) {
        Building.this.gasCostLevelUp = gasCostLevelUp;
        return this;
    }
    //設定給子類用
    protected Building setImgPath(String path) {
        Building.this.imgPath = path;
        return this;
    }

    //取得施工中圖片
    public Image getUnderConstructionImg(){
        return underConstructionImg;
    }

    //取得建築物圖片
    public Image getImg(){
        return img;
    }

    /**
     * 建築物編號 1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠
     *
     * @return 建築物編號
     */
    public int getId() {
        return id;
    }

    /**
     * 總共有(房屋/研究所/軍營/伐木場/煉鋼廠/兵工廠)
     *
     * @return 建築物名稱
     */
    public String getName() {
        return name;
    }

    /**
     * 獲得建築物初次建造完成的時間 (CreateTime: 只會在第一次建好的時候，被設定一次)
     *
     * @return 建築物初次建造完成的時間
     */
    public int getCreateTime() {
        return createTime;
    }


    /**
     * @return 獲得 建築物被設定去建造 或 升級時 的開始時間(設定的那一個瞬間 記錄下來 = 世紀帝國按下 升級帝王時代的時候)
     */
    public int getBuildStart() {
        return buildStartTime;
    }

    public int getBuildEnd() {
        return buildStartTime + buildTime;
    }


    public boolean isShowIcon() {
        return isShowIcon;
    }

    //控制是否顯示Icon
    public void setIsShowIcon(boolean b) {
        isShowIcon = b;
    }

    /**
     * 取得建築物 1.建造時間 2.升級時間 (建造完成後 升級時間會取代建造時間 成為新的建造時間 一起用這樣)
     *
     * @return 建築物的 建造時間/升級時間
     */
    public int getBuildTime() {
        return buildTime;
    }

    public int getUpgradeTime() {
        return upgradeTime;
    }

    //改BuildingNode會用到
//    public int getUpgradeStartTime(){
//        return upgradeStartTime;
//    }
//
//    public int getUpgradeEndTime(){
//        return upgradeTime+upgradeStartTime;
//    }

    /**
     * 取得建築物等級
     *
     * @return 建築物等級
     */
    public int getLevel() {
        return level;
    }

    /**
     * 取得升級所需要的木頭量
     *
     * @return 升級所需要的木頭量
     */
    public int getWoodCostLevelUp() {
        return woodCostLevelUp;
    }

    /**
     * 取得升級所需要的鋼鐵量
     *
     * @return 升級所需要的鋼鐵量
     */
    public int getSteelCostLevelUp() {
        return steelCostLevelUp;
    }

    /**
     * 取得升級所需要的瓦斯量
     *
     * @return 升級所需要的瓦斯量
     */
    public int getGasCostLevelUp() {
        return gasCostLevelUp;
    }

    //取得木頭所需物資
    public int getWoodRequired() {
        return woodRequired;
    }

    //取得鋼鐵所需物資
    public int getSteelRequired() {
        return steelRequired;
    }

    //取得瓦斯所需物資
    public int getGasRequired() {
        return gasRequired;
    }


    /**
     * 是否有足夠的資源建造
     *
     * @param resource 資源
     * @return
     */
    public boolean isEnoughBuild(Resource resource) {
        return ((woodCostCreate <= resource.getTotalWood()) &&
                (steelCostCreate <= resource.getTotalSteel()) &&
                ((gasCostCreate <= resource.getTotalGas())));
    }

    /**
     * 消耗資源，建築物建造
     *
     * @param resource 建築的資源
     */
    public void takeResourceBuild(Resource resource) {
        resource.takeWood(woodCostCreate);
        resource.takeSteel(steelCostCreate);
        resource.takeGas(gasCostCreate);
    }

    /**
     * 是否有足夠的資源升級
     *
     * @param resource
     * @return
     */
    public boolean isEnoughUpgrade(Resource resource) {
        return ((woodCostLevelUp <= resource.getTotalWood()) &&
                (steelCostLevelUp <= resource.getTotalSteel()) &&
                ((gasCostLevelUp <= resource.getTotalGas())));
    }

    /**
     * 消耗資源，建築物升級
     *
     * @param resource 建築的資源
     */
    public void takeResourceUpgrade(Resource resource) {
        resource.takeWood(woodCostLevelUp);
        resource.takeSteel(steelCostLevelUp);
        resource.takeGas(gasCostLevelUp);
    }

    /**
     * 是否有足夠的資源生產
     *
     * @param resource
     * @return
     */
    public boolean isEnoughProduction(Resource resource) {
        return ((woodForProduction <= resource.getTotalWood()) &&
                (steelForProduction <= resource.getTotalSteel()) &&
                ((gasForProduction <= resource.getTotalGas())));
    }

    /**
     * 消耗資源，建築物生產
     *
     * @param resource 建築的資源
     */
    public void takeResourceProduce(Resource resource) {
        resource.takeWood(woodForProduction);
        resource.takeSteel(steelForProduction);
        resource.takeGas(gasForProduction);
    }

    public boolean isReadyToUpgrade() {
        return readyToUpgrade;
    }

    /**
     * @return 取得 創建要的木頭數
     */
    public int getWoodCostCreate() {
        return woodCostCreate;
    }

    /**
     * @return 取得創建要的鋼鐵數
     */
    public int getSteelCostCreate() {
        return steelCostCreate;
    }

    /**
     * @return 取得創建要的瓦斯數
     */
    public int getGasCostCreate() {
        return gasCostCreate;
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


    public void setGasCostLevelUp(int gasCostLevelUp) {
        this.gasCostLevelUp = gasCostLevelUp;
    }

    public void getDamage(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) {
            this.hp = 0;
        }
    }
    //取得所有update的是否被點選的
    public boolean getAllUpdateIconsCan() {
        for (int i = 0; i < icons.size(); i++) {
            if (icons.get(i) instanceof WorkingIcon) {
                continue;
            }
            if (icons.get(i).getPressed()) {
                return true;
            }
        }
        return false;
    }

    public void shutAllUpdateIconCan(){
        for (int i = 0; i < icons.size(); i++) {
            if (icons.get(i) instanceof WorkingIcon) {
                continue;
            }
            icons.get(i).setPressed(false);
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setWoodCostLevelUp(int woodCostLevelUp) {
        this.woodCostLevelUp = woodCostLevelUp;
    }

    public void setSteelCostLevelUp(int steelCostLevelUp) {
        this.steelCostLevelUp = steelCostLevelUp;
    }

    /**
     * 建築的效果說明
     *
     * @return 建築的效果說明
     * @level 幾等時的作用
     */
    public abstract String buildingDetail(int level);

    public String showBuildCost() {
        return "建造所需木材:" + woodCostCreate + " 鋼鐵:" + steelCostCreate + " 瓦斯:" + gasCostCreate;
    }

    public String showUpgradeCost() {
        return "升級所需木材:" + woodCostLevelUp + " 鋼鐵:" + steelCostLevelUp + " 瓦斯:" + gasCostLevelUp;
    }



    @Override
    public void paintComponent(Graphics g) {
        if (level==0) { //畫出建造中的建築物 !isWorking() && !readyToUpgrade && !isUpgrading
            g.drawImage(underConstructionImg, painter().left(), painter().top(), painter().width(), painter().height(), null);

        } else if (readyToUpgrade && !isUpgrading) {  //畫出完成的建築物
            g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);
            //畫出Icon
            if (isShowIcon) {
                //畫出等級
                g.drawString("目前等級" + level, painter().left(), painter().top());
                for (int i = 0; i < icons.size(); i++) {
                    icons.get(i).paint(g);
                }
            }
            //畫出升級中
        } else if (isUpgrading && !readyToUpgrade) {
            g.drawImage(underConstructionImg, painter().left(), painter().top(), painter().width(), painter().height(), null);
            g.drawString("升級中", painter().left(), painter().top());
        }
    }


    @Override
    public void update() {
        if (isShowIcon) {
            for (int i = 0; i < icons.size(); i++) {
                icons.get(i).update();
            }
        }
    }


    //子類初始化
    protected abstract void init();

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        switch (state) {
            case PRESSED:
                if (readyToUpgrade && !isUpgrading && isEntered(e.getX(), e.getY())) {
                    isShowIcon = true;
                } else {
                    isShowIcon = false;
                }
        }
        if (isShowIcon) {
            for (int i = 0; i < icons.size(); i++) {
                icons.get(i).mouseTrig(e, state, trigTime);
            }
        }
    }

    public String getImgPath() {
        return imgPath;
    }
}
