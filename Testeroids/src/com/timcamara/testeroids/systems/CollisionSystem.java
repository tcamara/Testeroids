package com.timcamara.testeroids.systems;

import java.util.Random;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.timcamara.testeroids.EntityFactory;
import com.timcamara.testeroids.components.Asteroid;
import com.timcamara.testeroids.components.Graphic;
import com.timcamara.testeroids.components.Position;
import com.timcamara.testeroids.components.Velocity;

public class CollisionSystem extends EntityProcessingSystem {
	private TextureAtlas atlas;
	
	@Mapper ComponentMapper<Asteroid> am;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Graphic>  gm;
	@Mapper ComponentMapper<Velocity> vm;
	
	@SuppressWarnings("unchecked")
	public CollisionSystem(TextureAtlas atlas) {
		super(Aspect.getAspectForAll(Asteroid.class, Position.class, Graphic.class, Velocity.class));
		
		this.atlas = atlas;
	}
	
	@Override
	protected void process(Entity e) {
		// Check for collision with player
		Entity player = world.getManager(TagManager.class).getEntity("PLAYER");
		if(collisionExists(gm.get(e), gm.get(player))) {
			handlePlayerCollision(player);
		}
		
		// Check for collision with bullet
		ImmutableBag<Entity> bag = world.getManager(GroupManager.class).getEntities("BULLETS");
		for(int i = 0; i < bag.size(); i++) {
			Entity bullet = bag.get(i);
			if(collisionExists(gm.get(e), gm.get(bullet))) {
				handleBulletCollision(e, bullet);
			}
		}
	}
	
	private boolean collisionExists(Graphic g1, Graphic g2) {
		return g1.getBounds().overlaps(g2.getBounds());
	}
	
	private void handlePlayerCollision(Entity player) {
		player.disable();
	}
	
	private void handleBulletCollision(Entity asteroid, Entity bullet) {
		bullet.deleteFromWorld();
		
		Asteroid a = am.get(asteroid);
		
		if(a.size == 0) {
			asteroid.deleteFromWorld();
		}
		else {
			Random   rand              = new Random();
			Graphic  asteroid_graphic  = gm.get(asteroid);
			Position asteroid_position = pm.get(asteroid);
			Velocity asteroid_velocity = vm.get(asteroid);
			
			a.size--;
			
			// Reduce asteroid, change it's direction slightly
			asteroid_graphic.changeImage(atlas.findRegion(a.getRegionName(a.size), rand.nextInt(2) + 1));
			asteroid_velocity.speed.rotate(10);
			
			// Add second asteroid, in a slightly different direction
			float angle = asteroid_velocity.speed.angle() + 10f;
			EntityFactory.createAsteroid(world, atlas, a.size, asteroid_position.x, asteroid_position.y, angle);
		}
	}
}
