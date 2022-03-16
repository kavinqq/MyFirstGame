package com.company.gameObj.batmanGame;

import com.company.Global;
import com.company.controllers.SceneController;
import com.company.gameObj.GameObject;
import com.company.gameObj.Rect;
import com.company.gametest9th.utils.Delay;
import com.company.gametest9th.utils.Path;

import java.awt.*;

public class Joker extends GameObject {

    public enum Status {
        UNBEATABLE,
        NORMAL
    }

    public Joker(int x, int y) {
        super(x, y, 50, 50);
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().actors().joker());
        bloodCount = 2;
        secondCount = 0;
        status = Status.NORMAL;
    }

    /**
     * 要畫的圖片
     */
    private Image img;

    /**
     * 血量
     */
    private int bloodCount;

    /**
     * 過去的秒數
     */
    private int secondCount;

    private Status status;
    private int changeTime;

    /**
     * 每次過去計數
     */
    public void addPassCount(int sec) {
        secondCount = sec;
    }

    /**
     * 被扣血
     */
    public void hit() {
        bloodCount--;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * 是否死亡
     *
     * @return 是否死亡
     */
    public boolean isDead() {
        return bloodCount <= 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);
        if (status == Status.UNBEATABLE) {
            //處於無敵狀態
            g.setColor(Color.YELLOW);
            g.drawRect(collider().left()-5, collider().top()-5, collider().width() + 10, collider().height() + 10);
        }
    }

    @Override
    public void update() {
        //如果在無敵狀態並且 時間差大餘2秒 則變回普通狀態
        if (status == Status.UNBEATABLE && (secondCount - changeTime) <= 2) {
            status = Status.NORMAL;
        }

        //每10秒變身一次
        if (secondCount % 10 == 0 && secondCount >= 3) {
            status = Status.UNBEATABLE;
            changeTime = secondCount;
        }
    }
}
