package com.alpayyildiray.pacman;

import com.alpayyildiray.pacman.stages.MenuStage;
import com.alpayyildiray.pacman.stages.Playstage10;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Pacman extends ApplicationAdapter {
	
	private int currentLevel = 0;
	private final int levelcount = 2;
	
	private SpriteBatch batch;
	private Stage menuStage;
	private Stage playstage10;
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private final int xTiles = 20;
	private final int yTiles = 20;
	private int tileSize;
	
	private float worldWidth = 800;
	private float worldHeight = 800;
	private float aspectRatio;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		tileSize = (int)worldHeight / yTiles;
		aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		viewport = new FitViewport(worldWidth * aspectRatio, worldHeight, camera);
		viewport.apply();
	}

	@Override
	public void render() {
//		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		switch(currentLevel) {
			case 0:
				if(menuStage == null) {
					menuStage = new MenuStage(this, viewport, batch);
				}
				menuStage.act(Gdx.graphics.getDeltaTime());
				menuStage.draw();
				break;
			case 1:
				if(playstage10 == null) {
					playstage10 = new Playstage10(this, viewport, batch);
				}
				playstage10.act(Gdx.graphics.getDeltaTime());
				playstage10.draw();
				break;
		}
	}
	
	@Override
	public void resize(int width, int height) {
		aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
//		viewport.update(width, height);
		camera.position.set(worldWidth / 2, worldHeight / 2, 0);
		super.resize(width, height);
	}
	
	public float getWorldWidth() {
		return worldWidth;
	}
	
	public float getWorldHeight() {
		return worldHeight;
	}
	
	public float getTileSize() {
		return tileSize;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	public void levelUp() {
		currentLevel += 1;
		currentLevel %= levelcount;
	}
}
