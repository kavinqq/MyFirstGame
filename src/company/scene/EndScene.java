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

public class EndScene extends Scene implements CommandSolver.KeyListener {

    private final int timeStrMove = 300;

    private Image img;

    private long startTime;

    private long nowTime;

    private String outputTimeStr;

    public EndScene(long startTime, boolean isWin){

        this.startTime = startTime;

        if(isWin){
            img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().victory());
        }else{
            img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().lose());
        }
    }


    @Override
    public void sceneBegin() {

        // 把 毫秒 換算回 秒 (1秒 = 10的9次方 * 1毫秒)
        nowTime = Math.round((System.nanoTime() - startTime) / 1000000000);

        // 如果 < 60 => 只顯示秒   (應該不可能有人玩一小時吧)
        if (nowTime < 60) {
            outputTimeStr = nowTime + " 秒";
        } else { // 否則 顯示分&&秒
            outputTimeStr = nowTime / 60 + " 分 " + nowTime % 60 + " 秒";
        }
    }

    @Override
    public void sceneEnd() {
        img = null;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 0,0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT, null);

        g.setColor(Color.black);
        g.setFont(new Font("TimeRoman", Font.BOLD, 50));
        g.drawString("總遊玩時間: " + outputTimeStr, Global.SCREEN_WIDTH / 2 - timeStrMove, Global.SCREEN_HEIGHT / 2);
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

    @Override
    public void keyPressed(int commandCode, long trigTime) {
        SceneController.getInstance().change(new StartScene());
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {

    }

    @Override
    public void keyTyped(char c, long trigTime) {

    }
}
