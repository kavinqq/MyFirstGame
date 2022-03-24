package company.gametest9th.utils;

import company.gameobj.Box;

import java.awt.event.MouseEvent;

public class BoxSelection implements CommandSolver.MouseCommandListener {

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

    // 取得這個Box
    public Box getBox() {
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
