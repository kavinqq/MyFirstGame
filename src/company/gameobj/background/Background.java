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

    private Image tarmac_img;

    public Background(int x, int y, int width, int height) {

        super(x, y, width, height);

        bg_img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().background());

        tarmac_img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().tarmac());

        shutDetectRange();
    }

    @Override
    public void paintComponent(Graphics g) {
        //背景
        g.drawImage(bg_img, painter().left(), painter().top(), SCREEN_WIDTH, SCREEN_HEIGHT, null);
        //停機坪
        for (int i = 0; i < Global.numY; i++) {
            for (int j = 0; j < Global.numX; j++) {
                if ((i == 0 || i == Global.numY - 1) && (j == 0 || j == Global.numX - 1)) {
                    g.drawImage(tarmac_img, (BUILDING_WIDTH) / 2 + Global.BUILDING_AREA_X + Global.BUILDING_AREA_DISTANCE_X * j, (BUILDING_HEIGHT) / 2 + Global.BUILDING_AREA_Y + Global.BUILDING_AREA_DISTANCE_Y * i, BUILDING_WIDTH, BUILDING_HEIGHT, null);
                }
            }
        }
    }


    @Override
    public void update() {

    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

    }

}