package company.gameobj.background.component;

import company.gametest9th.utils.GameKernel;

import java.awt.*;
import java.util.ArrayList;

public class BuildingArea implements GameKernel.GameInterface {
    private ArrayList<ArrayList<BuildingGrid>> buildingGrids;

    private final int numY=2;
    private final int numX=4;

    public BuildingArea(){
        buildingGrids=new ArrayList<>();
//        buildingGrids=new ArrayList();

        for (int i = 0; i < numY; i++) {
            buildingGrids.add(new ArrayList<>());
            for (int j = 0; j < numX; j++) {
                buildingGrids.get(i).add(new BuildingGrid(96+384*j,96+384*i));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < numY; i++) {
            for (int j = 0; j < numX; j++) {
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
    public int lengthX(){
        return numX;
    }

    public int lengthY(){
        return numY;
    }
}
