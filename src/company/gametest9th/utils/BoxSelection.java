package company.gametest9th.utils;

import company.controllers.SceneController;
import company.gameobj.GameObject;

import java.awt.*;
import java.awt.event.MouseEvent;

import static company.Global.*;
import static company.Global.WINDOW_HEIGHT;

public class BoxSelection implements CommandSolver.MouseCommandListener {

    private class Box extends GameObject {

        private Image img;  // Box圖片
        private int startX; // 起點X
        private int startY; // 起點Y
        private int endX;   // 終點X
        private int endY;   // 終點Y

        public Box() {

            super(0, 0, 0, 0);// 預設畫不出來 都是0

            img = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().box());// 載入圖片
        }

        /**
         * 設定起點
         * @param startX 起點X
         * @param startY 起點Y
         */

        public void setStart(int startX, int startY) {
            this.startX = startX;
            this.startY = startY;
        }

        /**
         * 這定終點
         * @param endX 終點X
         * @param endY 終點Y
         */

        public void setEnd(int endX, int endY) {

            endX = Math.max(endX, 0);
            endX = Math.min(endX, WINDOW_WIDTH - BUILDING_OPTION_WIDTH);

            endY = Math.max(endY, STATUS_BAR_HEIGHT);
            endY = Math.min(endY, WINDOW_HEIGHT);

            this.endX = endX;
            this.endY = endY;
        }

        @Override
        public void paintComponent(Graphics g) {

            // 畫出框選的框框
            g.drawImage(img, startX, startY, endX, endY, 0, 0, img.getWidth(null), img.getHeight(null), null);
        }

        @Override
        public void update() {

            //因為碰撞體都是從左上往右下畫,所以 起點X,Y 一定都是比較小的那個
            int topLeftX = Math.min(startX, endX);
            int topLeftY = Math.min(startY, endY);

            //設定左上角那個點
            setPainterStartFromTopLeft(topLeftX, topLeftY);

            //painter 的長寬
            painter().scale(Math.abs(endX - startX), Math.abs(endY - startY));
        }
    }

    // 框選
    private boolean hasSetStart;

    // 框框實體
    private Box box;

    // 建構子
    public BoxSelection() {

        // 預設沒設定起點
        hasSetStart = false;

        // new Box實體
        box = new Box();
    }

    // 畫出Box
    public void paint(Graphics g) {
        box.paint(g);
    }

    // 更新Box
    public void update() {box.update();}

    // 取得Box
    public Box getBox(){
        return box;
    }


    // 滑鼠監聽
    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

        /*
            如果上一層 pressed開啟了 框選模式
            那麼他在下一幀 會把 滑鼠監聽丟進來 => 所以我不需要處理pressed
        */
        switch (state) {

            // 拖曳期間
            case DRAGGED: {

                // 如果還沒設定過起點
                if(!hasSetStart){
                    // 設定起點
                    box.setStart(e.getX(), e.getY());
                    hasSetStart = true;
                }


                // 設定終點
                box.setEnd(e.getX(), e.getY());

                break;
            }

            // 拖曳完後 放開滑鼠瞬間
            case RELEASED: {

                // 把起點 和 終點 設為0
                box.setStart(0,0);
                box.setEnd(0,0);

                // reset 是否設定過起點
                hasSetStart = false;
                break;
            }

            default: {
                // 把起點 和 終點 設為0
                box.setStart(0,0);
                box.setEnd(0,0);

                // reset 是否設定過起點
                hasSetStart = false;
            }
        }

    }
}
