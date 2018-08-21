package com.alpayyildiray.pacman.stages.wallgroups;

import java.awt.IllegalComponentStateException;
import java.io.ObjectInputStream.GetField;

import com.alpayyildiray.pacman.actors.PacmanActor;
import com.alpayyildiray.pacman.actors.gameobjects.GameFood;
import com.alpayyildiray.pacman.actors.gameobjects.GameWall;
import com.alpayyildiray.pacman.stages.GameStage;

public class PacmanObjectGroup extends PacmanActor {

	private int[][] wallArray;
	private GameWall[][] gameWallArray;
	private GameFood[][] gameFoodArray;
	
	public PacmanObjectGroup() {
		super();
	}
	
	@Override
	public void init() {
		if(wallArray != null) {
			try {
				initWallGroup();
			} catch(IllegalComponentStateException e) {
				throw new RuntimeException("Illegal object handling in ObjectGroup!");
			}
		}
		super.init();
	}
	
	public void setWallArray(int[][] walls) {
		wallArray = walls;
	}
	
	public int[][] mirrorHalfGroup(int[][] halfGroup) {
		int xTiles = halfGroup[0].length * 2;
		int yTiles = halfGroup.length;
		
		int[][] wallGrid = new int[xTiles][yTiles];
		for(int a = yTiles-1; a >= 0; --a) {
			for(int b = 0; b < xTiles; ++b) {
				if(b-xTiles/2 < 0) {
					wallGrid[b][(yTiles-1)-a] = halfGroup[a][xTiles/2-Math.abs(b-xTiles/2)];
				} else {
					wallGrid[b][(yTiles-1)-a] = halfGroup[a][(xTiles/2-1)-(b-xTiles/2)];					
				}
			}
		}
		
		return wallGrid;
	}
	
	public GameWall[][] getWallArray() {
		return gameWallArray;
	}
	
	private void initWallGroup() {
		clearChildren();
		if(wallArray == null) {
			throw new IllegalComponentStateException("Wall array not initilized!");
		}
		if(getStage() == null) {
			throw new IllegalStateException("Group not part of a stage yet!");
		}
		int xTiles = wallArray.length;
		int yTiles = wallArray[0].length;
		
		GameStage stage = ((GameStage)getStage());
		gameWallArray = new GameWall[xTiles][yTiles];
		gameFoodArray = new GameFood[xTiles][yTiles];
		for(int a = 0; a < xTiles; a++) {
			for(int b = 0; b < yTiles; b++) {
				switch(wallArray[a][b]) {
					case 0:
						gameFoodArray[a][b] = new GameFood();
						stage.setFoodCount(stage.getFoodCount());
						break;
					case 1:
					case 2:
						gameWallArray[a][b] = new GameWall();
						break;
				}
				if(wallArray[a][b] == 0) {
					
				} else if(wallArray[a][b] == 1) {
					
				}
			}
		}
		
		for(int a = 0; a < xTiles; a++) {
			for(int b = 0; b < yTiles; b++) {
				switch(wallArray[a][b]) {
					case 0:
						addActor(gameFoodArray[a][b]);
						gameFoodArray[a][b].init();
						gameFoodArray[a][b].setPosition(a, b);
						break;
					case 1:
					case 2:
						addActor(gameWallArray[a][b]);
						gameWallArray[a][b].init();
						gameWallArray[a][b].setPosition(a, b);
						if(wallArray[a][b] == 2) {
							gameWallArray[a][b].setVisible(false);
						}
						break;
				}
			}
		}
	}
}
