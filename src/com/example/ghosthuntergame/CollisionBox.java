package com.example.ghosthuntergame;

import android.graphics.Rect;

//class contains all methods that handle collision of objects in game
public class CollisionBox {
	
	//returns integers based on what is to be done in gameView based on collision and relative posiitions of ghost and player
	// 1-above
	// 
	
	public static int checkCollision(Player player, Ghost ghost) {
		//CASE 1: player collides with top of ghost
			//within gameView class:
				//remove ghost from ghostList
				//increase the player's score
		//CASE 2: player collides with bottom
			//within gameView class:
				//reduce health of player
				//reverse y-direction of the player
				//reverse y-direction of the ghost
		//CASE 3: player collides with right
			//within gameView class:
				//reduce health of player
				//reverse x-direction of player
				//reverse x-direction of ghost
		//CASE 4: player collides with left
			//within gameView class:
				//reduce health of player
				//reverse x-direction of player
				//reverse x-direction of ghost
		
		
		int top = 1;	//CASE 1
		int bottom = 2;	//CASE 2
		int right = 3;	//CASE 3
		int left = 4;	//CASE 4
		
		boolean collides = Rect.intersects(player.getBounds(),ghost.getBounds());
		
		if(collides) {
			
			int buffer = 4;
			
			//get all player and ghost information needed to make decisions
			//	this includes:	player position (x and y)
			//					player height and width
			//					ghost position (x and y)
			//					ghost height and width
			
			double playerX = player.getxPosition();
			double playerY = player.getyPosition();
			double playerHeight = player.getHeight();
			double playerWidth = player.getWidth();
			
			double ghostX = ghost.getxPosition();
			double ghostY = ghost.getyPosition();
			double ghostHeight = ghost.getHeight();
			double ghostWidth = ghost.getWidth();
			
			//CASE 1
			//player collides with TOP of ghost
			if((playerY + (0.5*playerHeight)) < (ghostY - (0.5*ghostHeight) + buffer)) {
				return top;
			}
			
			//CASE 2
			//player collides with BOTTOM of ghost
			if((playerY - (0.5*playerHeight)) > (ghostY + (0.5*ghostHeight) - buffer)) {
				return bottom;
			}
			
			//CASE 3
			//player collides with RIGHT of ghost
			if((playerX - (0.5*playerWidth)) > (ghostX + (0.5*ghostWidth) - buffer)) {
				return right;
			}
			
			//CASE 4
			//player collides with LEFT of ghost
			if((playerX + (0.5*playerWidth)) < (ghostX - (0.5*ghostWidth) + buffer)) {
				return left;
			}
		}
		
		return 0;
		
	}
		
}
