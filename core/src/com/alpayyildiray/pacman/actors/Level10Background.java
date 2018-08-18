package com.alpayyildiray.pacman.actors;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.stages.GameStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Level10Background extends Actor {
	
	private Sprite background;
	private float worldWidth;
	private float worldHeight;

	public Level10Background() {
		Pacman pacman = ((GameStage)getStage()).getPacman();
		
		worldWidth = pacman.getWorldWidth();
		worldHeight = pacman.getWorldHeight();
		
		setSize(worldWidth, worldHeight);
		setBounds(0, 0, getWidth(), getHeight());
		setPosition(0.0f, 0.0f);
		
		background = new Sprite(new Texture(Gdx.files.internal("Level10.png")));
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
