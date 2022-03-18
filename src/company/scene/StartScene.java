package company.scene;

import company.Global;
import company.controllers.SceneController;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;


/**
 * 一開始的選單
 */

public class StartScene extends Scene{

    private Image img;

    @Override
    public void sceneBegin() {
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().background());
    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 0,0,Global.SCREEN_X, Global.SCREEN_Y,
                0,0,img.getWidth(null), img.getHeight(null), null);



        g.setFont(new Font("楷體", Font.PLAIN, 20));
        g.drawString("遊戲開始", Global.SCREEN_X / 2 - 50, Global.SCREEN_Y / 2);
    }

    @Override
    public void update() {

    }

    @Override
    public CommandSolver.MouseCommandListener mouseListener() {
        return null;
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return null;
    }
}
