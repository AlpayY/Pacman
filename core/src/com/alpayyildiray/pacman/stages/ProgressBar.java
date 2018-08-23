package com.alpayyildiray.pacman.stages;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.gameobjects.FoodCounter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ProgressBar extends GameStage {

	private FoodCounter foodCounter;
	
	public ProgressBar() {
		// TODO Auto-generated constructor stub
	}

	public ProgressBar(Viewport viewport) {
		super(viewport);
		// TODO Auto-generated constructor stub
	}

	public ProgressBar(Viewport viewport, Batch batch) {
		super(viewport, batch);
		// TODO Auto-generated constructor stub
	}

	public ProgressBar(Pacman pacman, Viewport viewport, Batch batch) {
		super(pacman, viewport, batch);
		foodCounter = new FoodCounter(0, 0);
		addActor(foodCounter);
	}
}
