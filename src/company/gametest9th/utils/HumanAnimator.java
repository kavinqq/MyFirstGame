package company.gametest9th.utils;

import company.Global;
import company.controllers.SceneController;

import java.awt.*;

public class HumanAnimator extends Animator{

    // 哪一張人物行走圖
    private Image img;
    // 哪一個人物
    private int type;
    // 站立 或 走路的 陣列 index => 用來換 左腳/右腳/站立 的圖片
    private int walkCount;
    // 延遲
    private Delay delay;
    // 方向
    private Global.Direction dir;
    // 人物狀態(站立/行走)
    private State state;

    public HumanAnimator(int type, State state) {

        // 初始化是哪個角色
        this.type = type;

        // 初始化該角色狀態
        this.state = state;

        // 初始化圖片
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().actors().Actor2());

        // 初始化 行走/站立 狀態的陣列index
        walkCount = 0;

        // 初始化 delay
        delay = new Delay(state.getSpeed());
        // 開啟 循環模式
        delay.loop();

        // 初始化方向
        dir = Global.Direction.DOWN;
    }

    @Override
    public void paint(Global.Direction dir, int left, int top, int right, int bottom, Graphics g) {

        g.drawImage(img, left, top, right, bottom,
                (type % 4) * 96 + state.getArr()[walkCount] * 32,
                (type / 4) * 128 + dir.getValue() * 32,
                (type % 4) * 96 + state.getArr()[walkCount] * 32 + 32,
                (type / 4) * 128 + dir.getValue() * 32 + 32,
                null);
    }


    @Override
    public void update(){
        delay.play();

        if(delay.count()){
            walkCount += 1;
            if(walkCount >= state.getArr().length){
                walkCount = 0;
            }
        }
    }

}
