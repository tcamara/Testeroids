package com.timcamara.testeroids;

import com.badlogic.gdx.Game;

public class TesteroidsGame extends Game {
	public static final int     screen_width  = 800;
	public static final int     screen_height = 480;
	public              boolean dev_mode      = false;
		
	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}
}
