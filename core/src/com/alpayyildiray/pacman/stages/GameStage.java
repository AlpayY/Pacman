package com.alpayyildiray.pacman.stages;

import java.awt.IllegalComponentStateException;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.PacmanActor;
import com.alpayyildiray.pacman.actors.PacmanActor.Type;
import com.alpayyildiray.pacman.actors.gameobjects.MenuButton;
import com.alpayyildiray.pacman.stages.wallgroups.PacmanObjectGroup;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameStage extends Stage implements Screen {

	private Pacman pacman;
	public int tileCountX = 0;
	public int tileCountY = 0;
	
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
	}
	
	public void setTilesX(int tiles) {
		tileCountX = tiles;
	}
	
	public void setTilesY(int tiles) {
		tileCountY = tiles;
	}
	
	public void setFoodCount(int count) {
		if(count == 0) {
			pacman.setLevel(0);
		}
		totalFood = count;
	}
	
	public int getTileSize() {
		if(tileCountX <= 0 || tileCountY <= 0) {
			throw new IllegalComponentStateException("Tile count invalid! Not set?");
		}
		if(tileCountX < tileCountY) {
			return (int)pacman.getWorldWidth() / tileCountX;
		} else {
			return (int)pacman.getWorldHeight() / tileCountY;
		}
	}
	
	public int getTilesX() {
		return tileCountX;
	}
	
	public int getTilesY() {
		return tileCountY;
	}
	
	public int getFoodCount() {
		return totalFood;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		draw();
		return;
	}

	@Override
	public void resize(int width, int height) {
		return;
	}

	@Override
	public void pause() {
		return;
	}

	@Override
	public void resume() {
		return;
	}

	@Override
	public void hide() {
		return;
	}
}
