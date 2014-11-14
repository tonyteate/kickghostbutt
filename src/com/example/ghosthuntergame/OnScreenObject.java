package com.example.ghosthuntergame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


public class OnScreenObject {
	
	protected int xPosition;
	protected int yPosition;
	protected Bitmap image;
	protected Rect bounds;
	protected int id;

	public OnScreenObject(int id, int xP, int yP, int height, int width, Bitmap sourceImage) {
		this.id = id;
		this.xPosition = xP;
		this.yPosition = yP;
		this.bounds = new Rect(this.xPosition-width/2, this.yPosition-width/2, this.xPosition+width/2,this.yPosition+width/2);
		this.image = Bitmap.createBitmap(sourceImage, 0, 0, width, height);
	}

	public void draw(Canvas c) {
		c.drawBitmap(this.image, this.bounds, this.bounds, null);
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
	
	public void update() {
		
	}
	
	public void respondToTouch(int x, int y) {
		
	}

	
}