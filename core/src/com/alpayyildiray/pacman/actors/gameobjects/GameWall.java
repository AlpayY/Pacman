package com.alpayyildiray.pacman.actors.gameobjects;

import com.alpayyildiray.pacman.Pacman;
import com.alpayyildiray.pacman.actors.PacmanActor;
import com.alpayyildiray.pacman.actors.PacmanActor.Type;
import com.alpayyildiray.pacman.stages.GameStage;

public class GameWall extends PacmanActor {
	
	private float offset = 5f;
	private float tileSize;
	
	public GameWall(float posX, float posY) {
		setType(Type.WALL);
		setPosition(posX, posY);
	}
	
	public void init() {
		Pacman pacman = ((GameStage)getStage()).getPacman();
		
		tileSize = pacman.getTileSize();
		
		setSize(tileSize, tileSize);
		setBounds(offset, offset, tileSize - offset, tileSize - offset);
	}
}
