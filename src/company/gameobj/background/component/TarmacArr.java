package company.gameobj.background.component;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.BuildingController;
import company.gameobj.GameObject;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.util.ArrayList;

import static company.Global.*;
import static company.gameobj.BuildingController.BuildingType.values;

public class TarmacArr {
    private ArrayList<ArrayList<Tarmac>> tarmacs;


    public TarmacArr() {
        tarmacs = new ArrayList<ArrayList<Tarmac>>();

        //停機坪
        for (int i = 0; i < Global.BUILDING_AREA_NUMY; i++) {
            tarmacs.add(new ArrayList<>());
            for (int j = 0; j < Global.BUILDING_AREA_NUMX; j++) {
                if ((i == 0 || i == Global.BUILDING_AREA_NUMY - 1) && (j == 0 || j == Global.BUILDING_AREA_NUMX - 1)) {
                    tarmacs.get(i).add(new Tarmac(+ Global.BUILDING_AREA_X + Global.BUILDING_AREA_DISTANCE_X * j,  + Global.BUILDING_AREA_Y + Global.BUILDING_AREA_DISTANCE_Y * i));
                }
            }
        }
    }

    private class Tarmac extends GameObject {

        private Image tarmac_img;

        public Tarmac(int x, int y) {
            super(x, y, BUILDING_GRID_WIDTH, BUILDING_GRID_HEIGHT);
            setBuildingOriginalX(Global.SUM_OF_CAMERA_MOVE_VX);
            setBuildingOriginalY(Global.SUM_OF_CAMERA_MOVE_VY);

//            tarmac_img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().magic());
        }

        @Override
        public void paintComponent(Graphics g) {
//            g.drawImage(tarmac_img, painter().left(), painter().top(), painter().width(), painter().height(), null);
        }

        @Override
        public void update() {

        }


    }

    /**
     * 跟隨鏡頭移動所有建築物位置
     */

    public void cameraMove() {
        for (int i = 0; i < tarmacs.size(); i++) {
            for (Tarmac tarmac : tarmacs.get(i)) {
                //移動停機坪
                tarmac.cameraMove();
            }
        }
    }

    /**
     * reset所有建築位置回初始位置
     */

    public void resetObjectXY() {
        for (int i = 0; i < tarmacs.size(); i++) {
            for (Tarmac tarmac : tarmacs.get(i)) {
                //返回停機坪
                tarmac.resetObjectXY();
            }
        }
    }

    public void paint(Graphics g) {
        for (int i = 0; i < tarmacs.size(); i++) {
            for (Tarmac tarmac : tarmacs.get(i)) {
                //返回停機坪
                tarmac.paint(g);
            }
        }
    }

    //取得停機坪位置
    public Tarmac get(int x, int y) {
        return tarmacs.get(x).get(y);
    }

    public Tarmac get(int index) {
        if(index < 0){
            return null;
        }
        int count=0;
        for (int i = 0; i < tarmacs.size(); i++) {
            for (int j = 0; j < tarmacs.get(i).size(); j++) {
                if(count==index){
                    return tarmacs.get(i).get(j);
                }
                count++;
            }
        }
        return null;
    }

    public int getX(int index){
        return get(index).painter().left();
    }

    public int getY(int index){
        return get(index).painter().top();
    }
}
