package com.alpayyildiray.pacman.actors.gameobjects;

import com.alpayyildiray.pacman.actors.PacmanActor;
import com.alpayyildiray.pacman.stages.GameStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameWall extends PacmanActor {
	
	private Texture texture;
	private Sprite sprite;
	
	private float offset = 5f;
	private float tileSize = 0f;
	
	public GameWall() {
		this(-100, 0);
	}
	
	public GameWall(float posX, float posY) {
		setType(Type.WALL);
		setPosition(posX, posY);
		
		texture = new Texture(Gdx.files.internal("Wall.png"));
		sprite = new Sprite(texture);
	}
	
	public void init() {
		GameStage stage = (GameStage)getStage();
		
		tileSize = stage.getTileSize();
		
//		setSize(tileSize - offset*2, tileSize - offset*2);
		setBounds(getX()+offset, getY()+offset, tileSize - offset, tileSize - offset);
		sprite.setSize(tileSize+1.5f, tileSize+1.5f);
	}
	
	public void setPosition(int x, int y) {
		float posX = x * tileSize;
		float posY = y * tileSize;
		setPosition(posX+offset, posY+offset);
		sprite.setPosition(posX, posY);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}
	
	@Override
	public void setVisible(boolean visible) {
		sprite.setAlpha(0f);
		super.setVisible(true);
	}
}
