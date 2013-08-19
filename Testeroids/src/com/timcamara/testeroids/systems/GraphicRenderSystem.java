package com.timcamara.testeroids.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.timcamara.testeroids.components.Position;
import com.timcamara.testeroids.components.Graphic;

public class GraphicRenderSystem extends EntitySystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Graphic> sm;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@SuppressWarnings("unchecked")
	public GraphicRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Position.class, Graphic.class));
		this.camera = camera;
	}
	
	@Override
	protected void initialize() {
		batch = new SpriteBatch();
	}
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for(int i = 0; i < entities.size(); i++) {
			process(entities.get(i));
		}
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}
	
	protected void process(Entity e) {
		if(pm.has(e)) {
			Position position = pm.getSafe(e);
			Graphic graphic = sm.get(e);
			
			graphic.sprite.setPosition(position.x, position.y);
			graphic.sprite.setRotation(position.rotation % 360);
			graphic.sprite.draw(batch);
		}
	}
	
	@Override
	protected void end() {
		batch.end();
	}
}
