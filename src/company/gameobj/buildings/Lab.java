package company.gameobj.buildings;
import company.Global;
import company.gameobj.BuildingController;
import company.gametest9th.utils.Path;
import oldMain.City;

import java.awt.*;

public class Lab extends Building {

    public static boolean isTechLevelUpgrading;

    private static float techUpgradePercent;

    /**
     * 父類建構子
     * id 建築物ID  (1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠 7.瓦斯場 8.飛機工廠)
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
     */
    public Lab(int x, int y) {
        super(x, y);
        init();

    }

    //設定現在是否在升級科技
    public static void setTechLevelUpgrading(boolean b){
        isTechLevelUpgrading =b;
    }

    public Lab() {

        init();
    }
    //初始化
    protected void init(){
        setId(2)
                .setName("研究所")
                .setBuildTime(5)
                .setUpgradeTime(3)
                .setLevelC(0)
                .setTechLevelNeedBuild(1)
                .setTechLevelNeedUpgrade(1)
                .setHp(500)
                .setWoodCostCreate(10)
                .setSteelCostCreate(5)
                .setGasCostCreate(0)
                .setWoodCostLevelUpC(40)
                .setSteelCostLevelUpC(25)
                .setGasCostLevelup(0)
                .setImgPath(new Path().img().building().Lab());
        imgInit();
        getIcons().add(new UpGradeIcon(getIcons().size(),"升級科技等級"));
    }
    @Override
    public String toString() {
        return "升級科技等級";
    }

    public String buildingDetail(int level){
        return "";
    }

    /**
     * 升級後屬性更改(ex再升級需要的資源增加)
     * @param level 目前等級
     */
    public void levelUpTechResource(int level){
        switch (level){
            case 0:{
                super.setLevel(0);
            }
            case 1:{//一級時 需要升級的資源改變
//                Button處文字需要更新
//                setWoodCostLevelUp(60);
//                setSteelCostLevelUp(30);
//                setGasCostLevelUp(10);
                setLevel(1);
                break;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        //升級中及完成顯示白色
        g.setColor(Color.white);
        g.setFont(new Font("Dialog",Font.BOLD,Global.FONT_SIZE));

        if (getLevel() == 0) { //畫出建造中的建築物 !isWorking() && !readyToUpgrade && !isUpgrading
            g.drawImage(getUnderConstructionImg(), painter().left(), painter().top(), painter().width(), painter().height(), null);

        } else if (!isTechLevelUpgrading) {  //畫出完成的建築物
            g.drawImage(getImg(), painter().left(), painter().top(), painter().width(), painter().height(), null);
            //畫出Icon
            if (isShowIcon()) {
                //畫出等級
                g.drawString("目前科技等級" + City.getTechLevel(), painter().width() / 2 + painter().left() - Global.FONT_SIZE * 2, painter().top() - showStringHeight());
                for (int i = 0; i < getIcons().size(); i++) {

                    getIcons().get(i).paint(g);
                }

            }
        //畫出升級中
        } else if (isTechLevelUpgrading) {
            g.drawImage(getUnderConstructionImg(), painter().left(), painter().top(), painter().width(), painter().height(), null);
            g.drawString("升級科技等級", painter().width() / 2 + painter().left() - Global.FONT_SIZE * 2, painter().top() - showStringHeight());
            //升級進度條
            g.setColor(Color.yellow);
            g.fillRect(painter().left(), painter().bottom()+Global.HP_HEIGHT+2, (int) (techUpgradePercent * painter().width()), Global.HP_HEIGHT);
        }
        //血量條
        g.setColor(Color.red);
        g.fillRect(painter().left(), painter().bottom(), painter().width(), Global.HP_HEIGHT);
        g.setColor(Color.green);
        g.fillRect(painter().left(), painter().bottom(), (int)(getCurrentHp() * painter().width())/getHp(), Global.HP_HEIGHT);
        //復原
        g.setColor(Color.black);

        if(this.getFightEffect()!=null){
            this.getFightEffect().paintComponent(g);
        }
    }


    //所有研究所共有此升級條
    public static void addPercentTechUpgrade(int num){
        techUpgradePercent += num*1.0f / BuildingController.TECH_LEVEL_UPGRADE_TIME;
        if(techUpgradePercent>1){
            techUpgradePercent=1;
        }
    }

    // 研究進度條歸0
    public static void resetTechUpgradePercent(){
        techUpgradePercent=0;
    }

    //取得科技進度百分比
    public static int getTechUpgradePercent(){
        return (int)techUpgradePercent;
    }
}
