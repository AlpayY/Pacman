package com.alpayyildiray.pacman.stages;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.MenuButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuStage extends Stage {

	public MenuStage() {
		throw new UnsupportedOperationException();
	}

	public MenuStage(Viewport viewport) {
		throw new UnsupportedOperationException();
	}

	public MenuStage(Viewport viewport, Batch batch) {
		super(viewport, batch);
	}
	
	public MenuStage(Pacman pacman, Viewport viewport, Batch batch) {
		this(viewport, batch);
		MenuButton menuButton = new MenuButton(pacman, 100, 100);
		addActor(menuButton);
		
		Gdx.input.setInputProcessor(this);
		setKeyboardFocus(getRoot());
		
		viewport.getCamera().position.set(pacman.getWorldWidth() / 2, pacman.getWorldHeight() / 2, 0);
	}
}
