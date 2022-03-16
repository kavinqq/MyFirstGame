package company;

import company.gametest9th.utils.GameKernel;

import javax.swing.*;
import java.awt.event.*;

public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("飛機射擊遊戲");
        frame.setSize(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //NONE(4),
        //        UP(3),
        //        DOWN(0),
        //        LEFT(1),
        //        RIGHT(2);
        int[][] commands = {
                {KeyEvent.VK_LEFT, Global.LEFT},
                {KeyEvent.VK_RIGHT, Global.RIGHT},
                {KeyEvent.VK_UP, Global.UP},
                {KeyEvent.VK_DOWN, Global.DOWN},
                {KeyEvent.VK_SPACE, Global.SPACE}
        };

        GameCenter center = new GameCenter();
        GameKernel kernel = new GameKernel.Builder(center, Global.LIMIT_DELTA_TIME, Global.NANOSECOUND_PER_UPDATE)
                .initListener(commands)
                .enableMouseTrack(center)
                .trackChar()
                .enableKeyboardTrack(center)
                .keyTypedMode()
                .gen();


        frame.add(kernel);
        frame.setVisible(true);
        kernel.run(Global.IS_DEBUG);
    }
}
