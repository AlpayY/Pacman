package com.alpayyildiray.pacman.actors.gameobjects;

import com.alpayyildiray.pacman.actors.PacmanActor;
import com.alpayyildiray.pacman.actors.PacmanActor.Type;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameFood extends PacmanActor {

	private Texture texture;
	private Sprite sprite;
	
	private float offset = 5f;
	private float tileSize = 0f;
	private float scaleFactor = 0.4f;
	
	public GameFood() {
		this(-100, 0);
	}
	
	public GameFood(float posX, float posY) {
		setType(Type.FOOD);
		setPosition(posX, posY);
		
		texture = new Texture(Gdx.files.internal("Food.png"));
		sprite = new Sprite(texture);
	}

	public void init() {
//		PacmanActor parent = this;
//		while(hasParent()) {
//			parent = (PacmanActor)parent.getParent();
//		}
//		GamegetParentStage() getParentStage() = (GamegetParentStage())parent.getgetParentStage()();
		super.init();
		
		tileSize = getParentStage().getTileSize();
		
		setSize(tileSize - offset*2, tileSize - offset*2);
		setBounds(getX(), getY(), tileSize - offset*2, tileSize - offset*2);
		sprite.setSize(tileSize*scaleFactor, tileSize*scaleFactor);
	}
	
	public void setPosition(int x, int y) {
		float posX = x * tileSize;
		float posY = y * tileSize;
		setPosition(posX + offset, posY + offset);
		sprite.setPosition(posX+tileSize/2-sprite.getWidth()/2, posY+tileSize/2-sprite.getHeight()/2);
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
