package company.gameobj.background;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gameobj.background.component.BuildingGrid;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.GameKernel;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static company.Global.*;

public class Background extends GameObject implements GameKernel.GameInterface, CommandSolver.MouseCommandListener {

    private Image bg_img;



    public Background(int x, int y, int width, int height) {

        super(x, y, width, height);

        bg_img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().background());

        shutDetectRange();
    }

    @Override
    public void paintComponent(Graphics g) {
        //背景
        g.drawImage(bg_img, painter().left(), painter().top(), SCREEN_WIDTH, SCREEN_HEIGHT, null);

    }


    @Override
    public void update() {

    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

    }

}