package com.timcamara.testeroids.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.timcamara.testeroids.components.Asteroid;
import com.timcamara.testeroids.components.Graphic;
import com.timcamara.testeroids.components.Position;

public class CollisionSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Graphic> gm;
	
	@SuppressWarnings("unchecked")
	public CollisionSystem() {
		super(Aspect.getAspectForAll(Position.class, Graphic.class, Asteroid.class));
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
		asteroid.deleteFromWorld();
	}
}
