package com.alpayyildiray.pacman.stages;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.backgrounds.WindowBackground;
import com.alpayyildiray.pacman.actors.entities.Player;
import com.alpayyildiray.pacman.actors.gameobjects.GameWall;
import com.alpayyildiray.pacman.stages.wallgroups.ObjectGroup10;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Playstage10 extends GameStage {
	
	private final int xTiles = 20;
	private final int yTiles = 20;
	
	public Playstage10(Pacman pacman, Viewport viewport, Batch batch) {
		super(pacman, viewport, batch);
		
		setTilesX(xTiles);
		setTilesY(yTiles);
		
		WindowBackground bg = new WindowBackground("20-to-20-background.png");
		Player player = new Player();
		
		addActor(bg);
		addActor(player);
		
		Group wallGroup = new ObjectGroup10();
		addActor(wallGroup);
		
		player.setZIndex(10);
		setKeyboardFocus(player);
		
		init();
	}
}
