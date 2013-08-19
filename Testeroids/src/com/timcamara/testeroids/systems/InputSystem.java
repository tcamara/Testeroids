package com.timcamara.testeroids.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.timcamara.testeroids.EntityFactory;
import com.timcamara.testeroids.components.*;

public class InputSystem extends EntityProcessingSystem implements InputProcessor {
	private World        world;
	private TextureAtlas atlas;
	private boolean      forward, left, right, fire;
    private int          fire_timer;
    private int          fire_timer_max = 20;
	
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	@Mapper ComponentMapper<Graphic>  gm;
	
	@SuppressWarnings("unchecked")
	public InputSystem(World world, TextureAtlas atlas) {
		super(Aspect.getAspectForAll(Player.class, Position.class, Velocity.class, Graphic.class));
		
		this.world = world;
		this.atlas = atlas;
	}
	
	@Override
    protected void initialize() {
            Gdx.input.setInputProcessor(this);
    }
	
	@Override
	protected void process(Entity e) {
		Position position = pm.get(e);
		Velocity velocity = vm.get(e);
		
		if(forward) {
			velocity.add(position.rotation);
		}
		
		if(left) {
			position.addRotation(Player.turn_rate);
		}
		
		if(right) {
			position.addRotation(-Player.turn_rate);
		}
		
		if(fire && fire_timer <= 0) {
			fire_timer = fire_timer_max; // reset timer for bullet limiting
			Graphic graphic = gm.get(e);
			
			EntityFactory.createBullet(world, atlas, (position.x + (graphic.sprite.getWidth() / 2)), (position.y + (graphic.sprite.getHeight() / 2)), position.rotation);
		}
		
		fire_timer--; // tick down till we can fire again
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.A) {
            left = true;
	    }
	    else if(keycode == Input.Keys.D) {
	        right = true;
	    }
	    else if(keycode == Input.Keys.W) {
	        forward = true;
	    }
	    else if(keycode == Input.Keys.SPACE) {
	        fire = true;
	    }
	    
	    return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.A) {
            left = false;
	    }
	    else if(keycode == Input.Keys.D) {
	        right = false;
	    }
	    else if(keycode == Input.Keys.W) {
	        forward = false;
	    }
	    else if(keycode == Input.Keys.SPACE) {
	        fire = false;
	    }
	    
	    return true;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
