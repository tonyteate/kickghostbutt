package com.example.ghosthuntergame;

import android.graphics.Bitmap;

public class GameButton extends OnScreenObject {

	public GameButton(int id, int xP, int yP, int height, int width, Bitmap sourceImage) {
		super(id, xP, yP, height, width, sourceImage);
	}
	
	@Override
	public void respondToTouch(int x, int y) {
		
	}

}
