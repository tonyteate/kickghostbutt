package com.example.ghosthuntergame;

import android.graphics.Rect;

//class contains all methods that handle collision of objects in game
public class CollisionBox {
	
	//returns integers based on what is to be done in gameView based on collision and relative posiitions of ghost and player
	// 1-above
	// 
	
	public int checkCollision(Player player, Ghost ghost) {
		//CASE 1: player collides with top of ghost
			//within gameView class:
				//remove ghost from gamePieces
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
			
			
			
			//player is above ghost
			//CASE 1
			if(player.getyPosition() < ghost.getyPosition()) {
				
				
				return top;
			}
			
			//player is below ghost
			//if y position of player is greater than y position of ghost
			if(player.getyPosition() > ghost.getyPosition()) {
				return bottom;
			}
			
			//player is right of ghost
			//if x position of player is greater than x position of ghost
			if(player.getxPosition() > ghost.getxPosition()) {
				return right;
			}
			
			//player is left of ghost
			//if x position of player is greater than x position of ghost
			if(player.getxPosition() > ghost.getxPosition()) {
				return left;
			}
		}
		
		return 0;
		
	}
		
}
