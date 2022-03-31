package company.gameobj.background.component;

import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gametest9th.utils.Path;

import java.awt.*;

public class BuildingGrid extends GameObject {
    private Image img;

    private boolean isOnBuildGrid;
    private boolean isPreOnBuildGrid;

    public BuildingGrid(int x, int y) {
        super(x, y,96*5/4,96*5/4);
        img=SceneController.getInstance().imageController().tryGetImage(new Path().img().background().grass());
    }

    public void setOnBuildGrid(boolean bool){
        isPreOnBuildGrid =bool;
    }

    public boolean isOnBuildGrid(){
        return isPreOnBuildGrid;
    }



    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(img,painter().left(),painter().top(),painter().width(),painter().height(),null);
    }

    @Override
    public void update() {

    }
}
