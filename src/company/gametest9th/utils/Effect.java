package company.gametest9th.utils;

import company.controllers.AudioResourceController;
import company.controllers.SceneController;
import company.gameobj.GameObject;

import java.awt.*;

public abstract class Effect extends GameObject {
    private Delay totalDelay;
    private Image img;
    private boolean show;

    public Effect(int x,int y, int width, int height,Delay totalDelay, Delay delay,String img){
        super(x,y,width,height);
        this.totalDelay = totalDelay;
        totalDelay.play();
        this.img = SceneController.getInstance().imageController().tryGetImage(img);


        // 隨機音效
        if(Math.random() < 0.3) {
            AudioResourceController.getInstance().play(new Path().sound().fight());
        } else if(Math.random() < 0.6){
            AudioResourceController.getInstance().play(new Path().sound().fight1());
        } else {
            AudioResourceController.getInstance().play(new Path().sound().fight2());
        }
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    public abstract void paintEffect(Graphics g, int x, int y);

    @Override
    public void update() {

    }

    public boolean isDue(){
        return this.totalDelay.count();
    }

    public Image getImg() {
        return img;
    }
}
