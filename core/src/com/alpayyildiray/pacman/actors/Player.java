package com.alpayyildiray.pacman.actors;

import com.alpayyildiray.pacman.Pacman;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Player extends Actor {
	
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
	
	public Player(Pacman pacman) {
		this.pacman = pacman;
		tileSize = pacman.getTileSize();
		
		setSize(tileSize, tileSize);
		setOrigin(tileSize/2, tileSize/2);
		setBounds(0, 0, getWidth(), getHeight());
		setPosition(0.0f, 0.0f);
		
		animation = new PacmanAnimation();
//		Texture texture = new Texture(Gdx.files.internal("player.png"));
		TextureRegion texture = animation.getTextureRegion();
		sprite = new Sprite(texture);
		sprite.setSize(tileSize, tileSize);
		sprite.setBounds(0, 0, tileSize, tileSize);
		sprite.setOrigin(getOriginX(), getOriginY());
		sprite.setColor(1.0f, 1.0f, 0.0f, 1.0f);
		
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
		float potentialUp = getY() + tileSize;
		float potentialRight = getX() + tileSize;
		float potentialDown = getY();
		float potentialLeft = getX();
		
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
				if(potentialDown > 1) {
					return true;
				}
				break;
			case LEFT:
				if(potentialLeft > 1) {
					return true;
				}
				break;
		}
		
		return false;
	}
}
