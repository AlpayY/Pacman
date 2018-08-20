package com.alpayyildiray.pacman.stages;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.PacmanActor;
import com.alpayyildiray.pacman.actors.PacmanActor.Type;
import com.alpayyildiray.pacman.actors.gameobjects.MenuButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameStage extends Stage {

	private Pacman pacman;
	public int tileCountX;
	public int tileCountY;
	
	public int totalFood = 0;

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
	
	public void init() {
		for(Actor actor : getActors()) {
			((PacmanActor)actor).init();
		}
		
		Gdx.input.setInputProcessor(this);
		
		getViewport().getCamera().position.set(pacman.getWorldWidth() / 2, pacman.getWorldHeight() / 2, 0);
	}
	
	public void setTilesX(int tiles) {
		tileCountX = tiles;
	}
	
	public void setTilesY(int tiles) {
		tileCountY = tiles;
	}
	
	public void setFoodCount(int count) {
		totalFood = count;
	}
	
	public int getTileSize() {
		if(tileCountX < tileCountY) {
			return (int)pacman.getWorldHeight() / tileCountY;
		} else {
			return (int)pacman.getWorldWidth() / tileCountX;
		}
	}
	
	public int getTilesX() {
		return tileCountX;
	}
	
	public int getTilesY() {
		return tileCountY;
	}
	
	public int getFoodCount() {
		PacmanActor actors[] = (PacmanActor[])(getActors().toArray());
		int count = 0;
		for(PacmanActor actor : actors) {
			if(actor.getType() == Type.FOOD) ++count;
		}
		return count;
	}
}
