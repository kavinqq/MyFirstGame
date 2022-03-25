package company.gameobj.background;


import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.GameKernel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class HintDialog implements GameKernel.GameInterface, CommandSolver.MouseCommandListener {
    //提示
    private String message;
    //x座標
    private int x;
    //y座標
    private int y;

    private static HintDialog hintDoalog;

    public static HintDialog instance(){
        if(hintDoalog==null){
            hintDoalog=new HintDialog();
        }
        return hintDoalog;
    }

    private HintDialog(){
        message="";
    }

    //外面傳入提示
    public void setHintMessage(String hintMessage) {
        message = hintMessage;
    }

    //外面傳入滑鼠座標
    public void setCuurentXY(int cuurentX,int currentY){
        this.x=cuurentX;
        this.y=currentY;
    }


    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);
        Font font = new Font("Dialog", Font.BOLD, 25);
        g.setFont(font);
        if(message==null){
            message="";
        }
        g.drawString(message,x,y);
        g.setColor(Color.black);
    }

    @Override
    public void update() {

    }


    //好像不用這個
    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

    }
}
