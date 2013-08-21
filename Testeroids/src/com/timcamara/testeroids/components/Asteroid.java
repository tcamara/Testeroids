package com.timcamara.testeroids.components;

import com.artemis.Component;

public class Asteroid extends Component {
	public static final float turn_rate = 6f;
	public static final float max_speed = 100f;
	public              int   size;
		
	public Asteroid(int size) {
		this.size = size;
	}
	
	public String getRegionName(int size) {
		String val =  "asteroid_";
		
		if(size == 2) {
			val += "l";
		}
		else if(size == 1) {
			val += "m";
		}
		else {
			val += "s";
		}
		
		return val;
	}
}
