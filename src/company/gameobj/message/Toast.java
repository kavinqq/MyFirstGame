package company.gameobj.message;

import company.Global;
import company.gametest9th.utils.Delay;
import company.gametest9th.utils.GameKernel;

import java.awt.*;

class Toast implements GameKernel.GameInterface {//每一個toast為獨立 因此不為靜態
    //再螢幕中跳出提示 會往上移動且時間到自動消失
    private String toastMessage;

    //移動座標 toast的字位置
    private int toastX;
    private int toastY;

    //出現幾秒
    private Delay toastDelay;
    //延緩秒數
    private int delaySecond;
    //是否可以清除自己
    private boolean isClearSelf;
    //控制文字上升速度沒有double
    private int countSpeed;
    private int count;
    //字體大小
    private int fontSize;
    public Toast(String string){
        toastMessage = string;

        //控制文字上升速度 值越大速度越慢
        delaySecond= Global.FRAME_LIMIT*2;
        toastDelay = new Delay(delaySecond);
        countSpeed=3;// 速度越大越慢
        toastDelay.loop();

        isClearSelf =false;
        fontSize=25;
        toastX = Global.WINDOW_WIDTH/2-string.length()*fontSize/2;
        toastY = Global.WINDOW_HEIGHT*3/4;

    }

    public boolean isClearSelf(){
        return isClearSelf;
    }

    private void toastMove() {
        toastY=toastY-1;
    }

    @Override
    public void paint(Graphics g){

        g.setColor(Color.darkGray);
        g.setFont(new Font("Dialog", Font.BOLD, fontSize));
        //當偵數跑完後刪除
        if (toastDelay.count()) {
            isClearSelf =true;
        } else {
            if(count%countSpeed==0){
                count-=countSpeed;
                toastMove();
            }
            count++;
            g.drawString(toastMessage, toastX, toastY);
        }
        g.setColor(Color.black);
    }

    @Override
    public void update() {

    }
}
