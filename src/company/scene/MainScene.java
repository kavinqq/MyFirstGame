package company.scene;

import company.Global;
import company.gametest9th.utils.CommandSolver;

import java.awt.*;

/**
 * 遊戲主場景
 */
public class MainScene extends Scene {

    private Image img;

    @Override
    public void sceneBegin() {

    }

    @Override
    public void sceneEnd() {
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(Global.SCREEN_X / 2, Global.SCREEN_Y / 2, 300,300);
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
