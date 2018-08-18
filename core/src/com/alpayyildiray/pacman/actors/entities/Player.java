package com.alpayyildiray.pacman.actors.entities;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.PacmanActor;
import com.alpayyildiray.pacman.animations.PacmanAnimation;
import com.alpayyildiray.pacman.stages.GameStage;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Player extends PacmanActor {
	
	private enum Direction {
		UP(180.0f),
		RIGHT(90.0f),
		DOWN(0.0f),
		LEFT(270.0f);
		
		private float dir;
		Direction(float ection) {
			dir = ection;
		}
		
		public float asFloat() {
			return dir;
		}
	}	

	private Pacman pacman;
	private PacmanAnimation animation;
	private Sprite sprite;
	
	private static float movingSpeed = 0.2f;
	
	private Direction facing = Direction.RIGHT;
	private Direction moving = Direction.RIGHT;
	private float tileSize;
	private float tickDelay = 0.05f;
	
	public Player() {
		setType(Type.PLAYER);
		animation = new PacmanAnimation();
		TextureRegion texture = animation.getTextureRegion();
		sprite = new Sprite(texture);
		
		addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				switch(keycode) {
					case Input.Keys.UP: {
						if(canMove(Direction.UP)) {
							facing = Direction.UP;
						}
						break;
					}
					case Input.Keys.RIGHT: {
						if(canMove(Direction.RIGHT)) {
							facing = Direction.RIGHT;
						}
						break;
					}
					case Input.Keys.DOWN: {
						if(canMove(Direction.DOWN)) {
							facing = Direction.DOWN;
						}
						break;
					}
					case Input.Keys.LEFT: {
						if(canMove(Direction.LEFT)) {
							facing = Direction.LEFT;
						}
						break;
					}
				}
				return true;
			}
		});
	}
	
	public void init() {
		this.pacman = ((GameStage)getStage()).getPacman();
		
		tileSize = pacman.getTileSize();
		
		setSize(tileSize, tileSize);
		setBounds(0, 0, getWidth(), getHeight());
		setPosition(0.0f, 0.0f);
		
		sprite.setSize(tileSize, tileSize);
		sprite.setBounds(0, 0, tileSize, tileSize);
		sprite.setOrigin(tileSize/2, tileSize/2);
		sprite.setColor(1.0f, 1.0f, 0.0f, 1.0f);
	}
	
	@Override
	protected void positionChanged() {
		sprite.setPosition(getX(), getY());
		super.positionChanged();
	}
	
	@Override
	public void act(float deltaT) {
		if(!hasActions()) {
			if(tickDelay > 0.0f) {
				tickDelay = 0.0f;
				return;
			}
			float adjustedSpeed = movingSpeed - deltaT;
			if(deltaT > movingSpeed) {
				adjustedSpeed = 0.0f;
			}
			if(!canMove(facing)) {
				return;
			}
			switch(facing) {
				case UP: {
					addAction(Actions.moveBy(0.0f, tileSize, adjustedSpeed));
					animation.animate(adjustedSpeed);
					moving = facing;
					break;
				}
				case RIGHT: {
					addAction(Actions.moveBy(tileSize, 0.0f, adjustedSpeed));
					animation.animate(adjustedSpeed);
					moving = facing;
					break;
				}
				case DOWN: {
					addAction(Actions.moveBy(0.0f, -tileSize, adjustedSpeed));
					animation.animate(adjustedSpeed);
					moving = facing;
					break;
				}
				case LEFT: {
					addAction(Actions.moveBy(-tileSize, 0.0f, adjustedSpeed));
					animation.animate(adjustedSpeed);
					moving = facing;
					break;
				}
			}
			if(stopped()) {
				animation.reset();
			}
		} else {
			tickDelay = 0.05f;
			animation.act(deltaT);
		}
		super.act(deltaT);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setRegion(animation.getTextureRegion());
		if(moving.equals(facing)) {
			sprite.setRotation(facing.asFloat());
		}
		sprite.draw(batch);
		super.draw(batch, parentAlpha);
	}
	
	private boolean stopped() {
		return canMove(moving);
	}
	
	private boolean canMove(Direction d) {
		float potentialUp = sprite.getY() + tileSize;
		float potentialRight = sprite.getX() + tileSize;
		float potentialDown = sprite.getY() - tileSize;
		float potentialLeft = sprite.getX() - tileSize;
		
		Vector2 vector = getStage().screenToStageCoordinates(new Vector2(sprite.getX(), sprite.getY()));
		
		switch(d) {
			case UP:
				vector = getStage().screenToStageCoordinates(new Vector2(potentialUp, sprite.getY()));
				break;
			case RIGHT:
				vector = getStage().screenToStageCoordinates(new Vector2(sprite.getX(), potentialLeft));
				break;
			case DOWN:
				vector = getStage().screenToStageCoordinates(new Vector2(potentialDown, sprite.getY()));
				break;
			case LEFT:
				vector = getStage().screenToStageCoordinates(new Vector2(sprite.getX(), potentialLeft));
				break;
		}
		if(isWall(vector)) {
			return false;
		}
		
		switch(d) {
			case UP:
				if(potentialUp - 1 < pacman.getWorldHeight() - tileSize) {
					return true;
				}
				break;
			case RIGHT:
				if(potentialRight - 1 < pacman.getWorldWidth() - tileSize) {
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
			PacmanActor actor = (PacmanActor)(getStage().hit(vector.x, vector.y, false));
			return actor.getType() == Type.WALL;
		} catch(NullPointerException e) {
			return false;
		}
	}
}
