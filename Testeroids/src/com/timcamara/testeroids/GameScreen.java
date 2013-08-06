package com.timcamara.testeroids;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.timcamara.testeroids.components.*;
import com.timcamara.testeroids.systems.*;

public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private TesteroidsGame     game;
	private TextureAtlas       atlas;
	private World              world;
	private GraphicRenderSystem spriteRenderSystem;
	private MovementSystem     movementSystem;
	private InputSystem        inputSystem;
	
	public GameScreen(TesteroidsGame tgame) {
		game = tgame;
		
		// Load texture atlas
		if(game.dev_mode) {
			atlas = new TextureAtlas(Gdx.files.internal("images/game_pack_dev.txt"));
		}
		else {
			atlas = new TextureAtlas(Gdx.files.internal("images/game_pack.txt"));
		}
		
		// Set up the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, TesteroidsGame.screen_width, TesteroidsGame.screen_height);
		
		// Set up the world
		world = new World();
		spriteRenderSystem = world.setSystem(new GraphicRenderSystem(camera), true);
		movementSystem     = world.setSystem(new MovementSystem(), true);
		inputSystem        = world.setSystem(new InputSystem(), true);
		world.initialize();
		
		// Add Background
		Entity bg = world.createEntity();
		bg.addComponent(new Graphic(atlas.findRegion("starfield")));
		bg.addComponent(new Position());
		bg.addToWorld();
		
		// Add ship
		Entity ship = world.createEntity();
		ship.addComponent(new Player());
		Graphic graphic = new Graphic(atlas.findRegion("Firebug"));
		ship.addComponent(graphic);
		ship.addComponent(new Position((TesteroidsGame.screen_width / 2) - (graphic.sprite.getWidth() / 2), (TesteroidsGame.screen_height / 2) - (graphic.sprite.getHeight() / 2)));
		ship.addComponent(new Velocity(500f, 500f));
		ship.addToWorld();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0.2f,1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    camera.update();
	    
	    world.setDelta(delta);
	    world.process();
	    spriteRenderSystem.process();
	    movementSystem.process();
	    inputSystem.process();
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void show() {
		
	}
	
	@Override
	public void hide() {
		
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void dispose() {
		
	}
}