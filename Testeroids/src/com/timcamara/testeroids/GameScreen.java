package com.timcamara.testeroids;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.timcamara.testeroids.systems.*;

public class GameScreen implements Screen {
	private TesteroidsGame       game;
	private OrthographicCamera   camera;
	private TextureAtlas         atlas;
	private World                world;
	private GraphicRenderSystem  spriteRenderSystem;
	private MovementSystem       movementSystem;
	private BulletMovementSystem bulletMovementSystem;
	private InputSystem          inputSystem;
	private CollisionSystem      collisionSystem;
	
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
		spriteRenderSystem   = world.setSystem(new GraphicRenderSystem(camera), true);
		movementSystem       = world.setSystem(new MovementSystem(), true);
		bulletMovementSystem = world.setSystem(new BulletMovementSystem(), true);
		inputSystem          = world.setSystem(new InputSystem(world, atlas), true);
		collisionSystem      = world.setSystem(new CollisionSystem(), true);
		world.initialize();
		world.setManager(new GroupManager());
		world.setManager(new TagManager());
		
		// Add Entities
		EntityFactory.createBackground(world, atlas);
		EntityFactory.createPlayer(world, atlas);

		for(int i = 0; i < 5; i++) {
			EntityFactory.createAsteroid(world, atlas);
		}
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
	    bulletMovementSystem.process();
	    inputSystem.process();
	    collisionSystem.process();
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
