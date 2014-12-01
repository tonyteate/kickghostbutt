package com.example.ghosthuntergame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class HealthBar extends OnScreenObject {
	
	double changeInWidth;
	double currentLength;
	double maxWidth;
	double maxPlayerHealth;
	Player player;
	Bitmap backgroundImage;
	Rect originalBounds;
	
	public HealthBar(int id, int xP, int yP, int height, int width, Bitmap sourceImage, Bitmap backgroundImage, Player player) {
		super(id, xP, yP, height, width, sourceImage);
		this.player = player;
		this.maxWidth = width;
		this.maxPlayerHealth = player.getHealth();
		this.originalBounds = new Rect(this.xPosition-this.width/2, this.yPosition-this.height/2, this.xPosition+this.width/2,this.yPosition+this.height/2);
		this.backgroundImage = Bitmap.createScaledBitmap(backgroundImage, (int)this.maxWidth, this.height, true);
	}
	
	public void update() {
		
		//calculate changeInWidth
		this.changeInWidth = this.width - (int)((this.maxWidth / this.maxPlayerHealth) * (double)this.player.getHealth());
		//adjust currentLength
		this.currentLength = this.width - this.changeInWidth;
		//set bounds to reflect changes
		this.bounds.set(this.xPosition-this.width/2, this.yPosition-this.height/2, this.xPosition+this.width/2-(int)this.changeInWidth,this.yPosition+this.height/2);
	}
	
	public int changeColor() {
		//1 -- change to red, 0 -- change to green
		
		if(this.currentLength <= ((double)this.maxWidth / 4.0)) {
			return 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public void draw(Canvas c) {
		c.drawBitmap(this.backgroundImage, null, this.originalBounds, null);
		c.drawBitmap(this.image, null, this.bounds, null);
	}
	
}
