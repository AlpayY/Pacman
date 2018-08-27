package com.alpayyildiray.pacman.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyAnimation {

	Animation<TextureRegion> animation;
	TextureRegion[] textures;
	
	private final int FRAME_COLS = 2;
	
	private volatile TextureRegion actualTexture;
	private volatile float stateTime = 0f;
	
	public EnemyAnimation() {
		Texture pacmanSheet = new Texture(Gdx.files.internal("Enemy-Sprite.png"));
		
		TextureRegion pacmanRegion = new TextureRegion(pacmanSheet);

		TextureRegion[][] tmp = pacmanRegion.split(pacmanSheet.getWidth() / FRAME_COLS, pacmanSheet.getHeight());

		textures = new TextureRegion[FRAME_COLS];
		int index = 0;
		for (TextureRegion tex : tmp[0]) {
			textures[index++] = tex;
		}
		
		actualTexture = textures[0];
	}
	
	public void act(float deltaT) {
		stateTime += deltaT;
		if(stateTime >= animation.getFrameDuration() * FRAME_COLS) {
			stateTime = deltaT;
		}
		actualTexture = animation.getKeyFrame(stateTime, true);
	}
	
	public void reset() {
		stateTime = 0f;
		act(0f);
	}
	
	public void animate(float duration) {
		actualTexture = textures[0];
		float frameDuration = duration / FRAME_COLS;
		animation = new Animation<TextureRegion>(0.1f, textures);
		animation.setFrameDuration((float)(Math.floor(frameDuration * 100) / 100)); // Could go into constructor
	}
	
	public TextureRegion getTextureRegion() {
		return actualTexture;
	}
}
