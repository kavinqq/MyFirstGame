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
    private boolean isClear;
    //沒有double 減少數度
    private int countSpeed;
    private int count;

    public Toast(String string){
        toastMessage = string;
        delaySecond= Global.FRAME_LIMIT*2;
        toastDelay = new Delay(delaySecond);
        isClear=false;
        countSpeed=3;// 速度越大越慢
        toastX = Global.WINDOW_WIDTH/2;
        toastY = Global.WINDOW_HEIGHT / 2 + 200;
        toastDelay.loop();
    }

    public boolean isClear(){
        return isClear;
    }

    private void toastMove() {
        toastY=toastY-1;
    }

    @Override
    public void paint(Graphics g){

        g.setColor(Color.darkGray);
        g.setFont(new Font("Dialog", Font.BOLD, 25));;
        if (toastDelay.count()) {
            isClear=true;
            toastY = Global.WINDOW_HEIGHT / 2 + 200;
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
