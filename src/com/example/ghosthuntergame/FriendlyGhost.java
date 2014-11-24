package com.example.ghosthuntergame;

import android.graphics.Bitmap;

public class FriendlyGhost extends GamePiece {

	public FriendlyGhost(int id, int xP, int yP, int height, int width,
			Bitmap sourceImage) {
		super(id, xP, yP, height, width, sourceImage);
		this.setxVelocity(0);
		this.setyVelocity(1);
	}



}
