package com.alpayyildiray.pacman.actors;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.stages.GameStage;
import com.badlogic.gdx.scenes.scene2d.Group;

public class PacmanActor extends Group {
	
	public enum Type {
		NONE,
		PLAYER,
		FOOD,
		ENEMY,
		WALL;
	}
	
	private Type type = Type.NONE;
	
	private PacmanActor highestParent;
	private GameStage stage;
	private Pacman pacman;
	
	public void init() {
		try {
			Group parent = this;
			while(parent.getParent() != null) {
				if(parent.getParent().getParent() != null) {
					parent = (PacmanActor)parent.getParent();
				} else {
					break;
				}
			}
			this.highestParent = (PacmanActor)parent;
		} catch(NullPointerException e) {
			System.out.println("WARNING: A PacmanActor as been initialized without a stage!");
		}
	}
	
	public PacmanActor getParent() {
		return highestParent;
	}
	
	public GameStage getParentStage() {
		if(highestParent == this) {
			return (GameStage)super.getStage();
		}
		return (GameStage)highestParent.getStage();
	}
	
	public Pacman getPacman() {
		return getParentStage().getPacman();
	}
	
	public void setParentStage(GameStage stage) {
		this.stage = stage;
	}
	
	public void setPacman(Pacman pacman) {
		this.pacman = pacman;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
}
