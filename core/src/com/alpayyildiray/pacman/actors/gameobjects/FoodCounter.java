package com.alpayyildiray.pacman.actors.gameobjects;

import com.alpayyildiray.pacman.actors.PacmanActor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class FoodCounter extends PacmanActor {

	private BitmapFont font;
	private GlyphLayout layout;
	
	private int barHeight = 0;
	private int posX = 0;
	private int posY = 0;
	
	public FoodCounter(int posX, int posY) {
		font = new BitmapFont();
		layout = new GlyphLayout(font, "");
		this.posX = posX;
		this.posY = posY;
	}
	
	@Override
	public void init() {
		super.init();
		barHeight = (int)(getPacman().getTotalHeight() - getPacman().getWorldHeight());
		posY = (int)(posY + getPacman().getWorldHeight() + layout.height + barHeight/10);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		font.draw(batch, "REMAINING FOOD: " + getParentStage().getFoodCount(), posX, posY);
		super.draw(batch, parentAlpha);
	}
}
