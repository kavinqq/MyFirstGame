package company.gameobj.buildings;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Building extends GameObject implements CommandSolver.MouseCommandListener {

    private Image img;
    private int woodRequired;
    private int steelRequired;
    private int gasRequired;

    public Building(){
        super(-1000, -1000, 1,1);
        img=SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Base());
    }
    //TODO: delete
    public Building(int x, int y){
        super(x,y, 1,1);
        img=SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Base());
    }

    //for non-base buildings
    public Building(int x, int y, String imgPath) {
        super(x, y, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);
        img=SceneController.getInstance().imageController().tryGetImage(imgPath);
    }

    //for base building only
    public Building(int x, int y, int width, int height, String imgPath) {
        super(Global.SCREEN_X/2, Global.SCREEN_Y/2, Global.BASE_WIDTH, Global.BASE_HEIGHT);
        img=SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Base());
    }

    public int getWoodRequired() {
        return woodRequired;
    }

    public int getSteelRequired() {
        return steelRequired;
    }

    public int getGasRequired() {
        return gasRequired;
    }

    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(img, painter().left() , painter().top(),  painter().width(), painter().height(), null);
    }

    @Override
    public void update() {

    }

    private boolean canCatchBuilding;
    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

    }
}
