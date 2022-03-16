package com.company.gameObj;

import com.company.Global;

import java.awt.*;

public class Buff extends GameObject{
    private int x;
    private int y;

    public Buff(int x, int y) {
        super(x, y, 30, 30);
        this.x = x;
        this.y = y;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.green);
        g.drawOval(painter().left(),painter().top(),30,30);
    }

    @Override
    public void update() {
        move();
    }


    /**
     * 一時也想不到 怎麼讓drawOval 變成 Image 乾脆讓他一起動
     */
    public void move(){
        translateY(5);
    }

}
