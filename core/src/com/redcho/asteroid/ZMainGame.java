package com.redcho.asteroid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ZMainGame extends ApplicationAdapter {

    public static int WIDTH;
    public static int HEIGHT;

    public static OrthographicCamera cam;
    private GameStateManager gsm;
	
	@Override
	public void create () {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        cam = new OrthographicCamera(WIDTH, HEIGHT);
        cam.translate(WIDTH / 2, HEIGHT /2);
        cam.update();

        gsm = new GameStateManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.draw();

	}
}
