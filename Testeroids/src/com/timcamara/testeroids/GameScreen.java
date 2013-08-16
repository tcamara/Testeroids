package com.timcamara.testeroids;

import java.util.Random;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.timcamara.testeroids.components.*;
import com.timcamara.testeroids.systems.CollisionSystem;
import com.timcamara.testeroids.systems.GraphicRenderSystem;
import com.timcamara.testeroids.systems.InputSystem;
import com.timcamara.testeroids.systems.MovementSystem;

public class GameScreen implements Screen {
	private OrthographicCamera  camera;
	private TesteroidsGame      game;
	private TextureAtlas        atlas;
	private World               world;
	private GraphicRenderSystem spriteRenderSystem;
	private MovementSystem      movementSystem;
	private InputSystem         inputSystem;
	private CollisionSystem     collisionSystem;
	
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
		collisionSystem    = world.setSystem(new CollisionSystem(), true);
		world.initialize();
		world.setManager(new GroupManager());
		world.setManager(new TagManager());
		
		// Add Background
		Entity bg = world.createEntity();
		bg.addComponent(new Graphic(atlas.findRegion("starfield")));
		bg.addComponent(new Position());
		bg.addToWorld();
		
		// Add Ship
		Entity ship = world.createEntity();
		ship.addComponent(new Player());
		Graphic shipGraphic = new Graphic(atlas.findRegion("Firebug"));
		ship.addComponent(shipGraphic);
		ship.addComponent(new Position((TesteroidsGame.screen_width / 2) - (shipGraphic.sprite.getWidth() / 2), (TesteroidsGame.screen_height / 2) - (shipGraphic.sprite.getHeight() / 2)));
		ship.addComponent(new Velocity(0, 0, Player.max_speed, Player.acceleration, Player.friction));
		ship.addComponent(new Health(1));
		world.getManager(TagManager.class).register("PLAYER", ship);
		ship.addToWorld();
		
		// Add Asteroid
		Random rand = new Random();
		for(int i = 0; i < 5; i++) {
			Entity asteroid = world.createEntity();
			asteroid.addComponent(new Asteroid(2));
			asteroid.addComponent(new Graphic(atlas.findRegion("asteroid_l", 1)));
			asteroid.addComponent(new Position((TesteroidsGame.screen_width), (TesteroidsGame.screen_height)));
			asteroid.addComponent(new Velocity(Asteroid.max_speed, (float)rand.nextInt(360), Asteroid.max_speed));
			world.getManager(GroupManager.class).add(asteroid, "ASTEROIDS");
			asteroid.addToWorld();
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
