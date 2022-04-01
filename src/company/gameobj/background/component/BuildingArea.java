package company.gameobj.background.component;

import company.Global;
import company.gametest9th.utils.GameKernel;
import company.gametest9th.utils.Vector;

import java.awt.*;
import java.util.ArrayList;

public class BuildingArea implements GameKernel.GameInterface {
    private ArrayList<ArrayList<BuildingGrid>> buildingGrids;
    private boolean isPreAllNonOnBuildGrid;

    public BuildingArea(){
        buildingGrids=new ArrayList<>();
//        buildingGrids=new ArrayList();

        for (int i = 0; i < Global.numY; i++) {
            buildingGrids.add(new ArrayList<>());
            for (int j = 0; j < Global.numX; j++) {
                //做方形
                if(i==0 || i== Global.numY-1 || j==0 || j==Global.numX-1){
                    //挖掉四角
                    if(!((i==0 || i==Global.numY-1) && (j==0 || j==Global.numX-1))){
                        buildingGrids.get(i).add(new BuildingGrid(Global.BUILDING_AREA_X+Global.BUILDING_AREA_DISTANCE_X*j,Global.BUILDING_AREA_Y+Global.BUILDING_AREA_DISTANCE_Y*i));
                    }
                }
            }
        }
    }

    public boolean isAllNonOnBuildGrid(){
        for (int i = 0; i <  buildingGrids.size(); i++) {
            for (int j = 0; j < buildingGrids.get(i).size(); j++) {
                if (buildingGrids.get(i).get(j).isOnBuildGrid()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < buildingGrids.size(); i++) {
            for (int j = 0; j <  buildingGrids.get(i).size(); j++) {
                buildingGrids.get(i).get(j).paint(g);
            }
        }
    }


    @Override
    public void update() {
    }
    //取得值
    public BuildingGrid get(int i,int j){
        return buildingGrids.get(i).get(j);
    }
//    //取得一維陣列
//    public ArrayList<BuildingGrid> get(int i){
//        return buildingGrids.get(i);
//    }
    public int lengthX(int i){
        return buildingGrids.get(i).size();
    }

    public int lengthY(){
        return buildingGrids.size();
    }

    /**
     * 所有的基座 給我朝著 這段向量前進
     */

    public void buildingAreaCameraMove(){
        for(ArrayList<BuildingGrid> buildingGridArr : buildingGrids){

            for(BuildingGrid buildingGrid: buildingGridArr) {

                buildingGrid.cameraMove();
            }
        }
    }

    /**
     * 所有的基座 給我回歸原味
     *
    */

    public void buildingAreaResetPosition(){
        for(ArrayList<BuildingGrid> buildingGridArr : buildingGrids){

            for(BuildingGrid buildingGrid: buildingGridArr) {

                buildingGrid.resetObjectXY();
            }
        }
    }

}
