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

    public void print(String string){
        toasts.add(new Toast(string));
    }

    @Override
    public void paint(Graphics g){
        for (int i = 0; i < toasts.size(); i++) {
            toasts.get(i).paint(g);
            if( toasts.get(i).isClear()){
                toasts.remove(i--); //一定是先進先出
            }
        }
    }

    @Override
    public void update() {
    }
}
