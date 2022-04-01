package company.gameobj.message;

import company.gametest9th.utils.GameKernel;

import java.awt.*;
import java.util.ArrayList;

public class ToastController implements GameKernel.GameInterface {
    private static ToastController toastController;

    public static ToastController instance() {
        if (toastController == null) {
            toastController = new ToastController();
        }
        return toastController;
    }

    private ArrayList<Toast> toasts;

    private ToastController() {
        toasts=new ArrayList<>();
    }

    //印出
    public void print(String string){
        //印出前把上面的全部畫掉
        for (int i = 0; i < toasts.size(); i++) {
            if(toasts.get(i)!=null){
                toasts.remove(i--);
            }
        }
        toasts.add(new Toast(string));
    }

    @Override
    public void paint(Graphics g){
        for (int i = 0; i < toasts.size(); i++) {
            toasts.get(i).paint(g);
            if( toasts.get(i).isClearSelf()){
                toasts.remove(i--); //一定是先進先出
            }
        }
    }

    @Override
    public void update() {

    }
}
