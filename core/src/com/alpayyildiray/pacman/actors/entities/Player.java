package com.alpayyildiray.pacman.actors.entities;

import java.util.LinkedList;
import java.util.Queue;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.PacmanActor;
import com.alpayyildiray.pacman.animations.PacmanAnimation;
import com.badlogic.gdx.Gdx;
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
	
	public enum Direction {
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

	private PacmanAnimation animation;
	private Sprite sprite;
	private Queue<Integer> inputQueue = new LinkedList<Integer>();
	
	private static float movingSpeed = 0.2f;
	private Direction facing = Direction.RIGHT;
	private Direction moving = Direction.RIGHT;
	private float tileSize;
	private float tickDelay = 0.05f;
	
	public class KeyEvent {
		
		private static final int inputHoldTime = 250;
		
		KeyEvent(final int keycode) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Integer integer = keycode;
						inputQueue.offer(integer);
						Thread.sleep(inputHoldTime);
						inputQueue.remove(integer);
					} catch(InterruptedException e) {}
				}
			}).start();
		}
	}
	
	public Player() {
		setType(Type.PLAYER);
		animation = new PacmanAnimation();
		TextureRegion texture = animation.getTextureRegion();
		sprite = new Sprite(texture);
		
		addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Input.Keys.ESCAPE) {
					getPacman().setLevel(0);
				}
				new KeyEvent(keycode);
				return true;
			}
		});
	}
	
	public void init() {
		super.init();
		
		tileSize = getParentStage().getTileSize();
		
		setSize(tileSize, tileSize);
//		setBounds(0, 0, getWidth(), getHeight());
		setPosition(0.0f, 0.0f);
		
//		sprite.setBounds(2, 2, tileSize-2, tileSize-2);
//		sprite.setOrigin(tileSize/2-1, tileSize/2-1);
//		sprite.setColor(1.0f, 1.0f, 0.0f, 1.0f);
		sprite.setBounds(2, 2, tileSize-2, tileSize-2);
		sprite.setOriginCenter();
		sprite.setColor(1.0f, 1.0f, 0.0f, 1.0f);
	}
	
	@Override
	protected void positionChanged() {
		sprite.setPosition(getX()+2f, getY()+2f);
		super.positionChanged();
	}
	
	@Override
	public void act(float deltaT) {
		handleKeyInput();
		
		if(touchesEnemy()) {
			getPacman().setLevel(0);
		}
		
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
				if(canMove(moving)) {
					facing = moving;
				} else {
					return;
				}
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
			if(eatsFood()) {
				PacmanActor food = getLocal();
//				food.setVisible(false);
				boolean removed = false;
				for(Actor each : getParentStage().getRoot().getChildren().toArray()) {
					PacmanActor actor = (PacmanActor)each;
					if(actor.removeActor(food)) {
						removed = true;
						break;
					}
				}
				if(removed == true) {
					getParentStage().setFoodCount(getParentStage().getFoodCount()-1);
				}
			}
		}
		
		sprite.setRegion(animation.getTextureRegion());
		if(moving.equals(facing)) {
			sprite.setRotation(facing.asFloat());
		}
		super.act(deltaT);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		sprite.draw(batch);
		super.draw(batch, parentAlpha);
	}
	
	private void handleKeyInput() {
		Integer[] inputs = new Integer[inputQueue.size()]; 
		inputs = inputQueue.toArray(inputs);
		for(Integer keycode : inputs) {
			try {
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
			} catch(NullPointerException e) {
				continue;
			}
		}
	}
	
	private boolean stopped() {
		return canMove(moving);
	}
	
	private boolean canMove(Direction d) {
//		float spriteX = sprite.getOriginX() + sprite.getX();
//		float spriteY = sprite.getOriginY() + sprite.getY();
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
	
	public boolean touchesEnemy() {
		try {
			return getLocal().getType() == Type.ENEMY;
		} catch(NullPointerException e) {
			return false;
		}
	}

	public boolean eatsFood() {
		boolean result =  getLocal().getType() == Type.FOOD;
		return result;
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
