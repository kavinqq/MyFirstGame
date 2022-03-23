package company.gameobj.buildings;

import company.controllers.SceneController;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Base extends Building {
    private Image img;

    public Base(int x, int y, int width, int height) {

        super(x, y, width, height);
        //System.out.println(painter().left());
        this.img=SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Base());
    }


    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);

    }

    @Override
    public void update() {

    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        switch (state){
            case DRAGGED: {
                offset(e.getX(),e.getY());
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
