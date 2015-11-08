package com.redcho.asteroid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.redcho.asteroid.ZMainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ZMainGame(), config);
        config.width = 500;
        config.height = 400;
        config.title = "Asteroids";
	}
}
