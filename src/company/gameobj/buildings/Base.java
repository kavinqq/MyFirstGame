package company.gameobj.buildings;

import company.Global;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.event.MouseEvent;

import static company.Global.*;

public class Base extends Building {
    public static final int BASE_WIDTH = 250;
    public static final int BASE_HEIGHT = 250;

    public Base(int x, int y)
    {
        super(x,y,BASE_WIDTH, BASE_HEIGHT);

        setBuildingOriginalX(Global.SUM_OF_CAMERA_MOVE_VX);
        setBuildingOriginalY(Global.SUM_OF_CAMERA_MOVE_VY);
        init();
    }

    public Base()
    {
        super(LAND_X+(LAND_WIDTH) / 2 - BASE_WIDTH / 2,SCREEN_Y / 2 - BASE_HEIGHT / 2,BASE_WIDTH, BASE_HEIGHT);
        setBuildingOriginalX(Global.SUM_OF_CAMERA_MOVE_VX);
        setBuildingOriginalY(Global.SUM_OF_CAMERA_MOVE_VY);
        init();
    }

    @Override
    public void update() {
        cameraMove();
    }

    //初始化
    @Override
    protected void init() {
        setId(9)
                .setName("主堡")
                .setBuildTime(1)
                .setUpgradeTime(30)
                .setLevelC(1)  //初始等級為1
                .setTechLevelNeedUpgrade(2)
                .setHp(100)
                .setWoodCostCreate(99)
                .setSteelCostCreate(99)
                .setGasCostCreate(99)
                .setWoodCostLevelUpC(99)
                .setSteelCostLevelUpC(99)
                .setGasCostLevelup(99)
                .setImgPath(new Path().img().building().Base());

        imgInit();
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        switch (state){
            case DRAGGED: {
                setPainterStartFromTopLeft(e.getX(),e.getY());
                break;
            }
        }
    }
    @Override
    public String toString() {
        return "主堡:我被催毀遊戲結束";
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
                super.setWoodCostLevelUp(60);
                super.setSteelCostLevelUp(30);
                super.setGasCostLevelUp(10);
                super.setLevel(1);
                break;
            }
        }
    }


}
