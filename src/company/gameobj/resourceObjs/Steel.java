package company.gameobj.resourceObjs;

import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gametest9th.utils.Path;

import java.awt.*;

public class Steel extends GameObject {

    private Image image;

    private int x;
    private int y;

    private int eachTimeGet;
    private int totalNum;


    public Steel(int x, int y) {

        super(x, y, 128,128);

        this.x = x;
        this.y = y;

        image = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().steel());

        eachTimeGet = 1;

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