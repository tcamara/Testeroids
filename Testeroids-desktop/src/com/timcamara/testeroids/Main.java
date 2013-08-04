package com.timcamara.testeroids;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Main {
	public static void main(String[] args) {
		new LwjglApplication(new TesteroidsGame(), "Testeroids", 800, 480, false);
	}
}
