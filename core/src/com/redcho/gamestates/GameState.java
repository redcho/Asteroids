package com.redcho.gamestates;

import com.redcho.asteroid.GameStateManager;

/**
 * Created by jagcurasur on 11/8/15.
 */
public abstract class GameState {
    protected GameStateManager gsm;

    protected GameState(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }

    public abstract void init();
    public abstract void update(float dt);
    public abstract void draw();
    public abstract void handleInput();
    public abstract void dispose();
}
