package com.company.scene;

import com.company.Global;
import com.company.controllers.SceneController;
import com.company.gametest9th.utils.CommandSolver;

import java.awt.*;

public class SelectScene extends Scene implements CommandSolver.KeyListener {

    @Override
    public void sceneBegin() {

    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("按下a開啟飛機遊戲，按下b開啟蝙蝠俠遊戲", Global.SCREEN_X / 2, Global.SCREEN_Y / 2);
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
        return this;
    }

    @Override
    public void keyPressed(int commandCode, long trigTime) {
        //按鍵按下的時候
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {
        //按鍵按下後放開的時候
    }

    @Override
    public void keyTyped(char c, long trigTime) {
        System.out.println(c);

        //打某個字的時候
        if (c == 'a' || c == 'A') {
            SceneController.getInstance().change(new MainScene());
        }
        if (c == 'b' || c == 'B') {
            SceneController.getInstance().change(new BatmanScene());
        }
    }
}
