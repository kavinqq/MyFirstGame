package com.company.scene;

import com.company.Global;
import com.company.controllers.SceneController;
import com.company.gameObj.batmanGame.Batman;
import com.company.gameObj.batmanGame.BatmanDart;
import com.company.gameObj.batmanGame.Joker;
import com.company.gametest9th.utils.CommandSolver;

import java.awt.*;
import java.util.ArrayList;

public class BatmanScene extends Scene implements CommandSolver.KeyListener {

    private Batman batman;
    private int batManSpeed;

    private Joker joker;

    private int jokerDestination;
    private int jokerSwitchCount;
    private int jokerSpeed;

    private ArrayList<BatmanDart> dartSet;

    private long startTime;
    private long costTime;

    @Override
    public void sceneBegin() {
        batman = new Batman(Global.SCREEN_X / 2 - 50, Global.SCREEN_Y - 50);
        batManSpeed = 16;

        //小丑的設定
        joker = new Joker(Global.SCREEN_X / 2 - 50, 5);
        jokerDestination = joker.collider().left();
        jokerSwitchCount = 0;
        jokerSpeed = 4;

        //飛鏢設定
        dartSet = new ArrayList<>();
        startTime = System.nanoTime();
    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {
        batman.paint(g);
        joker.paint(g);

        for (int i = 0; i < dartSet.size(); i++) {
            dartSet.get(i).paint(g);
        }

        g.setColor(Color.RED);
        g.drawString("花費時間：" + costTime + "秒", 0, 20);
    }

    @Override
    public void update() {
        joker.update();

        //小丑的移動
        //如果目的地在目標的左邊 朝左移動
        if (joker.collider().left() < jokerDestination) {
            joker.translateX(jokerSpeed);
        } else {
            //如果目的地在目標的右邊 朝右移動
            joker.translateX(jokerSpeed * -1);
        }

        //如果時間到 則換目的地
        if (jokerSwitchCount == Global.FRAME_LIMIT) {
            jokerDestination = Global.random(0, Global.SCREEN_X - joker.collider().width());
            jokerSwitchCount = 0;
        }
        jokerSwitchCount++;


        //飛鏢的移動以及邏輯
        for (int i = 0; i < dartSet.size(); i++) {
            BatmanDart dart = dartSet.get(i);
            dart.update();

            //超出畫面則移除
            if (dart.touchTop()) {
                dartSet.remove(i);
            }

            //若是擊中joker則移除
            if (dart.isCollision(joker)) {
                dartSet.remove(i);
                if (joker.getStatus() != Joker.Status.UNBEATABLE) {
                    joker.hit();
                }
            }
        }
        if (joker.isDead()) {
            GameOverScene gameOver = new GameOverScene();
            gameOver.setOtherString("總花費秒數：" + costTime);
            SceneController.getInstance().change(gameOver);
        }

        //計算時間 每10秒幫joker加狀態
        long currentTime = System.nanoTime();
        costTime = ((currentTime - startTime) / 1000000000L);
        joker.addPassCount((int) costTime);

    }

    private void hit() {

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
        if (commandCode == Global.LEFT) {
            batman.translateX(batManSpeed * -1);
        }
        if (commandCode == Global.RIGHT) {
            batman.translateX(batManSpeed);
        }
        if (commandCode == Global.SPACE) {
            dartSet.add(new BatmanDart(batman.collider().left(), batman.collider().top() - batman.collider().height()));
        }
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {

    }

    @Override
    public void keyTyped(char c, long trigTime) {
    }
}
