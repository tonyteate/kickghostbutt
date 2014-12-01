package com.example.ghosthuntergame;

import android.graphics.Bitmap;

public class Wall extends GamePiece {
	
	private boolean moving;
	private int initialVelocityX;
	private int initialVelocityY;
	private int xMax;
	private int xMin;
	private int yMax;
	private int yMin;
	
	public Wall(int id, int xP, int yP, int height, int width, Bitmap sourceImage, boolean moving, int initialVelocityX, int initialVelocityY, int xMin, int xMax, int yMin, int yMax) {
		super(id, xP, yP, height, width, sourceImage);
		this.moving = moving;
		this.initialVelocityX = initialVelocityX;
		this.initialVelocityY = initialVelocityY;
		this.xVelocity = this.initialVelocityX;
		this.yVelocity = this.initialVelocityY;
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	public Wall(int id, int xP, int yP, int height, int width, Bitmap sourceImage, boolean moving) {
		super(id, xP, yP, height, width, sourceImage);
		this.moving = moving;
		this.initialVelocityX = 0;
		this.initialVelocityY = 0;
		this.xVelocity = this.initialVelocityX;
		this.yVelocity = this.initialVelocityY;
		this.xMin = this.xPosition;
		this.xMax = this.xPosition;
		this.yMin = this.yPosition;
		this.yMax = this.yPosition;
	}
	
	@Override
	public void update() {
		
		if(this.moving) {
		
			//first time, set velocities to initial conditions
			if((this.xVelocity == 0)&&(this.yVelocity == 0)) {
				this.xVelocity = this.initialVelocityX;
				this.yVelocity = this.initialVelocityY;
			} else {
				if(this.xPosition <= this.xMin) {
					//velocity should be made positive (move right)
					if(this.initialVelocityX >= 0) {
						this.xVelocity = this.initialVelocityX;
					} else {
						this.xVelocity = -1*this.initialVelocityX;
					}
				}
				if(this.xPosition >= this.xMax) {
					//velocity should be made negative (move left)
					if(this.initialVelocityX <= 0) {
						this.xVelocity = this.initialVelocityX;
					} else {
						this.xVelocity = -1*this.initialVelocityX;
					}
				}
				if(this.yPosition <= this.yMin) {
					//velocity should be made positive (move up)
					if(this.initialVelocityY >= 0) {
						this.yVelocity = this.initialVelocityY;
					} else {
						this.yVelocity = -1*this.initialVelocityY;
					}
				}
				if(this.yPosition >= this.yMax) {
					//velocity should be made negative (move down)
					if(this.initialVelocityY <= 0) {
						this.yVelocity = this.initialVelocityY;
					} else {
						this.yVelocity = -1*this.initialVelocityY;
					}
				}
			}
			
			this.xPosition += this.xVelocity;
			this.yPosition += this.yVelocity;
		
			this.bounds.set(this.xPosition-this.width/2, this.yPosition-this.height/2, this.xPosition+this.width/2,this.yPosition+this.height/2);
		
		}
	}
	
}