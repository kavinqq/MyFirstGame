package company.gametest9th.utils;

import company.controllers.AudioResourceController;
import company.controllers.SceneController;
import company.gameobj.GameObject;

import java.awt.*;

public abstract class Effect extends GameObject {
    private Delay totalDelay;
    private Image attackEffectImg;
    private boolean show;

    public Effect(int x,int y, int width, int height,Delay totalDelay, Delay delay,String img){
        super(x,y,width,height);
        this.totalDelay = totalDelay;
        totalDelay.play();

        if(Math.random() < 0.3){
            attackEffectImg = SceneController.getInstance().imageController().tryGetImage(new Path().img().effects().attackEffect());
        } else if(Math.random() < 0.6){
            attackEffectImg = SceneController.getInstance().imageController().tryGetImage(new Path().img().effects().attackEffect2());
        }  else {
            attackEffectImg = SceneController.getInstance().imageController().tryGetImage(new Path().img().effects().attackEffect3());
        }

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
       g.drawImage(attackEffectImg,this.painter().left(), this.painter().top(),null);
        //paintEffect(g, this.painter().centerX(), this.painter().centerY());
    }

    public abstract void paintEffect(Graphics g, int x, int y);

    @Override
    public void update() {

    }

    public boolean isDue(){
        return this.totalDelay.count();
    }

    public Image getImg() {
        return attackEffectImg;
    }
}
