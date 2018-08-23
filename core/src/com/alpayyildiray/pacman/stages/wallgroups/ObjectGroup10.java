package com.alpayyildiray.pacman.stages.wallgroups;

import com.alpayyildiray.pacman.actors.entities.Player;

public class ObjectGroup10 extends PacmanObjectGroup {

	private Player player;
	
	public ObjectGroup10() {
		super();
		int[][] wallGridHalf = {
				{0,0,0,0,1,1,0,0,0,0},
				{0,1,1,0,0,0,0,1,0,1},
				{0,1,0,0,1,1,1,1,0,0},
				{0,1,0,1,0,0,0,0,0,1},
				{0,0,0,1,0,1,1,1,0,0},
				{1,1,0,1,0,0,0,0,0,1},
				{0,0,0,0,0,1,1,0,1,1},
				{0,1,1,1,0,0,0,0,0,0},
				{0,1,0,0,0,1,1,0,1,2},
				{0,1,0,1,0,0,1,0,1,3},
				{0,1,0,1,1,0,1,0,1,3},
				{0,0,0,0,0,0,1,0,1,3},
				{0,1,1,1,1,0,0,0,0,1},
				{0,0,0,0,0,1,0,1,0,0},
				{1,1,0,1,0,0,0,0,1,1},
				{0,0,0,1,0,1,1,0,0,0},
				{0,1,0,1,0,0,0,0,1,1},
				{0,1,0,0,1,1,1,0,0,0},
				{0,1,1,0,0,0,0,1,0,1},
				{0,0,0,0,1,1,0,0,0,0},
		};
		int[][] wallGroup = mirrorHalfGroup(wallGridHalf);
		wallGroup[0][0] = 3;
		setWallArray(wallGroup);
	}
	
	@Override
	public void init() {
		super.init();
	}
}
