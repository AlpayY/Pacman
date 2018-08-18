package com.alpayyildiray.pacman.stages;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.Level10Background;
import com.alpayyildiray.pacman.actors.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Playstage10 extends GameStage {
	
	public Playstage10(Pacman pacman, Viewport viewport, Batch batch) {
		super(pacman, viewport, batch);
		
		addActor(new Level10Background());
		addActor(new Player());
		
		Gdx.input.setInputProcessor(this);
		setKeyboardFocus(getActors().get(1));
		
		viewport.getCamera().position.set(pacman.getWorldWidth() / 2, pacman.getWorldHeight() / 2, 0);
	}
}