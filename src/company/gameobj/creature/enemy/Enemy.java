package company.gameobj.creature.enemy;

import company.Global;
import company.gameobj.creature.Creature;
import company.gametest9th.utils.Animator;

public abstract class Enemy extends Creature {

    public Enemy(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, int speed, String img, FLY_ABILITY flyAbility, Animator.State moveStatus) {
        super(x, y, Global.SCREEN_WIDTH /2, Global.SCREEN_HEIGHT /2, painterWidth, painterHeight, colliderWidth, colliderHeight, value, speed, img, flyAbility, moveStatus);
    }
}
