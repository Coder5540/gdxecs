package com.coder5560.nesecs.desktop;

import badlogic.demo.ecs.R;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.coder5560.nesecs.NesECS;

public class ECSDesktop {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = R.WIDTH_SCREEN;
		config.height = R.HEIGHT_SCREEN;
		config.title = "Demo Ecs";
		new LwjglApplication(new NesECS(), config);
		// new LwjglApplication(new NsGame(), config);
	}
}
