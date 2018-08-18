package com.alpayyildiray.pacman.actors.gameobjects;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.PacmanActor;
import com.alpayyildiray.pacman.stages.GameStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class MenuButton extends PacmanActor {

	TextureRegion[] textures;
	
	private final int FRAME_COLS = 2;
	
	private Sprite sprite;
	private TextureRegion actualTexture;
	
	private String text = "";
	private float positionX = 0f;
	private float positionY = 0f;
	private float sizeX = 0f;
	private float sizeY = 0f;
	private float margin = 30f;
	
	public MenuButton(float posX, float posY, String text, Runnable action) {
		this.positionX = posX;
		this.positionY = posY;
		this.text = text;
		
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
					action.run();
				}
				return true;
			}
		});
		
		sprite = new Sprite(actualTexture);
	}
	
	public void init() {
		Pacman pacman = ((GameStage)getStage()).getPacman();
		
		this.sizeX = pacman.getWorldWidth() / 3;
		this.sizeY = pacman.getWorldHeight() / 9;
		margin = pacman.getWorldWidth()/10;
		
		setSize(sizeX + margin*2, sizeY + margin*2);
		setBounds(0, 0, getWidth(), getHeight());
		setPosition(positionX - margin, positionY - margin);
		
		sprite.setSize(sizeX, sizeY);
		sprite.setOriginCenter();
		sprite.setPosition(positionX, positionY);
	}

	@Override
	public void act(float delta) {
		sprite.setRegion(actualTexture);
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}
	
	private boolean onButton(float x, float y) {
		float leftBorder = margin;
		float rightBorder = sizeX + margin;
		float lowerBorder = margin;
		float upperBorder = sizeY + margin;
		
		if(x >= leftBorder && x <= rightBorder && y >= lowerBorder && y <= upperBorder) {
			return true;
		} else {
			return false;
		}
	}
}
