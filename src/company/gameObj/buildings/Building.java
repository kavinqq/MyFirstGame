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

    public Building(int x, int y) {
        super(x, y, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);
        img=SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Base());
    }

    public Building(int x, int y,int width,int height) {
        super(x, y, width, height);
        img=SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Base());
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
