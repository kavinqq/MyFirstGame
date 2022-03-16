package company.scene;

import company.Global;
import company.gametest9th.utils.CommandSolver;

import java.awt.*;

public class StartScene extends Scene{

    @Override
    public void sceneBegin() {

    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("楷體", Font.PLAIN, 20));
        g.drawString("遊戲開始", Global.SCREEN_X / 2, Global.SCREEN_Y / 2);
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
