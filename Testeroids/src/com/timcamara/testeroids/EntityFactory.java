package com.timcamara.testeroids;

import java.util.Random;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.timcamara.testeroids.components.*;

public class EntityFactory {
	private static Random rand = new Random();
	
	public static Entity createPlayer(World world, TextureAtlas atlas) {
		Entity ship = world.createEntity();
		ship.addComponent(new Player());
		Graphic shipGraphic = new Graphic(atlas.findRegion("Firebug"));
		ship.addComponent(shipGraphic);
		ship.addComponent(new Position((TesteroidsGame.screen_width / 2) - (shipGraphic.sprite.getWidth() / 2), (TesteroidsGame.screen_height / 2) - (shipGraphic.sprite.getHeight() / 2)));
		ship.addComponent(new Velocity(0, 0, Player.max_speed, Player.acceleration, Player.friction));
		ship.addComponent(new Health(1));
		world.getManager(TagManager.class).register("PLAYER", ship);
		ship.addToWorld();
		
		return ship;
	}
	
	public static Entity createAsteroid(World world, TextureAtlas atlas) {
		Entity asteroid = world.createEntity();
		asteroid.addComponent(new Asteroid(2));
		asteroid.addComponent(new Graphic(atlas.findRegion("asteroid_l", 1)));
		asteroid.addComponent(new Position((TesteroidsGame.screen_width), (TesteroidsGame.screen_height)));
		asteroid.addComponent(new Velocity(Asteroid.max_speed, (float)rand.nextInt(360)));
		world.getManager(GroupManager.class).add(asteroid, "ASTEROIDS");
		asteroid.addToWorld();
		
		return asteroid;
	}
	
	public static Entity createBullet(World world, TextureAtlas atlas, float x, float y, float angle) {
		Entity bullet = world.createEntity();
		bullet.addComponent(new Bullet());
		bullet.addComponent(new Graphic(atlas.findRegion("bullet")));
		bullet.addComponent(new Position(x, y, angle));
		bullet.addComponent(new Velocity(Bullet.speed, angle));
		world.getManager(GroupManager.class).add(bullet, "BULLETS");
		bullet.addToWorld();
		
		return bullet;
	}
	
	public static Entity createBackground(World world, TextureAtlas atlas) {
		Entity bg = world.createEntity();
		bg.addComponent(new Graphic(atlas.findRegion("starfield")));
		bg.addComponent(new Position());
		bg.addToWorld();
		
		return bg;
	}
}
