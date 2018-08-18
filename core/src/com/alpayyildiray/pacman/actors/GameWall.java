package com.alpayyildiray.pacman.actors;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.stages.GameStage;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameWall extends Actor {
	
	private float offset = 5f;
	private float tileSize;
	
	public GameWall(float posX, float posY) {
		Pacman pacman = ((GameStage)getStage()).getPacman();
		tileSize = pacman.getTileSize();
		
		setSize(tileSize, tileSize);
		setBounds(offset, offset, tileSize - offset, tileSize - offset);
		setPosition(posX, posY);
	}
}
