package com.alpayyildiray.pacman.stages;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.MenuButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuStage extends GameStage {
	
	public MenuStage(Pacman pacman, Viewport viewport, Batch batch) {
		super(pacman, viewport, batch);
		
		MenuButton bStartGame = new MenuButton(100, 100, "Start game", () -> pacman.levelUp());
		bStartGame.setName("StartGame");
		addActor(bStartGame);
		
		Gdx.input.setInputProcessor(this);
		setKeyboardFocus(getRoot());
		
		viewport.getCamera().position.set(pacman.getWorldWidth() / 2, pacman.getWorldHeight() / 2, 0);
	}
}
