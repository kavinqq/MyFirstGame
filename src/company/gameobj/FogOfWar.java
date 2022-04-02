package company.gameobj;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static company.Global.*;

public class FogOfWar {

    /**
     * 迷霧本體別人也用不到 設為內部類別
     */
    private class Fog extends GameObject {

        // 迷霧建構子(碰撞範圍很大)
        public Fog(int x, int y) {
            super(x, y, 250, 250);
        }


        // 畫出迷霧
        @Override
        public void paintComponent(Graphics g) {

            g.setColor(Color.BLACK);
            g.fillRect(painter().left(), painter().top(), 150, 150);
        }

        @Override
        public void update() {

        }
    }

    private List<Fog> fogs;

    public FogOfWar() {

        // 初始化LinkedList(因為要常常刪除節點)
        fogs = new LinkedList<>();

        // 加入迷霧
        for (int fogY = MAP_TOP; fogY < MAP_BOTTOM; fogY += 150) {
            for (int fogX = MAP_LEFT; fogX < MAP_RIGHT; fogX += 150) {
                if (!(fogX >= 0 && fogX <= SCREEN_X && fogY >= 0 && fogY <= SCREEN_Y)) {
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
