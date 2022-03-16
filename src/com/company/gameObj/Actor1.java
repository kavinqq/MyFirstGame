package com.company.gameObj;

import com.company.Global.Direction;
import com.company.controllers.SceneController;
import com.company.gametest9th.utils.Delay;
import com.company.gametest9th.utils.Path;

import java.awt.*;

public class Actor1 extends GameObject{

    private Image img;

    private int[] walk;
    private int walkIndex;
    private int changeDir;
    Direction direction;

    Delay delay;


    public Actor1(int x, int y) {
        super(x, y, 64, 64);

        // new Path().img().actors().Actor1()
        img = SceneController.getInstance().imageController().tryGetImage("resources/img/actors/Actor1.png");

        walk = new int[]{1,0,1,2};
        walkIndex = 0;
        changeDir = 0;
        direction = Direction.UP;

        delay = new Delay(20);
        delay.loop();
    }

    @Override
    public void paintComponent(Graphics g) {
        // 我竟然跟影片裡面做一模一樣的蠢事 超好笑XDD => 直接用left top right bottom就好了嗎 幹 我還畫圖出來才想通
        g.drawImage(img, painter().left(), painter().top(), painter().right(), painter().bottom(),
                walk[walkIndex] * 32,Direction.values()[changeDir].getValue() * 32 - 32,
                walk[walkIndex] * 32 + 32,Direction.values()[changeDir].getValue() * 32, null);

    }

    @Override
    public void update() {
        delay.play();

        //[isLoop() On] 如果 count >= countLimit => count = 0 => return true;
        //[isLoop() Off] 如果 count >= countLimit => stop()[isPause = true; count = 0;]; => return false;
        if(delay.count()){
            walkIndex += 1;
            if(walkIndex >= walk.length){
                walkIndex = 0;
            }

            changeDir += 1;
            if(changeDir >= Direction.values().length){
                changeDir = 0;
            }
        }
    }
}
