package com.alpayyildiray.pacman.actors;

import com.alpayyildiray.pacman.Pacman;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class MenuButton extends Actor {

	TextureRegion[] textures;
	
	private final int FRAME_COLS = 2;
	
	private Sprite sprite;
	private TextureRegion actualTexture;
	
	private float positionX = 0f;
	private float positionY = 0f;
	private float sizeX = 300f;
	private float sizeY = sizeX/3;
	
	public MenuButton(Pacman pacman, int posX, int posY) {
		this.positionX = posX;
		this.positionX = posY;
		
		setSize(pacman.getWorldHeight(), pacman.getWorldWidth());
		
		Texture buttonSheet = new Texture(Gdx.files.internal("MenuButton-Sprite.png"));
		TextureRegion pacmanRegion = new TextureRegion(buttonSheet);
		TextureRegion[][] tmp = pacmanRegion.split(buttonSheet.getWidth() / FRAME_COLS, buttonSheet.getHeight());

		textures = new TextureRegion[FRAME_COLS];
		int index = 0;
		for (TextureRegion tex : tmp[0]) {
			textures[index++] = tex;
		}
		
		actualTexture = textures[0];
		
		addListener(new InputListener() {
			@Override
//			@SuppressWarnings("unused")
			public boolean mouseMoved(InputEvent event, float x, float y) {
				if(onButton(x, y)) {
					actualTexture = textures[1];
				} else {
					actualTexture = textures[0];
				}
				return true;
			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(onButton(x, y)) {
					pacman.levelUp();
				}
				return true;
			}
		});
		
		sprite = new Sprite(actualTexture);
	}

	@Override
	public void act(float delta) {
		sprite.setSize(sizeX, sizeY);
		sprite.setPosition(positionX, positionY);
		sprite.setRegion(actualTexture);
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}
	
	private boolean onButton(float x, float y) {
		if(x >= positionX && x <= positionX + sizeX && y >= positionY && y <= positionY + sizeY) {
			return true;
		} else {
			return false;
		}
	}
}
