package company.gameobj.background.component;

import company.gametest9th.utils.GameKernel;
import company.gametest9th.utils.Vector;

import java.awt.*;
import java.util.ArrayList;

public class BuildingArea implements GameKernel.GameInterface {
    private ArrayList<ArrayList<BuildingGrid>> buildingGrids;
    private boolean isPreAllNonOnBuildGrid;

    private final int numY=4;
    private final int numX=7;


    public BuildingArea(){
        buildingGrids=new ArrayList<>();
//        buildingGrids=new ArrayList();

        for (int i = 0; i < numY; i++) {
            buildingGrids.add(new ArrayList<>());
            for (int j = 0; j < numX; j++) {
                if(i==0 || i== numY-1 || j==0 || j==numX-1){
                    buildingGrids.get(i).add(new BuildingGrid(96+96*5*j/4*2,96+96*5*i/4*2));
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
     * @param vector 向量
     */

    public void buildingAreaCameraMove(Vector vector){
        for(ArrayList<BuildingGrid> buildingGridArr : buildingGrids){

            for(BuildingGrid buildingGrid: buildingGridArr) {

                buildingGrid.cameraMove(vector);
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
