package com.timcamara.testeroids.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Graphic extends Component {
	public Sprite  sprite;
	public boolean disabled;
	
	public Graphic(TextureAtlas.AtlasRegion region) {
		sprite  = new Sprite(region);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
	}
}
