package company.gameobj.message;

import company.Global;
import company.controllers.SceneController;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MultiIHintDialog {
    ArrayList<HintDialog> hintDialogs;
    HintDialog.State status;
    private Image messageBg;
    private int x;
    private int y;
    private int gapX=5;
    public MultiIHintDialog(HintDialog.State state, int x, int y) {
        status = state;
        this.x = x;
        this.y = y;
        hintDialogs = new ArrayList<>();
        messageBg = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().hintUI());
    }

    //新增文字
    public void add(String message) {
        touchBottom();
        hintDialogs.add(new HintDialog(status, x + gapX, y + Global.FONT_SIZE * hintDialogs.size()+(Global.FONT_SIZE), message)); // 左邊間隔:5 文字會往左上角長與圖片不一樣:文字需要在往下一個size
    }

    //底部不要超出螢幕範圍
    private void touchBottom() {
        if (y + Global.FONT_SIZE * hintDialogs.size() > Global.SCREEN_HEIGHT) {
            for (int i = 0; i < hintDialogs.size(); i++) {
                hintDialogs.get(i).offset(0,Global.SCREEN_HEIGHT-(Global.FONT_SIZE * hintDialogs.size()));
            }
        }
    }

    public void paint(Graphics g) {
        g.drawImage(messageBg, x, y, 300, Global.FONT_SIZE * (hintDialogs.size()+1), null);
        for (int i = 0; i < hintDialogs.size(); i++) {
            hintDialogs.get(i).paint(g);
        }
    }

    public void update() {
        for (int i = 0; i < hintDialogs.size(); i++) {
            hintDialogs.get(i).update();
        }
    }

    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        for (int i = 0; i < hintDialogs.size(); i++) {
            hintDialogs.get(i).mouseTrig(e, state, trigTime);
        }
    }
}
