package company;

import company.controllers.SceneController;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.GameKernel;
import company.scene.MainScene;
import company.scene.StartScene;


import java.awt.*;
import java.awt.event.MouseEvent;

public class GameCenter implements GameKernel.GameInterface, CommandSolver.MouseCommandListener, CommandSolver.KeyListener {

    public GameCenter() {
        SceneController.getInstance().change(new StartScene());
    }

    @Override
    public void paint(Graphics g) {
        SceneController.getInstance().paint(g);
    }

    @Override
    public void update() {
        SceneController.getInstance().update();
    }


    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        // 滑鼠監聽
        CommandSolver.MouseCommandListener listener = SceneController.getInstance().mouseListener();

        // 如果監聽有實例
        if (listener != null) {
            listener.mouseTrig(e, state, trigTime);
        }
    }

    @Override
    public void keyPressed(int commandCode, long trigTime) {
        CommandSolver.KeyListener listener = SceneController.getInstance().keyListener();
        if (listener != null) {
            listener.keyPressed(commandCode, trigTime);
        }
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {
        CommandSolver.KeyListener listener = SceneController.getInstance().keyListener();
        if (listener != null) {
            listener.keyReleased(commandCode, trigTime);
        }
    }

    @Override
    public void keyTyped(char c, long trigTime) {
        CommandSolver.KeyListener listener = SceneController.getInstance().keyListener();
        if (listener != null) {
            listener.keyTyped(c, trigTime);
        }
    }
}
