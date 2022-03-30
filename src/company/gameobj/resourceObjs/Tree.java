package company.gameobj.resourceObjs;

import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gametest9th.utils.Path;

import java.awt.*;

public class Tree extends GameObject {

    private Image image;

    private int eachTimeGet; // 每次拿的量
    private int totalNum; // 這個資源總共有多少量

    public Tree(int x, int y) {
        super(x, y, 128,128);

        image = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().tree());

        eachTimeGet = 3;

        totalNum = 600;
    }

    /**
     * 每次拿多少 && 扣多少
     * @return 每次拿的量
     */

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
        g.drawImage(image, painter().left(), painter().top(), null);
    }

    @Override
    public void update() {

    }
}
