package company.gameobj.background.component;

import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gametest9th.utils.Path;

import java.awt.*;

public class BuildingGrid extends GameObject {
    private Image img;
    public BuildingGrid(int x, int y) {
        super(x, y,192+96,192+96);
        img=SceneController.getInstance().imageController().tryGetImage(new Path().img().background().foundation());
    }

    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(img,painter().left(),painter().top(),painter().width(),painter().height(),null);
    }

    @Override
    public void update() {

    }
}
