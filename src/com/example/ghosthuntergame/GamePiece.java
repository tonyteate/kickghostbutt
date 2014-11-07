package com.example.ghosthuntergame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GamePiece {

	protected double xPosition;
	protected double yPosition;
	protected Bitmap image;
	protected Rect bounds;
	protected int id;

	public GamePiece(int id, double xP, double yP, Rect r, Bitmap sourceImage) {
		this.id = id;
		this.xPosition = xP;
		this.yPosition = yP;
		this.bounds = r;
		this.image = Bitmap.createBitmap(sourceImage, 0, 0, r.width(), r.height()); 
	}
	
	public void draw(Canvas c) {
		c.drawBitmap(this.image, (float) this.xPosition, (float) this.yPosition, null);
	}

	public double getxPosition() {
		return xPosition;
	}

	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}

	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public Rect getBounds() {
		return bounds;
	}

	public void setBounds(Rect bounds) {
		this.bounds = bounds;
	}

}
