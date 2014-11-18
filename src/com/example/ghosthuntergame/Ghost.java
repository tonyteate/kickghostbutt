package com.example.ghosthuntergame;

import android.graphics.Bitmap;
import android.graphics.Rect;

//want ghost 

public class Ghost extends GamePiece {

	public Ghost(int id, int xP, int yP, int height, int width,Bitmap sourceImage) {
		super(id, xP, yP, height, width, sourceImage);
		this.setxVelocity(1);
		this.setyVelocity(1);
	}



}
