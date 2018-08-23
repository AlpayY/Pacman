package com.alpayyildiray.pacman.stages;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.backgrounds.WindowBackground;
import com.alpayyildiray.pacman.actors.entities.Player;
import com.alpayyildiray.pacman.stages.wallgroups.ObjectGroup10;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayStage10 extends GameStage {
	
	private final int xTiles = 20;
	private final int yTiles = 20;
	
	public PlayStage10(Pacman pacman, Viewport viewport, Batch batch) {
		super(pacman, viewport, batch);
		
		setTilesX(xTiles);
		setTilesY(yTiles);
		
		Group objectGroup = new ObjectGroup10();
//		setRoot(objectGroup);
		addActor(objectGroup);
		
		WindowBackground bg = new WindowBackground("20-to-20-background.png");
		addActor(bg);
		
		Player player = new Player();
		addActor(player);
		setKeyboardFocus(player);
		
		
		bg.setZIndex(0);
		objectGroup.setZIndex(1);
		player.setZIndex(2);
	}
}
