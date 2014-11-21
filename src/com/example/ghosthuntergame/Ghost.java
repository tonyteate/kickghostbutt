package com.example.ghosthuntergame;

import android.graphics.Bitmap;
import android.graphics.Rect;

//want ghost 

public class Ghost extends GamePiece {

	public Ghost(int id, int xP, int yP, int height, int width,Bitmap sourceImage, Player player) {
		super(id, xP, yP, height, width, sourceImage);
		this.setSpeedX(player);
		this.setSpeedY(player);
	}

	public void setSpeedX(Player player) {
		
		//subtraction finds the player relative to the ghost
		double destinationX = player.getxPosition() - this.xPosition;
		
		//negative X value means the player is to the left of the ghost
		if(destinationX < 0) {
			
			this.setxVelocity(-1);
		} else if (destinationX > 0) {
			
			this.setxVelocity(1);
		}
	}

	public void setSpeedY(Player player) {
		
		//subtraction finds the player relative to the ghost
		double destinationY = player.getyPosition() - this.yPosition;
		
		//negative Y value means the player is above the ghost
		if(destinationY < 0) {
			
			this.setyVelocity(-1);
		} else if (destinationY > 0) {
			
			this.setyVelocity(1);
		}
	}

}
