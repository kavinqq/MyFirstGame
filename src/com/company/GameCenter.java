package com.company;

import com.company.controllers.SceneController;
import com.company.gametest9th.utils.CommandSolver;
import com.company.gametest9th.utils.GameKernel;
import com.company.scene.MainScene;
import com.company.scene.SelectScene;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GameCenter implements GameKernel.GameInterface, CommandSolver.MouseCommandListener, CommandSolver.KeyListener {

    public GameCenter() {
        SceneController.getInstance().change(new SelectScene());
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
        CommandSolver.MouseCommandListener listener = SceneController.getInstance().mouseListener();
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
