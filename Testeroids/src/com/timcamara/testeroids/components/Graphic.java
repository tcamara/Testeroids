package com.timcamara.testeroids.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Graphic extends Component {
	public Sprite  sprite;
	
	public Graphic(TextureAtlas.AtlasRegion region) {
		sprite  = new Sprite(region);
	}
}
