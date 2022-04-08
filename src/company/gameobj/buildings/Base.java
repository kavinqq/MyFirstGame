package company.gameobj.buildings;

import company.Global;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;

import static company.Global.*;

public class Base extends Building {
    public static final int BASE_WIDTH = 250;
    public static final int BASE_HEIGHT = 250;
    public final static int BASE_X = LAND_X + (LAND_WIDTH) / 2 - Base.BASE_WIDTH / 2;
    public final static int BASE_Y = SCREEN_HEIGHT / 2 - Base.BASE_HEIGHT / 2;


    public Base(int x, int y) {
        super(x, y, BASE_WIDTH, BASE_HEIGHT);

        setBuildingOriginalX(Global.SUM_OF_CAMERA_MOVE_VX);
        setBuildingOriginalY(Global.SUM_OF_CAMERA_MOVE_VY);
        init();
    }

    public Base() {
        super(BASE_X, BASE_Y, BASE_WIDTH, BASE_HEIGHT);
        setBuildingOriginalX(Global.SUM_OF_CAMERA_MOVE_VX);
        setBuildingOriginalY(Global.SUM_OF_CAMERA_MOVE_VY);
        init();
    }

    @Override
    public void update() {

    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(getImg(),painter().left(),painter().top(),painter().width(),painter().height(),null);

        //血量條
        g.setColor(Color.red);
        g.fillRect(painter().left(), painter().bottom(), painter().width(), Global.HP_HEIGHT);
        g.setColor(Color.green);
        g.fillRect(painter().left(), painter().bottom(), (int) (getCurrentHp() * painter().width())/getHp(), Global.HP_HEIGHT);
    }

    //初始化
    @Override
    protected void init() {
        setId(9)
                .setName("主堡")
                .setBuildTime(0)
                .setUpgradeTime(0)
                .setLevelC(1)  //初始等級為1
                .setTechLevelNeedUpgrade(2)
                .setHp(5000)
                .setWoodCostCreate(0)
                .setSteelCostCreate(0)
                .setGasCostCreate(0)
                .setWoodCostLevelUpC(0)
                .setSteelCostLevelUpC(0)
                .setGasCostLevelup(0)
                .setImgPath(new Path().img().building().Base());

        imgInit();
        setCurrentHp(getHp());
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        super.mouseTrig(e,state,trigTime);
        switch (state) {
            case DRAGGED: {
//                setPainterStartFromTopLeft(e.getX(), e.getY());
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "主堡";
    }

    public String buildingDetail(int level) {
        return "";
    }

    /**
     * 升級後屬性更改(ex再升級需要的資源增加)
     *
     * @param level 目前等級
     */
    public void levelUpTechResource(int level) {
        switch (level) {
            case 0: {
                super.setLevel(0);
            }
            case 1: {//一級時 需要升級的資源改變
                super.setWoodCostLevelUp(60);
                super.setSteelCostLevelUp(30);
                super.setGasCostLevelUp(10);
                super.setLevel(1);
                break;
            }
        }
    }


}
