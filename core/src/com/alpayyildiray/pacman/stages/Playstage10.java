package com.alpayyildiray.pacman.stages;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.backgrounds.WindowBackground;
import com.alpayyildiray.pacman.actors.entities.Enemy;
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
		
		WindowBackground bg = new WindowBackground("20-to-20-background.png");
		addActor(bg);
		
		Group objectGroup = new ObjectGroup10();
//		setRoot(objectGroup);
		addActor(objectGroup);
		
		Player player = new Player();
		addActor(player);
		setKeyboardFocus(player);
		
		Enemy enemy01 = new Enemy();
		addActor(enemy01);
		Enemy enemy02 = new Enemy();
		addActor(enemy02);
		Enemy enemy03 = new Enemy();
		addActor(enemy03);
		Enemy enemy04 = new Enemy();
		addActor(enemy04);
		
//		bg.setZIndex(0);
//		objectGroup.setZIndex(1);
//		player.setZIndex(2);
	}
}
