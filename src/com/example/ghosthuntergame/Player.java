package com.example.ghosthuntergame;

import android.graphics.Bitmap;

public class Player extends GamePiece {
	
	protected int health;
	protected int score;
	
	public Player(int id, int xP, int yP, int height, int width, Bitmap sourceImage) {
		super(id, xP, yP, height, width, sourceImage);
		this.health = 100;
		this.score = 0;
		// TODO Auto-generated constructor stub
	}
	
}
