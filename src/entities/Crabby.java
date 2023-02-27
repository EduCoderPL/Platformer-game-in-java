package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.RIGHT;
import static utils.Constants.EnemyConstants.*;

public class Crabby extends Enemy{

    private int attackBoxOffsetX;


    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitBox(22, 19);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(82 * Game.SCALE), (int)(19 * Game.SCALE));
        attackBoxOffsetX = (int)(Game.SCALE * 30);
    }

    public void update(int[][] lvlData, Player player){
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitBox.x - attackBoxOffsetX;
        attackBox.y = hitBox.y;

    }

    private void updateBehavior(int[][] lvlData, Player player){
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir)
            updateInAir(lvlData);
        else {
            switch (state) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if(canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseToAttack(player))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if(animIndex == 0)
                        attackChecked = false;
                    if(animIndex == 3 && !attackChecked)
                        checkEnemyHit(attackBox, player);
            }
        }
    }



    public int flipX(){
        return walkDir == RIGHT ? width : 0;
    }

    public int flipW(){
        return walkDir == RIGHT ? -1 : 1;
    }
}
