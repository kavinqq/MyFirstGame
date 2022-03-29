package company.gameobj;

import company.controllers.SceneController;
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

        totalNum = 9;
    }

    public int eachTimeGet() {
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
        g.drawImage(image, x, y, null);
    }

    @Override
    public void update() {

    }
}