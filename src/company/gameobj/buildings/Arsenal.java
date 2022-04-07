package company.gameobj.buildings;


import company.Global;
import company.gameobj.BuildingController;
import company.gametest9th.utils.Path;
import oldMain.City;

import java.awt.*;

public class Arsenal extends Building {
    //所有軍人升級共用此進度條
    private static float soldierLevelUpPercent;
    //所有飛機升級共用此進度條
    private static float planeLevelUpPercent;
    //軍人是否正在升級
    private static boolean soldierLevelUpgrading;
    //飛機是否正在升級
    private static boolean planeLevelUpgrading;
    /**
     * 父類建構子
     * id 建築物ID  (1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠 7.瓦斯場 8.飛機工場)
     * name 建築物名稱
     * buildTime 建築物持續時間
     * upgradeTime 建築物升級時間
     * level 建築物等級 預設-1  建好 0 升級過 1~2,147,483,647
     * techLevelNeed 需要文明等級
     * readyToUpgrade 建築物是否在建築，建築中 -> true
     * woodCostCreate 創建所需要的木頭量
     * steelCostCreate 創建所需要的鋼鐵量
     * woodCostLevelUp 升級所需要的木頭量
     * steelCostLevelUp 升級所需要的鋼鐵量
     *
     * PS: 兵工廠的等級 → 決定士兵的攻擊力
     * 兵工廠升級  → 呼叫 Human類別 的 levelUp()方法
     * levelUp() → City類別的 第 347行 使用
     */
    public Arsenal(int x, int y) {
        super(x, y);

        init();
        getIcons().add(new UpGradeIcon(getIcons().size(),"升級士兵"));
        getIcons().add(new UpGradeIcon(getIcons().size(),"升級機器士兵"));
        }

    public Arsenal() {
        init();
    }

    @Override
    public String toString() {
        return "強化士兵";
    }

    public String buildingDetail(int level){
        return "";
    }


    public static void setSoldierLevelUpgrading(boolean b) {
        soldierLevelUpgrading=b;
    }

    public static void setPlaneLevelUpgrading(boolean b) {
        planeLevelUpgrading=b;
    }


    //傳入目前擁有兵工廠數量 ，蓋越多越快
    public static void addSoldierPercent(int num){
        soldierLevelUpPercent += num*1.0f / BuildingController.SOLDIER_LEVEL_UPGRADE_TIME;
        if(soldierLevelUpPercent>1){
            soldierLevelUpPercent=1;
        }
    }
    // 進度條歸0
    public static void resetSoldierPercent(){
        soldierLevelUpPercent=0;
    }
    //取得軍人進度條百分比
    public static int getSoldierPercent(){
        System.out.println(soldierLevelUpPercent);
        return (int)soldierLevelUpPercent;

    }

    //傳入目前擁有兵工廠數量 ，蓋越快
    public static void addPlanePercent(int num){
        planeLevelUpPercent += num*1.0f / BuildingController.PLANE_LEVEL_UPGRADE_TIME;
        if(planeLevelUpPercent>1){
            planeLevelUpPercent=1;
        }
    }
    // 進度條歸0
    public static void resetPlanePercent(){
        planeLevelUpPercent=0;
    }
    //取得飛機進度條百分比
    public static int getPlanePercent(){
        return (int)planeLevelUpPercent;
    }

    //初始化
    @Override
    protected void init() {
        setId(6)
                .setName("兵工廠")
                .setBuildTime(3)
                .setUpgradeTime(48)
                .setLevelC(0)
                .setTechLevelNeedBuild(2)
                .setTechLevelNeedUpgrade(2)
                .setHp(800)
                .setWoodCostCreate(30)
                .setSteelCostCreate(10)
                .setGasCostCreate(0)
                .setWoodCostLevelUpC(70)
                .setSteelCostLevelUpC(40)
                .setGasCostLevelup(0)
                .setImgPath(new Path().img().building().Arsenal());
        imgInit();
    }

    @Override
    public void paintComponent(Graphics g) {
        //升級中及完成顯示白色
        g.setColor(Color.white);


        if (getLevel() == 0) { //畫出建造中的建築物 !isWorking() && !readyToUpgrade && !isUpgrading
            g.drawImage(getUnderConstructionImg(), painter().left(), painter().top(), painter().width(), painter().height(), null);

        } else if (!soldierLevelUpgrading && !planeLevelUpgrading) {  //畫出完成的建築物
            g.drawImage(getImg(), painter().left(), painter().top(), painter().width(), painter().height(), null);
            //畫出Icon
            if (isShowIcon()) {
                g.setFont(new Font("Dialog", Font.BOLD, Global.FONT_SIZE));
                //畫出等級
                g.drawString("目前等級" + City.getTechLevel(), painter().width() / 2 + painter().left() - Global.FONT_SIZE * 2, painter().top() - showStringHeight());
                for (int i = 0; i < getIcons().size(); i++) {
                    getIcons().get(i).paint(g);
                }

            }
            //畫出升級中
        } else if ((soldierLevelUpgrading) || (planeLevelUpgrading)) {
            g.drawImage(getUnderConstructionImg(), painter().left(), painter().top(), painter().width(), painter().height(), null);
            g.drawString("升級等級中", painter().width() / 2 + painter().left() - Global.FONT_SIZE * 2, painter().top() - showStringHeight());
            //升級進度條 有兩個
            int num=1;
            g.setColor(Color.yellow);
            if(soldierLevelUpPercent>0){
                g.fillRect(painter().left(), painter().bottom()+Global.HP_HEIGHT*num+2, (int) (soldierLevelUpPercent * painter().width()), Global.HP_HEIGHT);
                num++;
            }
            g.fillRect(painter().left(), painter().bottom()+Global.HP_HEIGHT*num+2, (int) (planeLevelUpPercent * painter().width()), Global.HP_HEIGHT);

        }
        //血量條
        g.setColor(Color.red);
        g.fillRect(painter().left(), painter().bottom(), painter().width(), Global.HP_HEIGHT);
        g.setColor(Color.green);
        g.fillRect(painter().left(), painter().bottom(), (int) (getCurrentHp() * painter().width())/getHp(), Global.HP_HEIGHT);
        //復原
        g.setColor(Color.black);

        if(this.getFightEffect()!=null){
            this.getFightEffect().paintComponent(g);
        }
    }
}
