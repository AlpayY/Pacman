package com.alpayyildiray.pacman;

import com.alpayyildiray.pacman.stages.GameStage;
import com.alpayyildiray.pacman.stages.PlayStage10;
import com.alpayyildiray.pacman.stages.levels.Level10;
import com.alpayyildiray.pacman.stages.levels.MenuStage;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Pacman extends Game {
	
	private int currentLevel = 0;
	private final int levelcount = 2;
	
	private SpriteBatch batch;
	private GameStage[] gameStage;
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private float worldWidth = 800;
	private float worldHeight = 800;
	private float menuHeight = 100;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		gameStage = new GameStage[levelcount];
		
		camera = new OrthographicCamera();
		viewport = new FitViewport(worldWidth, worldWidth, camera);
		viewport.apply();
		viewport.setWorldSize(worldWidth, worldHeight);
		int width = Gdx.app.getGraphics().getDisplayMode().width/2;
		int height = Gdx.app.getGraphics().getDisplayMode().height/2;
		Gdx.graphics.setWindowedMode(width, height);
		
		setLevel(currentLevel);
	}

	@Override
	public void render() {
//		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		gameStage[currentLevel].act(Gdx.graphics.getDeltaTime());
		gameStage[currentLevel].draw();
	}
	
	@Override
	public void resize(int width, int height) {
		if(currentLevel == 0) {
			viewport.setWorldSize(worldWidth, worldHeight);
			camera.position.set(worldWidth / 2, worldWidth / 2, 0);
		} else {
			viewport.setWorldSize(worldWidth, getTotalHeight());
			camera.position.set(worldWidth / 2, getTotalHeight() / 2, 0);
		}
		viewport.update(width, height);
		super.resize(width, height);
	}
	
	public float getWorldWidth() {
		return worldWidth;
	}
	
	public float getWorldHeight() {
		return worldHeight;
	}
	
	public float getTotalHeight() {
		return worldHeight+menuHeight;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	public void setLevel(int level) {
		if(gameStage[currentLevel] != null) {
			gameStage[currentLevel].dispose();
			gameStage[currentLevel] = null;
		}
		currentLevel = level;
		if(gameStage[currentLevel] == null) {
			switch(currentLevel) {
				case 0:
					gameStage[currentLevel] = new MenuStage(this, viewport, batch);
					break;
				case 1:
					gameStage[currentLevel] = new Level10(this, viewport, batch);
					break;
			}
		}
		Gdx.input.setInputProcessor(gameStage[currentLevel]);
		gameStage[currentLevel].init();
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		setScreen((Screen)gameStage[currentLevel]);
	}
}
