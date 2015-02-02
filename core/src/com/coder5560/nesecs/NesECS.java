package com.coder5560.nesecs;

import badlogic.demo.ecs.GameFactory;
import badlogic.demo.ecs.systems.ViewportSystem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class NesECS extends ApplicationAdapter {
	GameFactory gameFactory;

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		gameFactory.getWorld().getSystem(ViewportSystem.class)
		.onGameResize(width, height);
	}

	@Override
	public void create() {
		gameFactory = new GameFactory();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameFactory.processWorld(Gdx.graphics.getDeltaTime());
	}
}
