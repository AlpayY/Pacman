package com.alpayyildiray.pacman.stages;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.backgrounds.Level10Background;
import com.alpayyildiray.pacman.actors.entities.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Playstage10 extends GameStage {
	
	public Playstage10(Pacman pacman, Viewport viewport, Batch batch) {
		super(pacman, viewport, batch);
		
		Player player = new Player();
		Level10Background bg = new Level10Background();
		
		addActor(player);
		addActor(bg);
		
		player.setZIndex(10);
		
		setKeyboardFocus(player);
		
		init();
	}
}
