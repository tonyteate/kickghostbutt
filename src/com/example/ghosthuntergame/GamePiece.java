package com.example.ghosthuntergame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GamePiece {

	protected int xPosition;
	protected int yPosition;
	protected int xVelocity;
	protected int yVelocity;
	protected Bitmap image;
	protected Rect bounds;
	protected int id;

	public GamePiece(int id, int xP, int yP, int height, int width, Bitmap sourceImage) {
		this.id = id;
		this.xPosition = xP;
		this.yPosition = yP;
		this.bounds = new Rect(this.xPosition-width/2, this.yPosition-width/2, this.xPosition+width/2,this.yPosition+width/2);
		this.image = Bitmap.createBitmap(sourceImage, 0, 0, width, height);
		this.xVelocity = 0;
		this.yVelocity = 0;
	}
	
	public void draw(Canvas c) {
		c.drawBitmap(this.image, this.bounds, this.bounds, null);
	}
	
	public void update() {
		this.xPosition += this.xVelocity;
		this.yPosition += this.yVelocity;
	}
	
	public double getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public Rect getBounds() {
		return bounds;
	}

	public void setBounds(Rect bounds) {
		this.bounds = bounds;
	}

}
