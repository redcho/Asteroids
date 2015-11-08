package com.redcho.asteroid;

import com.redcho.gamestates.GameState;
import com.redcho.gamestates.PlayState;

/**
 * Created by jagcurasur on 11/8/15.
 */
public class GameStateManager {

    private GameState gameState;

    public static final int MENU = 0;
    public static final int PLAY = 1;

    public GameStateManager(){
        setState(PLAY);
    }

    public void setState(int state){
        if( gameState != null) {
            gameState.dispose();
        }
        if( state == MENU ){
            // Switch to menu
        }
        if( state == PLAY ){
            gameState = new PlayState(this);
        }
    }

    public void update(float dt){
        gameState.update(dt);
    }

    public void draw(){
        gameState.draw();
    }


}
