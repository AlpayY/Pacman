package com.alpayyildiray.pacman.actors.entities;

import java.util.Random;

import com.alpayyildiray.pacman.actors.PacmanActor;
import com.alpayyildiray.pacman.actors.PacmanActor.Type;
import com.alpayyildiray.pacman.actors.entities.Player.Direction;
import com.alpayyildiray.pacman.animations.EnemyAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Enemy extends PacmanActor {

	private EnemyAnimation animation;
	private Sprite sprite;
	
	private static float movingSpeed = 0.35f;
	private Direction facing = Direction.RIGHT;
	private float initialPosX = 400f;
	private float initialPosY = 400f;
	private float tileSize;
	private float tickDelay = 0.05f;
	private Color color;
	private Color[] colorRange = {
			Color.BLUE,
			Color.BROWN,
			Color.GREEN,
			Color.ORANGE,
			Color.PINK,
			Color.PURPLE,
			Color.RED,
			Color.YELLOW
	};
	
	public Enemy() {
		setType(Type.ENEMY);
		animation = new EnemyAnimation();
		TextureRegion texture = animation.getTextureRegion();
		sprite = new Sprite(texture);
		int randomIndex = Math.abs((new Random()).nextInt()) % colorRange.length;
		color = colorRange[randomIndex];
	}

	public void init() {
		super.init();
		
		tileSize = getParentStage().getTileSize();
		
		setSize(tileSize, tileSize);
//		setPosition(initialPosX, initialPosY);
		
		sprite.setBounds(2, 2, tileSize-2, tileSize-2);
		sprite.setOrigin(tileSize/2-1, tileSize/2-1);
		sprite.setColor(color);
		generateInitialPosition(getParentStage().getTilesX(), getParentStage().getTilesY(), getParentStage().getTileSize());
	}
	
	public void generateInitialPosition(int xTiles, int yTiles, float tileSize) {
		float posX = (new Random().nextInt(xTiles / 2) * 2) * tileSize;
		float posY = (new Random().nextInt(yTiles / 2) * 2) * tileSize;
		setPosition(posX, posY);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch);
		super.draw(batch, parentAlpha);
	}
	
	@Override
	protected void positionChanged() {
		sprite.setPosition(getX()+2f, getY()+2f);
		super.positionChanged();
	}
	
	@Override
	public void act(float deltaT) {
		super.act(deltaT);
		
		if(!hasActions()) {
			if(tickDelay > 0.0f) {
				tickDelay = 0.0f;
				return;
			}
			float adjustedSpeed = movingSpeed - deltaT;
			if(deltaT > movingSpeed) {
				adjustedSpeed = 0.0f;
			}
			
			if(atJunction()) {
				if(new Random().nextInt(3) == 0 || !canMove(facing)) {
					do {
						facing = Direction.values()[new Random().nextInt(Direction.values().length)];
					} while(!canMove(facing));
				}
			}
			
			try {
				facing = playerInSight();
			} catch(RuntimeException e) {}
			
			switch(facing) {
				case UP: {
					addAction(Actions.moveBy(0.0f, tileSize, adjustedSpeed));
					break;
				}
				case RIGHT: {
					addAction(Actions.moveBy(tileSize, 0.0f, adjustedSpeed));
					break;
				}
				case DOWN: {
					addAction(Actions.moveBy(0.0f, -tileSize, adjustedSpeed));
					break;
				}
				case LEFT: {
					addAction(Actions.moveBy(-tileSize, 0.0f, adjustedSpeed));
					break;
				}
			}
			animation.animate(adjustedSpeed);
		} else {
			tickDelay = 0.05f;
			animation.act(deltaT);
		}
		
		sprite.setRegion(animation.getTextureRegion());
//		sprite.setRotation(facing.asFloat());
		super.act(deltaT);
	}
	
	private boolean atJunction() {
		for(int i = 0; i < 2; ++i) {
			int mod = 1 - 2*i;
			float interval = 90f * mod;
			int turned = (int)(facing.asFloat() + interval) % 360;
			if(turned < 0) {
				turned = 360 + turned;
			}
			Direction dir = facing;
			if(Direction.UP.asFloat() == turned) {
				dir = Direction.UP;
			}
			if(Direction.DOWN.asFloat() == turned) {
				dir = Direction.DOWN;
			}
			if(Direction.LEFT.asFloat() == turned) {
				dir = Direction.LEFT;
			}
			if(Direction.RIGHT.asFloat() == turned) {
				dir = Direction.RIGHT;
			}
			if(canMove(dir)) {
				return true;
			}
		}
		return false;
	}
	
	private Direction playerInSight() {
		for(Direction dir : Direction.values()) {
			float tX = tileSize/2 + getX();
			float tY = tileSize/2 + getY();
			while(tX < getPacman().getWorldWidth() && tX >= 0 && tY < getPacman().getTotalHeight() && tY >= 0) {
				Vector2 vector = new Vector2(tX, tY);
				Type objectType = getObjectAt(vector).getType(); 
				if(objectType == Type.PLAYER) {
					return dir;
				}
				if(objectType == Type.WALL) {
					break;
				}
				switch(dir) {
					case UP:
						tY += getParentStage().getTileSize();
						break;
					case DOWN:
						tY += getParentStage().getTileSize();
						break;
					case LEFT:
						tX -= getParentStage().getTileSize();
						break;
					case RIGHT:
						tX += getParentStage().getTileSize();
						break;
				}
			}
		}
		throw new RuntimeException();
	}
	
	private boolean canMove(Direction d) {
		float spriteX = tileSize/2 + getX();
		float spriteY = tileSize/2 + getY();
		float potentialUp = spriteY + tileSize;
		float potentialRight = spriteX + tileSize;
		float potentialDown = spriteY - tileSize;
		float potentialLeft = spriteX - tileSize;
		
		Vector2 vector = new Vector2(spriteX, spriteY);
		
		switch(d) {
			case UP:
				vector = new Vector2(spriteX, potentialUp);
				break;
			case RIGHT:
				vector = new Vector2(potentialRight, spriteY);
				break;
			case DOWN:
				vector = new Vector2(spriteX, potentialDown);
				break;
			case LEFT:
				vector = new Vector2(potentialLeft, spriteY);
				break;
		}
		if(isWall(vector)) {
			return false;
		}
		
		switch(d) {
			case UP:
				if(potentialUp - 1 < getPacman().getWorldHeight() - tileSize/2) {
					return true;
				}
				break;
			case RIGHT:
				if(potentialRight - 1 < getPacman().getWorldWidth() - tileSize/2) {
					return true;
				}
				break;
			case DOWN:
				if(potentialDown > -1) {
					return true;
				}
				break;
			case LEFT:
				if(potentialLeft > -1) {
					return true;
				}
				break;
		}
		return false;
	}
	
	public boolean isWall(Vector2 vector) {
		try {
			PacmanActor actor = getObjectAt(vector);
			return actor.getType() == Type.WALL;
		} catch(NullPointerException e) {
			return false;
		}
	}
	
	public PacmanActor getLocal() {
		float spriteX = sprite.getOriginX() + sprite.getX();
		float spriteY = sprite.getOriginY() + sprite.getY();
		
		Vector2 vector = new Vector2(spriteX, spriteY);
		toBack();
		PacmanActor actor = getObjectAt(vector);
		toFront();
		return actor;
	}
	
	private PacmanActor getObjectAt(Vector2 vector) {
		return (PacmanActor)(getParentStage().hit(vector.x, vector.y, false));
	}
}
