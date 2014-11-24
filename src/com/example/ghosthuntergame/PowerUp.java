package com.example.ghosthuntergame;

import android.graphics.Bitmap;

public class PowerUp extends GamePiece {

	private powerUpType type;
	
	
	public PowerUp(powerUpType type, int id, int xP, int yP, int height, int width, Bitmap sourceImage) {
		super(id, xP, yP, height, width, sourceImage);
		this.type = type;
	}
	
	public powerUpType getType() {
		return this.type;
	}
	
	public static enum powerUpType{BOMB, HEALTH, COIN}

}
