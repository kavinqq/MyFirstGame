package company.gameobj;

import company.controllers.SceneController;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static company.Global.*;

public class FogOfWar {

    /**
     * 迷霧本體別人也用不到 設為內部類別
     */
    private class Fog extends GameObject {

        // 迷霧的圖片
        private Image img;

        // 迷霧建構子(碰撞範圍很大)
        public Fog(int x, int y) {

            super(x, y, 250, 250);

            img = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().fog());

        }


        // 畫出迷霧
        @Override
        public void paintComponent(Graphics g) {

            g.drawImage(img, painter().left(), painter().top(),150,150,null);
        }

        @Override
        public void update() {

        }
    }

    private List<Fog> fogs;


    public FogOfWar() {

        // 初始化LinkedList(因為要常常刪除節點所以用LinkedList)
        fogs = new LinkedList<>();

        // 加入迷霧
        for (int fogY = MAP_TOP; fogY < MAP_BOTTOM; fogY += 150) {
            for (int fogX = MAP_LEFT; fogX < MAP_RIGHT; fogX += 150) {
                if (!(fogX >= 0 && fogX <= SCREEN_WIDTH && fogY >= 0 && fogY <= SCREEN_HEIGHT)) {
                    fogs.add(new Fog(fogX, fogY));
                }
            }
        }
    }

    /**
     * 畫出迷霧
     *
     * @param g Graphics
     */

    public void paint(Graphics g) {
        for (Fog fog : fogs) {
            fog.paint(g);
        }
    }

    /**
     * 迷霧主要的更新
     *
     * @param object 傳入一個遊戲物件
     */
    public void update(GameObject object) {

        // 我把人類的MistDetectRange 和 迷霧 做 碰撞
        for (int i = 0; i < fogs.size(); i++) {
            if (fogs.get(i).isCollision(object)) {
                fogs.remove(i);
                i--;
            }
        }
    }

    /**
     * 所有迷霧的鏡頭移動
     */
    public void cameraMove() {

        for (Fog fog : fogs) {
            fog.cameraMove();
        }
    }


    /**
     * 所有迷霧回到鏡頭位置
     */
    public void resetObjectXY() {

        for (Fog fog : fogs) {
            fog.resetObjectXY();
        }
    }
}
