package com.alpayyildiray.pacman.actors.entities;

import java.util.LinkedList;
import java.util.Queue;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.PacmanActor;
import com.alpayyildiray.pacman.animations.PacmanAnimation;
import com.alpayyildiray.pacman.stages.GameStage;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
	private GameStage stage;
	private PacmanAnimation animation;
	private Sprite sprite;
	private Queue<Integer> inputQueue = new LinkedList<Integer>();
	
	private static final float movingSpeed = 0.2f;
	private static final int inputHoldTime = 250;
	
	private Direction facing = Direction.RIGHT;
	private Direction moving = Direction.RIGHT;
	private float tileSize;
	private float tickDelay = 0.05f;
	
	public class KeyEvent {
		KeyEvent(int keycode) {
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
				new KeyEvent(keycode);
				return true;
			}
		});
	}
	
	public void init() {
		this.stage = (GameStage)getStage();
		this.pacman = stage.getPacman();
		
		tileSize = stage.getTileSize();
		
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
		handleKeyInput();
		
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
				food.setVisible(false);
				stage.setFoodCount(stage.getFoodCount()-1);
			}
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
		float spriteX = sprite.getOriginX() + sprite.getX();
		float spriteY = sprite.getOriginY() + sprite.getY();
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
				if(potentialUp - 1 < pacman.getWorldHeight() - tileSize/2) {
					return true;
				}
				break;
			case RIGHT:
				if(potentialRight - 1 < pacman.getWorldWidth() - tileSize/2) {
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
	
	public boolean eatsFood() {
		return getLocal().getType() == Type.FOOD;
	}
	
	public PacmanActor getLocal() {
		float spriteX = sprite.getOriginX() + sprite.getX();
		float spriteY = sprite.getOriginY() + sprite.getY();
		
		Vector2 vector = new Vector2(spriteX, spriteY);
		return getObjectAt(vector);
	}
	
	private PacmanActor getObjectAt(Vector2 vector) {
		return (PacmanActor)(getStage().hit(vector.x, vector.y, false));
	}
}
