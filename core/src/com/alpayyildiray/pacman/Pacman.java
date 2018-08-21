package com.alpayyildiray.pacman;

import com.alpayyildiray.pacman.stages.GameStage;
import com.alpayyildiray.pacman.stages.MenuStage;
import com.alpayyildiray.pacman.stages.Playstage10;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Pacman extends Game {
	
	private int currentLevel = 0;
	private final int levelcount = 2;
	
	private SpriteBatch batch;
	private GameStage menuStage;
	private GameStage playstage10;
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private float worldWidth = 800;
	private float worldHeight = 800;
	private float menuHeight = 200;
//	private float aspectRatio;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
//		aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		viewport = new FitViewport(worldWidth, worldWidth, camera);
		viewport.apply();
		int width = Gdx.app.getGraphics().getDisplayMode().width/2;
		int height = Gdx.app.getGraphics().getDisplayMode().height/2;
		Gdx.graphics.setWindowedMode(width, height);
//		Gdx.graphics.setWindowedMode(800, 800);
	}

	@Override
	public void render() {
//		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
//		camera.update();
//		batch.setProjectionMatrix(camera.combined);
		
		switch(currentLevel) {
			case 0:
				if(menuStage == null) {
					menuStage = new MenuStage(this, viewport, batch);
					menuStage.init();
					Gdx.input.setInputProcessor(menuStage);
					resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}
				menuStage.act(Gdx.graphics.getDeltaTime());
				menuStage.draw();
				break;
			case 1:
				if(playstage10 == null) {
					playstage10 = new Playstage10(this, viewport, batch);
					playstage10.init();
					Gdx.input.setInputProcessor(playstage10);
					resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}
				playstage10.act(Gdx.graphics.getDeltaTime());
				playstage10.draw();
				break;
		}
	}
	
	@Override
	public void resize(int width, int height) {
//		aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
//		aspectRatio = newWidth / newHeight;
//		viewport.update(width, height);
		if(currentLevel == 0) {
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
		currentLevel = level;
		switch(currentLevel) {
			case 0:
				menuStage = new MenuStage(this, viewport, batch);
				Gdx.input.setInputProcessor(menuStage);
				setScreen((Screen)menuStage);
				break;
			case 1:
				playstage10 = new Playstage10(this, viewport, batch);
				Gdx.input.setInputProcessor(playstage10);
				setScreen((Screen)playstage10);
				break;
		}
	}
}
