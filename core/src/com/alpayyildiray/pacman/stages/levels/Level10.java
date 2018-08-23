package com.alpayyildiray.pacman.stages.levels;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.stages.GameStage;
import com.alpayyildiray.pacman.stages.PlayStage10;
import com.alpayyildiray.pacman.stages.ProgressBar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Level10 extends GameStage {

	PlayStage10 playstage;
	ProgressBar progressBar;
	
	public Level10() {
		// TODO Auto-generated constructor stub
	}

	public Level10(Viewport viewport) {
		super(viewport);
		// TODO Auto-generated constructor stub
	}

	public Level10(Viewport viewport, Batch batch) {
		super(viewport, batch);
	}

	public Level10(Pacman pacman, Viewport viewport, Batch batch) {
		super(pacman, viewport, batch);
		playstage = new PlayStage10(pacman, viewport, batch);
		progressBar = new ProgressBar(pacman, viewport, batch);
	}
	
	@Override
	public void init() {
		super.init();
		playstage.init();
		progressBar.init();
		Gdx.input.setInputProcessor(playstage);
	}
	
	@Override
	public void act(float dt) {
		playstage.act(dt);
		progressBar.setFoodCount(playstage.getFoodCount());
		progressBar.act();
		super.act(dt);
	}
	
	@Override
	public void draw() {
		playstage.draw();
		progressBar.draw();
		super.draw();
	}
}
