package com.alpayyildiray.pacman.stages;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.backgrounds.WindowBackground;
import com.alpayyildiray.pacman.actors.gameobjects.MenuButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuStage extends GameStage {
	
	public MenuStage(Pacman pacman, Viewport viewport, Batch batch) {
		super(pacman, viewport, batch);
		
		WindowBackground background = new WindowBackground("MenuBackground.png");
		
		Vector2 vStartGame = new Vector2(pacman.getWorldWidth()/2, pacman.getWorldHeight()/5*2);
		Vector2 vExit = new Vector2(pacman.getWorldWidth()/2, pacman.getWorldHeight()/5*1);
		
		MenuButton bStartGame = new MenuButton(vStartGame.x, vStartGame.y, "START", () -> pacman.setLevel(1));
		bStartGame.setName("StartGame");
		MenuButton bExit = new MenuButton(vExit.x, vExit.y, "EXIT", () -> Gdx.app.exit());
		bExit.setName("ExitGame");
		
		addActor(background);
		addActor(bStartGame);
		addActor(bExit);
		
		init();
	}
}
