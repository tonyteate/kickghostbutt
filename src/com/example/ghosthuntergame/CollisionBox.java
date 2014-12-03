package com.example.ghosthuntergame;

import com.example.ghosthuntergame.PowerUp.powerUpType;

import android.graphics.Rect;

//class contains all methods that handle collision of objects in game

//List of methods
/*
 * Player & Ghost
 * Player & Wall
 * Player & PowerUp
 * FirendlyGhost & Ghost
 * Ghost & Wall
 * Ghost & PowerUp 
 * Ghost & Ghost
 * PowerUp & Wall
 * PowerUp & FriendlyGhost
 * PowerUp & PowerUp
 * 
 */


public class CollisionBox {
	
	//returns integers based on what is to be done in gameView based on collision and relative positions of ghost and player
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
		//CASE 5: player is inside of ghost
		
		
		int top = 1;	//CASE 1
		int bottom = 2;	//CASE 2
		int right = 3;	//CASE 3
		int left = 4;	//CASE 4
		int inside = 5;	//CASE 5
		
		boolean collides = Rect.intersects(player.getBounds(),ghost.getBounds());
		
		if(collides) {
			
			int buffer = 4;
			
			//get all player and ghost information needed to make decisions
			//	this includes:	player position (x and y)
			//					player height and width
			//					player y velocity
			//					ghost position (x and y)
			//					ghost height and width
			
			double playerX = player.getxPosition();
			double playerY = player.getyPosition();
			double playerHeight = player.getHeight();
			double playerWidth = player.getWidth();
			double playerYVelocity = player.getyVelocity();
			
			double ghostX = ghost.getxPosition();
			double ghostY = ghost.getyPosition();
			double ghostHeight = ghost.getHeight();
			double ghostWidth = ghost.getWidth();
			
			//CASE 1
			//player collides with TOP of ghost and player y velocity is down (positive)
			if(((playerY + (0.5*playerHeight)) < (ghostY - (0.5*ghostHeight) + buffer)) && (playerYVelocity > 0)){
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
			
			//CASE 5
			//player is inside ghost (all other scenarios where player collides with ghost)
			return inside;
		}
		
		return 0;
		
	}
	
	//________________________________________________________________________________________________________________
	// PLAYER & WALL
	
	public static int checkCollision(Player player, Wall wall) {
		//CASE 1: player collides with top of gamePiece
			//within gameView class:
				
		//CASE 2: player collides with bottom
			//within gameView class:
				
		//CASE 3: player collides with right
			//within gameView class:
			
		//CASE 4: player collides with left
			//within gameView class:
				
		
		
		int top = 1;	//CASE 1
		int bottom = 2;	//CASE 2
		int right = 3;	//CASE 3
		int left = 4;	//CASE 4
		int inside = 5;	//CASE 5
		
		boolean collides = Rect.intersects(player.getBounds(),wall.getBounds());
		
		if(collides) {
			
			int buffer = 4;
			
			//get all player and ghost information needed to make decisions
			//	this includes:	player position (x and y)
			//					player height and width
			//					player y velocity
			//					gamePiece position (x and y)
			//					gamePiece height and width
			
			double playerX = player.getxPosition();
			double playerY = player.getyPosition();
			double playerHeight = player.getHeight();
			double playerWidth = player.getWidth();
			double playerYVelocity = player.getyVelocity();
			
			double wallX = wall.getxPosition();
			double wallY = wall.getyPosition();
			double wallHeight = wall.getHeight();
			double wallWidth = wall.getWidth();
			
			//CASE 1
			//player collides with TOP of ghost and player y velocity is down (positive)
			if((playerY + (0.5*playerHeight)) < (wallY - (0.5*wallHeight) + buffer)){
				return top;
			}
			
			//CASE 2
			//player collides with BOTTOM of ghost
			if((playerY - (0.5*playerHeight)) > (wallY + (0.5*wallHeight) - buffer)) {
				return bottom;
			}
			
			//CASE 3
			//player collides with RIGHT of ghost
			if((playerX - (0.5*playerWidth)) > (wallX + (0.5*wallWidth) - buffer)) {
				return right;
			}
			
			//CASE 4
			//player collides with LEFT of ghost
			if((playerX + (0.5*playerWidth)) < (wallX - (0.5*wallWidth) + buffer)) {
				return left;
			}
			
			//CASE 5
			//player inside of wall
			return inside;
		}
		
		return 0;
		
	}
	
	//________________________________________________________________________________________________________________
		// PLAYER & PowerUp
		
		public static powerUpType checkCollision(Player player, PowerUp powerUp) {
			//CASE: player collides with powerUp
			
			boolean collides = Rect.intersects(player.getBounds(),powerUp.getBounds());
			
			if(collides) {
				
				return powerUp.getType();
			
			} else {
				
			return null;
			
			}
		}	

		
	//________________________________________________________________________________________________________________
		// FRIENDLYGHOST & GHOST
		public static int checkCollision(FriendlyGhost f, Ghost g) {
			if (Rect.intersects(f.getBounds(), g.getBounds())) {
				return 1;
			} else {
				return 0;
			}

		}
		
	//________________________________________________________________________________________________________________
		// GHOST & WALL
		
		public static int checkCollision(Ghost ghost, Wall wall) {
			
			boolean collides = Rect.intersects(ghost.getBounds(), wall.getBounds());
			
			if(collides) {
				return 1;
			}
			return 0;
		}
		
	//________________________________________________________________________________________________________________
		// GHOST & POWERUP

		public static int checkCollision(Ghost ghost, PowerUp powerUp) {

			boolean collides = Rect.intersects(ghost.getBounds(), powerUp.getBounds());

			if(collides) {
				return 1;
			}
			return 0;
		}
		
	//________________________________________________________________________________________________________________
		// GHOST & GHOST

		public static int checkCollision(Ghost ghost1, Ghost ghost2) {

			boolean collides = Rect.intersects(ghost1.getBounds(), ghost2.getBounds());

			if(collides) {
				return 1;
			}
			return 0;
		}
	
	/*	PowerUp & Wall
		 * PowerUp & FriendlyGhost
		 * PowerUp & PowerUp
	*/	
		
	//________________________________________________________________________________________________________________
		// PowerUp & WALL

		public static int checkCollision(PowerUp powerUp, Wall wall) {

			boolean collides = Rect.intersects(powerUp.getBounds(), wall.getBounds());

			if(collides) {
				return 1;
			}
			return 0;
		}
		
	//________________________________________________________________________________________________________________
		// PowerUp & FriendlyGhost

		public static int checkCollision(PowerUp powerUp, FriendlyGhost friendlyGhost) {

			boolean collides = Rect.intersects(powerUp.getBounds(), friendlyGhost.getBounds());

			if(collides) {
				return 1;
			}
			return 0;
		}
		
	//________________________________________________________________________________________________________________
		// PowerUp & PowerUp

		public static int checkCollision(PowerUp powerUp1, PowerUp powerUp2) {

			boolean collides = Rect.intersects(powerUp1.getBounds(), powerUp2.getBounds());

			if(collides) {
				return 1;
			}
			return 0;
		}
	
		
}
