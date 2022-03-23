package company.gametest9th.utils;


import company.Global;
import company.controllers.SceneController;

import java.awt.*;


public class HumanAnimator extends Animator {

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


    /**
     * 改變狀態
     *
     * @param state 現在的狀態
     */
    @Override
    public void setState(State state) {

        // 把新的狀態 assign 給自己
        this.state = state;

        // reset delay limit
        delay.setLimit(state.getSpeed());
    }

    @Override
    public void paint(Global.Direction dir, int left, int top, int right, int bottom, Graphics g) {

        // 有時候, update的 if (walkCount >= state.getArr().length)會沒抓到 , 切換狀態會outOfBounds, 再次check
        if (walkCount >= state.getArr().length) {
            walkCount = 0;
        }

        // 根據角色的 type(哪一個角色) 和 state Arr(要印哪一張圖) 去判斷 這次 source X Y抓來源圖片
        g.drawImage(img, left, top, right, bottom,
                (type % 4) * 96 + state.getArr()[walkCount] * 32,
                (type / 4) * 128 + dir.getValue() * 32,
                (type % 4) * 96 + state.getArr()[walkCount] * 32 + 32,
                (type / 4) * 128 + dir.getValue() * 32 + 32,
                null);
    }


    @Override
    public void update() {
        // 如果 到了 該狀態的speed(換圖片速度)
        if (delay.count()) {

            // 要印的圖片index + 1
            walkCount += 1;

            // 如果 walkCount 比 該狀態陣列的length長
            if (walkCount >= state.getArr().length) {

                // 回到第一張圖
                walkCount = 0;
            }
        }
    }
}
