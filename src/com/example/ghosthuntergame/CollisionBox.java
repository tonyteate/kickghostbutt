package com.example.ghosthuntergame;

import com.example.ghosthuntergame.PowerUp.powerUpType;

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
	// PLAYER & GAMEPIECE
	
	public static int checkCollision(Player player, GamePiece gamePiece) {
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
		
		boolean collides = Rect.intersects(player.getBounds(),gamePiece.getBounds());
		
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
			
			double gamePieceX = gamePiece.getxPosition();
			double gamePieceY = gamePiece.getyPosition();
			double gamePieceHeight = gamePiece.getHeight();
			double gamePieceWidth = gamePiece.getWidth();
			
			//CASE 1
			//player collides with TOP of ghost and player y velocity is down (positive)
			if((playerY + (0.5*playerHeight)) < (gamePieceY - (0.5*gamePieceHeight) + buffer)){
				return top;
			}
			
			//CASE 2
			//player collides with BOTTOM of ghost
			if((playerY - (0.5*playerHeight)) > (gamePieceY + (0.5*gamePieceHeight) - buffer)) {
				return bottom;
			}
			
			//CASE 3
			//player collides with RIGHT of ghost
			if((playerX - (0.5*playerWidth)) > (gamePieceX + (0.5*gamePieceWidth) - buffer)) {
				return right;
			}
			
			//CASE 4
			//player collides with LEFT of ghost
			if((playerX + (0.5*playerWidth)) < (gamePieceX - (0.5*gamePieceWidth) + buffer)) {
				return left;
			}
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

		public static boolean checkCollisionFriendlyGhost(FriendlyGhost f, Ghost g) {
			if (Rect.intersects(f.getBounds(), g.getBounds())) {
				return true;
			} else {
				return false;
			}

		}
	
		
}
