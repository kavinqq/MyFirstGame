package company.gameObj.building;

import company.gameObj.Background.BackgroundComponent.*;
import company.gametest9th.utils.CommandSolver;

import java.awt.event.MouseEvent;

public class MouseController implements CommandSolver.MouseCommandListener {

    BuildingOption buildingOption=new BuildingOption();
    private Building building;
    public MouseController(Building building){
        this.building=building;
    }
    private boolean canCatchBuilding;
    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

        switch (state){
            case MOVED:{
                System.out.println(canCatchBuilding);
                canCatchBuilding = building.isClicked(e.getX(), e.getY());
            }
            case DRAGGED:{
                //System.out.println("BuildingOption_DRAGGER");
                if (canCatchBuilding) {
                    building.mouseTrig(e, state, trigTime);
                }
            }
            case RELEASED:{
                canCatchBuilding=false;
            }

        }
    }
}
