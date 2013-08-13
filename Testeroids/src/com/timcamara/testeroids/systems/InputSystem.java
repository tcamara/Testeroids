package com.timcamara.testeroids.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.timcamara.testeroids.components.*;


public class InputSystem extends EntityProcessingSystem implements InputProcessor {
	private boolean forward, left, right, fire;
//    private float timeToFire;
	
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	
	@SuppressWarnings("unchecked")
	public InputSystem() {
		super(Aspect.getAspectForAll(Player.class, Position.class, Velocity.class));
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
		
		if(fire) {
			
		}
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
