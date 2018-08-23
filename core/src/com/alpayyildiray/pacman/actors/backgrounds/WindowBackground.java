package com.alpayyildiray.pacman.actors.backgrounds;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.PacmanActor;
import com.alpayyildiray.pacman.stages.GameStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;

public class WindowBackground extends PacmanActor{
	
	private Sprite background;
	private float worldWidth;
	private float worldHeight;

	public WindowBackground(String file) {
		super();
		background = new Sprite(new Texture(Gdx.files.internal(file)));
	}
	
	public void init() {
		super.init();
		
		worldWidth = getPacman().getWorldWidth();
		worldHeight = getPacman().getWorldHeight();
		
		setSize(worldWidth, worldHeight);
		setBounds(0, 0, getWidth(), getHeight());
		setPosition(0.0f, 0.0f);
		
		background.setPosition(0, 0);
		background.setSize(worldWidth, worldHeight);
		background.setBounds(0, 0, worldWidth, worldHeight);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		background.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}
}
