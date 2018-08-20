package com.alpayyildiray.pacman.stages;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.backgrounds.WindowBackground;
import com.alpayyildiray.pacman.actors.gameobjects.MenuButton;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuStage extends GameStage {
	
	public MenuStage(Pacman pacman, Viewport viewport, Batch batch) {
		super(pacman, viewport, batch);
		
		WindowBackground background = new WindowBackground("MenuBackground.png");
		
		Vector2 vStartGame = new Vector2(pacman.getWorldWidth()/2, pacman.getWorldHeight()/10*4);
		Vector2 vExit = new Vector2(pacman.getWorldWidth()/2, pacman.getWorldHeight()/10*8);
		
		MenuButton bStartGame = new MenuButton(vStartGame.x, vStartGame.y, "START", () -> pacman.levelUp());
		bStartGame.setName("StartGame");
		MenuButton bExit = new MenuButton(vExit.x, vExit.y, "EXIT", () -> pacman.levelUp());
		bExit.setName("ExitGame");
		
		addActor(background);
		addActor(bStartGame);
		addActor(bExit);
		
		init();
	}
}
