package company.scene;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.MenuChoice;
import company.gameobj.background.component.StatusBar;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EventListener;

public class EndScene extends Scene{
    private Image bgImg;

    private boolean isWin;

    public void setWin(boolean isWin){
        this.isWin=isWin;
    }

    @Override
    public void sceneBegin() {
        bgImg = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().background());
    }

    @Override
    public void sceneEnd() {
        bgImg=null;
    }

    @Override
    public void paint(Graphics g) {
        int fontSize=25;
        g.setFont(new Font("Dialog",1,fontSize));
        g.drawImage(bgImg,0,0, Global.SCREEN_WIDTH,Global.SCREEN_HEIGHT,null);
        //遊玩時間
        String str="遊玩時間:"+StatusBar.instance().getTime();
        g.drawString(str,Global.SCREEN_WIDTH/2-100,Global.SCREEN_HEIGHT/2-50);
        //若贏了
        if(isWin){
            String resultStr="YOU WIN";
            g.drawString(resultStr,Global.SCREEN_WIDTH/2-100,Global.SCREEN_HEIGHT/2);
            //若輸了
        }else{
            String resultStr="YOU LOSE";
            g.drawString(resultStr,Global.SCREEN_WIDTH/2-100,Global.SCREEN_HEIGHT/2);
        }
        //回主畫面
        str ="點擊任意鍵回到主畫面";
        g.drawString(str,Global.SCREEN_WIDTH/2-180,Global.SCREEN_HEIGHT/2+300);
    }

    @Override
    public void update() {

    }

    @Override
    public CommandSolver.MouseCommandListener mouseListener() {
        return new CommandSolver.MouseCommandListener() {
            @Override
            public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

                // 滑鼠點擊
                if (state == CommandSolver.MouseState.PRESSED) {
                    Scene startScene= new StartScene();
                    SceneController.getInstance().change(startScene);
                }
            }

        };

    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return null;
    }
}
