package company.gameobj.message;


import company.Global;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.GameKernel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class HintDialog implements GameKernel.GameInterface, CommandSolver.MouseCommandListener {


    //提示
    private String message;
    //x座標
    private int mouseX;
    //y座標
    private int mouseY;
    //toast的字串
    private String toastString;
    //計算外面傳入toast的次數



    public HintDialog() {
        message = "";
    }

    //外面傳入提示
    public void setHintMessage(String hintMessage) {
        message = hintMessage;
    }


    @Override
    public void paint(Graphics g) {
        //畫滑鼠移到的字
        g.setColor(Color.white);
        g.setFont(new Font("Dialog", Font.BOLD, Global.FONT_SIZE));
        if (message == null) {
            message = "";
        }
        g.drawString(message, mouseX, mouseY-30);
        //畫toast;
        g.setColor(Color.black);
    }

    @Override
    public void update() {

    }


    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
