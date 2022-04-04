package company.gameobj.message;


import company.Global;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.GameKernel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class HintDialog implements GameKernel.GameInterface, CommandSolver.MouseCommandListener {
    enum State{
        ABSOLUTE, //絕對座標
        RELATIVE; //相對座標
    }

    //提示
    private String message;
    //x座標
    private int mouseX;
    //y座標
    private int mouseY;
    //位移座標
    private int x;
    private int y;
    //目前狀態
    private State status;

    public HintDialog() {
        status =State.RELATIVE;
        y=-30;
        message = "";
    }

    //外面傳入提示
    public void setHintMessage(String hintMessage) {
        message = hintMessage;
    }

    //設置要位移的位置
    public void setHintAbsolutePosition(int x,int y) {
        status=State.ABSOLUTE;
        this.x=x;
        this.y=y;
    }

    public void setHintRelativePosition(int x,int y){
        status=State.RELATIVE;
        this.x=x;
        this.y=y;
    }

    @Override
    public void paint(Graphics g) {
        //畫滑鼠移到的字
        g.setColor(Color.white);
        g.setFont(new Font("Dialog", Font.BOLD, Global.FONT_SIZE));
        if (message == null) {
            message = "";
        }
        if(status==State.ABSOLUTE){
            g.drawString(message, x, y);
        } else if(status==State.RELATIVE){
            g.drawString(message, mouseX+x, mouseY+y);
        }
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
