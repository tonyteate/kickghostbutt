package com.example.ghosthuntergame;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GamePiece extends OnScreenObject {

	
	protected int xVelocity;
	protected int yVelocity;
	
	public GamePiece(int id, int xP, int yP, int height, int width, Bitmap sourceImage) {
		super(id, xP, yP, height, width, sourceImage);
		this.xVelocity = 0;
		this.yVelocity = 0;
	}
	
	public void update() {
		this.xPosition += this.xVelocity;
		this.yPosition += this.yVelocity;
		
		this.bounds.set(this.xPosition-this.width/2, this.yPosition-this.height/2, this.xPosition+this.width/2,this.yPosition+this.height/2);
	}
	
	//position
	
	public double getxPosition() {
		return this.xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return this.yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
	//velocity
	
	public double getxVelocity() {
		return this.xVelocity;
	}

	public void setxVelocity(int xVelocity) {
		this.xVelocity = xVelocity;
	}

	public double getyVelocity() {
		return this.yVelocity;
	}

	public void setyVelocity(int yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	

	public Rect getBounds() {
		return bounds;
	}

	public void setBounds(Rect bounds) {
		this.bounds = bounds;
	}
	

}
