package company.gameobj.creature.enemy;

import company.Global;
import company.gameobj.creature.Creature;
import company.gametest9th.utils.Animator;

public abstract class Enemy extends Creature {

    public Enemy(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, int speed, String img, FLY_ABILITY flyAbility, Animator.State moveStatus) {
        super(x, y, Global.SCREEN_X/2, Global.SCREEN_Y/2, painterWidth, painterHeight, colliderWidth, colliderHeight, value, speed, img, flyAbility, moveStatus);
    }

    @Override
    public void walk() {
        //quit walk if the human is not moving
        if(this.getMoveStatus() == Animator.State.STAND){
            return;
        }
        //is not blocked by any buildings
        if(this.getBlockedDir()==null){
            if (targetX() == painter().centerX()){
                this.setWalkingDir((targetY() > painter().centerY()) ? Global.Direction.DOWN : Global.Direction.UP);
            }
            else if (targetY() == painter().centerY()){
                this.setWalkingDir((targetX() > painter().centerX()) ? Global.Direction.RIGHT: Global.Direction.LEFT);
            }
        }
        else if(this.getBlockedDir()!=null){
            if(this.getWalkingDir()==this.getBlockedDir()){
                switch (this.getBlockedDir()){
                    case LEFT:
                    case RIGHT:{
                        //當在往上或往下的時候撞到障礙物且已經來到目標的Ｘ做標時隨機選擇一個方向去走
                        if(this.painter().centerY()==targetY()){
                            this.setWalkingDir((Global.random(0,1)==0) ? Global.Direction.UP : Global.Direction.DOWN);
                        }
                        //當在往上或往下的時候撞到障礙物且尚未來到目標的Ｘ做標時選擇會靠近目標的方向去走
                        else{
                            this.setWalkingDir((targetY()>this.painter().centerY()) ? Global.Direction.UP : Global.Direction.DOWN);
                        }
                        break;
                    }
                    case UP:
                    case DOWN:{
                        //當在往上或往下的時候撞到障礙物且已經來到目標的Ｘ做標時隨機選擇一個方向去走
                        if(this.painter().centerX()==this.painter().centerX()){
                            this.setWalkingDir((Global.random(0,1)==0) ? Global.Direction.LEFT : Global.Direction.RIGHT);
                        }
                        //當在往上或往下的時候撞到障礙物且尚未來到目標的Ｘ做標時選擇會靠近目標的方向去走
                        else{
                            this.setWalkingDir((targetX()<this.painter().centerX()) ? Global.Direction.LEFT : Global.Direction.RIGHT);
                        }
                        break;
                    }
                }
            }
        }
        int speed = speed();
        if(this.getBlockedDir()==null){
            if(this.getWalkingDir()== Global.Direction.LEFT || this.getWalkingDir()== Global.Direction.RIGHT){
                speed = Math.min(speed, Math.abs(targetX() - painter().centerX()));
            }
            else if(this.getWalkingDir()== Global.Direction.UP || this.getWalkingDir()== Global.Direction.DOWN){
                speed = Math.min(speed, Math.abs(targetY() - painter().centerY()));
            }
        }
        switch (this.getWalkingDir()){
            case LEFT:{
                this.translateX(-1*speed);
                break;
            }
            case RIGHT:{
                this.translateX(speed);
                break;
            }
            case UP:{
                this.translateY(-1*speed);
                break;
            }
            case DOWN:{
                this.translateY(speed);
                break;
            }
        }
        if(this.isAtTarget()){
            this.setMoveStatus(Animator.State.STAND);
        }
    }
}
