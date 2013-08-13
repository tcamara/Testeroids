package com.timcamara.testeroids.components;

import com.artemis.Component;
import com.timcamara.testeroids.TesteroidsGame;

public class Position extends Component {
	public float x;
	public float y;
	public float rotation;
	
	public Position() {
		this(0, 0, 0);
	}
	
	public Position(float x, float y) {
		this(x, y, 0);
	}
	
	public Position(float x, float y, float rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
	
	public void addRotation(float r) {
		rotation = (rotation + r) % 360;
	}
	
	public void add(float x, float y) {
		this.x += x;
		this.y += y;
		
		limiter();
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
		
		limiter();
	}
	
	public void set(float x, float y, float r) {
		this.x = x;
		this.y = y;
		this.rotation = r;
		
		limiter();
	}
	
	// It's a spherical world!
	private void limiter() {
		if(x > TesteroidsGame.screen_width + 20) {
			x = -20;
		}
		if(y > TesteroidsGame.screen_height + 20) {
			y = -20;
		}
		if(x < -20) {
			x = TesteroidsGame.screen_width + 20;
		}
		if(y < -20) {
			y = TesteroidsGame.screen_height + 20;
		}
	}
}
