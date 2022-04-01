package company;

import company.controllers.AudioResourceController;
import company.gametest9th.utils.GameKernel;
import company.gametest9th.utils.Path;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("城市管理");
        frame.setSize(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int[][] commands = {
                {KeyEvent.VK_LEFT, Global.LEFT},
                {KeyEvent.VK_RIGHT, Global.RIGHT},
                {KeyEvent.VK_UP, Global.UP},
                {KeyEvent.VK_DOWN, Global.DOWN},
                {KeyEvent.VK_SPACE, Global.SPACE},
                {KeyEvent.VK_ESCAPE, Global.ESC}
        };

        GameCenter center = new GameCenter();
        GameKernel kernel = new GameKernel.Builder(center, Global.LIMIT_DELTA_TIME, Global.NANOSECOUND_PER_UPDATE)
                .initListener(commands)
                .enableMouseTrack(center)
                .trackChar()
                .enableKeyboardTrack(center)
                .keyCleanMode()
                .gen();


        frame.add(kernel);
        frame.setVisible(true);
        kernel.run(Global.IS_DEBUG);

    }
}
