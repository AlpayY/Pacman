package com.alpayyildiray.pacman.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class PacmanActor extends Actor {
	
	public enum Type {
		NONE,
		PLAYER,
		ENEMY,
		WALL;
	}
	
	private Type type = Type.NONE;
	
	public void init() {
		return;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
}
