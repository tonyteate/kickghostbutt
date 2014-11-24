
package com.example.ghosthuntergame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


public class OnScreenObject {
	
	protected int xPosition;
	protected int yPosition;
	protected int width;
	protected int height;
	protected Bitmap image;
	protected Rect bounds;
	protected int id;

	public OnScreenObject(int id, int xP, int yP, int height, int width, Bitmap sourceImage) {
		this.id = id;
		this.xPosition = xP;
		this.yPosition = yP;
		this.width = width;
		this.height = height;
		this.bounds = new Rect(this.xPosition-width/2, this.yPosition-height/2, this.xPosition+width/2,this.yPosition+height/2);
		this.image = Bitmap.createScaledBitmap(sourceImage, width, height, true);
	}

	public void draw(Canvas c) {
		c.drawBitmap(this.image, null, this.bounds, null);
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
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public Bitmap getBitmap() {
		return this.image;
	}
	
	public void setBitmap(Bitmap newImage) {
		this.image = newImage;
	}
	
	public void update() {
		
	}
	
	public void respondToTouch(int x, int y) {
		
	}

	
}

