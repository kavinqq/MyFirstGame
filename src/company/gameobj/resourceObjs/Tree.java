package company.gameobj.resourceObjs;

import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gametest9th.utils.Path;

import java.awt.*;

public class Tree extends GameObject {

    private Image image;

    // X,Y座標
    private int x;
    private int y;

    private int eachTimeGet; // 每次拿的量
    private int totalNum; // 這個資源總共有多少量

    public Tree(int x, int y) {
        super(x, y, 128,128);

        this.x = x;
        this.y = y;

        image = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().tree());

        eachTimeGet = 3;

        totalNum = 9;
    }


    public int eachTimeGet() {
        totalNum -= eachTimeGet;

        return eachTimeGet;
    }

    public void setEachTimeGet(int eachTimeGet){
        this.eachTimeGet = eachTimeGet;
    }

    public int getTotalNum(){
        return  totalNum;
    }


    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(image, x, y,x + 128, y + 128,0,0,image.getWidth(null), image.getHeight(null), null);
    }

    @Override
    public void update() {

    }
}
