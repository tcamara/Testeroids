package com.timcamara.testeroids.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.timcamara.testeroids.TesteroidsGame;
import com.timcamara.testeroids.components.*;

public class BulletMovementSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	
	@SuppressWarnings("unchecked")
	public BulletMovementSystem() {
		super(Aspect.getAspectForAll(Position.class, Velocity.class, Bullet.class));
	}
	
	@Override
	protected void process(Entity e) {
		Position position = pm.get(e);
		Velocity velocity = vm.get(e);
		
		// Calculate new position based on velocity
		position.add(velocity.speed.x * world.delta, velocity.speed.y * world.delta);
		
		// Make friction take effect on velocity
		velocity.speed.scl(velocity.friction);
		
		// Delete if we go off the screen
		if(position.x > TesteroidsGame.screen_width + 20 || position.y > TesteroidsGame.screen_height + 20 || position.x < -20 || position.y < -20) {
			e.deleteFromWorld();
		}
	}
}
