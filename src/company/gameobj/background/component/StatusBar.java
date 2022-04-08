package company.gameobj.background.component;

import company.Global;
import company.controllers.SceneController;
import company.gametest9th.utils.Path;
import company.gametest9th.utils.Vector;

import java.awt.*;

import static company.Global.*;
import static company.Global.ICON_HEIGHT;

/**
 * StatusBar 用 單例模式 => 因為整個遊戲只會有一個狀態欄
 */
public class StatusBar {

    private static StatusBar instance; // StatusBar唯一的實例

    private Image resourceBarUI; // 上方的資源欄位UI

    private Image citizenNumIcon;// 村民數量Icon
    private Image soldierNumIcon;// 士兵數量Icon

    private Image steelIcon;// 鋼鐵數量Icon
    private Image treeIcon;//  木頭數量Icon
    private Image gasIcon; //   瓦斯數量Icon
    private Image timeIcon; //  時間Icon

    private long nowTime; // 現在的時間
    private String outputTimeStr; // 要輸出的時間字串

    private int totalWood; // 當前木頭總量
    private int totalSteel;// 當前鋼鐵總量
    private int totalGas;  // 當前瓦斯總量
    private int numOfCitizen;// 當前村民總量
    private int numOfSoldier;// 當前士兵數量

    private int moveX;
    private int moveY;

    /**
     * StatusBar的建構子 (因為是單例模式 設成private => 不讓外面new => 只能透過 instance()呼叫 )
     */
    private StatusBar() {
        // UI 圖片
        resourceBarUI = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().resourceBarUI());

        // Icon圖片
        citizenNumIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().citizenNumIcon());
        soldierNumIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().soldierNumIcon());
        treeIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().treeIcon());
        steelIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().steelIcon());
        gasIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().gasIcon());
        timeIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().timeIcon());

        // 目前的總資源量
        totalWood = 0;
        totalSteel = 0;
        totalGas = 0;
        numOfCitizen = 0;
        numOfSoldier = 0;

        // 鏡頭移動時的物件移動量
        moveY = 0;
    }
    //取得現在時間
    public long getTime(){
        return nowTime;
    }


    /**
     * 取得Status的實例
     *
     * @return Status實例
     */

    public static StatusBar instance() {

        // 如果我還沒有一個Status的實例
        if (instance == null) {

            // 建一個
            instance = new StatusBar();
        }

        // 回傳實例
        return instance;
    }

    /**
     * StatusBar的畫畫
     *
     * @param g Graphic
     */

    public void paint(Graphics g) {
        //資源欄 UI
        // (70,0,500,60) => 我只取這個UI來源的 中間黑色部分
        g.drawImage(resourceBarUI, 0, 0 + moveY, WINDOW_WIDTH, STATUS_BAR_HEIGHT + moveY, 70, 0, 500, 60, null);

        // 每一組Icon + 搭配的數字 [還沒搭配到的 我先不寫]

        // 設定數字顏色 && 樣式
        g.setColor(Color.white);
        g.setFont(new Font("TimeRoman", Font.PLAIN, 60));

        // 樹木資源(Icon + 數量)
        g.drawImage(treeIcon, ICON_START_X, ICON_START_Y + moveY, ICON_WIDTH, ICON_HEIGHT, null);
        g.drawString("" + totalWood, ICON_START_X + ICON_WIDTH / 2 + 100, ICON_START_Y + 50 + moveY);

        // 鋼鐵資源(Icon + 數量)
        g.drawImage(steelIcon, ICON_START_X + ICON_GAP * 1, ICON_START_Y + moveY, ICON_WIDTH, ICON_HEIGHT, null);
        g.drawString("" + totalSteel, ICON_START_X + ICON_WIDTH / 2 + ICON_GAP + 100, ICON_START_Y + 50 + moveY);

        // 瓦斯資源(Icon + 數量)
        g.drawImage(gasIcon, ICON_START_X + ICON_GAP *2, ICON_START_Y + moveY, ICON_WIDTH, ICON_HEIGHT, null);
        g.drawString("" + totalGas, ICON_START_X * 2 + ICON_WIDTH / 2 + ICON_GAP*2 + 100, ICON_START_Y + 50);

        // 市民數量(Icon + 數量)
        g.drawImage(citizenNumIcon, ICON_START_X + ICON_GAP * 3, ICON_START_Y + moveY, ICON_WIDTH, ICON_HEIGHT, null);
        g.drawString("" + numOfCitizen, ICON_START_X + ICON_WIDTH / 2 + ICON_GAP * 3 + 100, ICON_START_Y + 50 + moveY);

        // 士兵數量(Icon + 數量)
        g.drawImage(soldierNumIcon, ICON_START_X + ICON_GAP * 4, ICON_START_Y + moveY, ICON_WIDTH, ICON_HEIGHT, null);
        g.drawString("" + numOfSoldier , ICON_START_X + ICON_WIDTH / 2 + ICON_GAP * 4 + 100, ICON_START_Y + 50 + moveY);

        //遊戲時間
        g.drawImage(timeIcon, ICON_START_X + ICON_GAP * 5, ICON_START_Y + moveY, ICON_WIDTH, ICON_HEIGHT, null);
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString(outputTimeStr, ICON_START_X + ICON_GAP * 5 + 100, ICON_HEIGHT + moveY);

    }


    /**
     * 處理現在要顯示的時間字串
     *
     * @param startTime 遊戲開始時間
     */

    public void setTimeString(long startTime) {

        // 把 毫秒 換算回 秒 (1秒 = 10的9次方 * 1毫秒)
        nowTime = Math.round((System.nanoTime() - startTime) / 1000000000);

        // 如果 < 60 => 只顯示秒   (應該不可能有人玩一小時吧)
        if (nowTime < 60) {
            outputTimeStr = nowTime + " 秒";
        } else { // 否則 顯示分&&秒
            outputTimeStr = nowTime / 60 + " 分 " + nowTime % 60 + " 秒";
        }
    }

//    public void cameraMove(Vector vector) {
//
//        int tmpMoveY = (int)(vector.vy() * CAMERA_SPEED);
//
//        if(moveY != 0) {
//            moveY += tmpMoveY;
//        } else {
//            moveY = tmpMoveY;
//        }
//        System.out.println("StatusBar moveY: " + moveY);
//    }

    public void updateResource(int totalWood, int totalSteel, int totalGas, int numOfCitizen, int numOfSoldier) {
        this.totalWood = totalWood;
        this.totalSteel = totalSteel;
        this.totalGas = totalGas;
        this.numOfCitizen = numOfCitizen;
        this.numOfSoldier = numOfSoldier;
    }
}
