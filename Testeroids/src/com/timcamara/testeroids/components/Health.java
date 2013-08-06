package com.timcamara.testeroids.components;

import com.artemis.Component;

public class Health extends Component {
	public float health;
	public float health_max;
	
	public Health(float health_max) {
		this.health     = health_max;
		this.health_max = health_max;
	}
	
	public Health(float health_max, float health) {
		this.health     = health;
		this.health_max = health_max;
	}
}
