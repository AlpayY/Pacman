package com.alpayyildiray.pacman.stages;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.MenuButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameStage extends Stage {

	private Pacman pacman;

	public GameStage() {
		throw new UnsupportedOperationException();
	}

	public GameStage(Viewport viewport) {
		throw new UnsupportedOperationException();
	}

	public GameStage(Viewport viewport, Batch batch) {
		super(viewport, batch);
	}
	
	public GameStage(Pacman pacman, Viewport viewport, Batch batch) {
		this(viewport, batch);
		this.pacman = pacman;
	}
	
	public Pacman getPacman() {
		return pacman;
	}
}
