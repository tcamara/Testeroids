package com.timcamara.testeroids.components;

import com.artemis.Component;
import com.artemis.utils.TrigLUT;

public class Velocity extends Component {
	public float x;
	public float y;
	public float x_max;
	public float y_max;
	
	public Velocity(float x_max, float y_max) {
		this(0, 0, x_max, y_max);
	}
	
	public Velocity(float x, float y, float x_max, float y_max) {
		this.x = x;
		this.y = y;
		this.x_max = x_max;
		this.y_max = y_max;
	}
	
	public void add(float speed, float angle) {
		x += speed * TrigLUT.cosDeg(angle + 90);
		y += speed * TrigLUT.sinDeg(angle + 90);
		
		// Make sure we don't exceed limit
		limiter();
	}
	
	public void set(float speed, float angle) {
		x = speed * TrigLUT.cosDeg(angle + 90);
		y = speed * TrigLUT.sinDeg(angle + 90);
		
		// Make sure we don't exceed limit
		limiter();
	}
	
	private void limiter() {
		if(x > x_max) {
			x = x_max;
		}
		if(y > y_max) {
			y = y_max;
		}
		if(x < -x_max) {
			x = -x_max;
		}
		if(y < -y_max) {
			y = -y_max;
		}
	}
}
