package com.example.ghosthuntergame;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.ghosthuntergame.PowerUp.powerUpType;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
	
	public static final String PLAYER_SCORE = "player_score";

	private boolean gameOver = false;
	private long startTime = 0;
	private long endTime = 0;
	private long minGameTime = 10;
	
	private ArrayList<Ghost> ghostList = new ArrayList<Ghost>();
	private ArrayList<GamePiece> wallList = new ArrayList<GamePiece>();
	private ArrayList<PowerUp> powerUpList = new ArrayList<PowerUp>();
	
	
	private HealthBar healthBar;
	

	private ArrayList<FriendlyGhost> friendlyGhostList = new ArrayList<FriendlyGhost>();
	private Player player;
	private int numTicks;
	private int timeLeftButtonTouched;
	private int timeDownButtonTouched;
	private int timeUpButtonTouched;
	private int timeRightButtonTouched;
	private Rect buttonLeft;
	private Rect buttonDown;
	private Rect buttonUp;
	private Rect buttonRight;
	private Bitmap buttonLeftImage;
	private Bitmap buttonDownImage;
	private Bitmap buttonUpImage;
	private Bitmap buttonRightImage;
	private Paint scorePaint;
	
	public int dP(int pixels) {
		return GameActivity.dP(pixels);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		System.out.println(h);
		if(w != 0) {
			//integers to create the buttons
			int padding = dP(10);
			int buttonHeight = dP(100);
			int screenWidth = getWidth();
			int screenHeight = getHeight();
			int screenWidthAdjusted = screenWidth - 5*padding;
			int buttonWidth = screenWidthAdjusted/4;
			
			buttonLeft = new Rect(padding, screenHeight - buttonHeight, buttonWidth + padding, screenHeight - dP(20));
			buttonDown = new Rect(buttonLeft.right + padding, screenHeight - buttonHeight, buttonLeft.right + padding + buttonWidth, screenHeight - dP(20));
			buttonUp = new Rect(buttonDown.right + padding, screenHeight - buttonHeight, buttonDown.right + padding + buttonWidth, screenHeight - dP(20));
			buttonRight = new Rect(buttonUp.right + padding, screenHeight - buttonHeight, buttonUp.right + padding + buttonWidth, screenHeight - dP(20));

			buttonLeftImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_arrow_left), buttonLeft.width(), buttonLeft.height(), true);
			buttonDownImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_arrow_down), buttonDown.width(), buttonDown.height(), true);
			buttonUpImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_arrow_up), buttonUp.width(), buttonUp.height(), true);
			buttonRightImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_arrow_right), buttonRight.width(), buttonRight.height(), true);
			
			// walls
			int wallRelativeWidth = this.player.width * 2;
			int wallRelativeHeight = this.player.height * 2;
			// left wall
			this.wallList.add(new GamePiece(this.wallList.size(), -(wallRelativeWidth/2), screenHeight/2, screenHeight, wallRelativeWidth, Bitmap.createBitmap(wallRelativeWidth, screenHeight, Bitmap.Config.ARGB_8888)));
			// right wall
			this.wallList.add(new GamePiece(this.wallList.size(), screenWidth + wallRelativeWidth/2, screenHeight/2, screenHeight, wallRelativeWidth, Bitmap.createBitmap(wallRelativeWidth, screenHeight, Bitmap.Config.ARGB_8888)));			
			// top wall
			this.wallList.add(new GamePiece(this.wallList.size(), screenWidth/2, -(wallRelativeHeight/2), wallRelativeHeight, screenWidth, Bitmap.createBitmap(screenWidth, wallRelativeHeight, Bitmap.Config.ARGB_8888)));						
			// bottom wall
			this.wallList.add(new GamePiece(this.wallList.size(), screenWidth/2, screenHeight - buttonHeight/2, buttonHeight, screenWidth, Bitmap.createBitmap(screenWidth, buttonHeight, Bitmap.Config.ARGB_8888)));									
		}
	}
	
	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public GameView(Context context) {
		super(context);
		initialize();
	}

	public void addGamePiece (Ghost g) {
		this.ghostList.add(g);
	}
	
	public void initialize() {
		this.player = new Player(1, dP(50), dP(50), dP(50), dP(50), BitmapFactory.decodeResource(getResources(), R.drawable.top_down_knight));		
		this.numTicks = 0;
		
		this.healthBar = new HealthBar(2, dP(100), dP(40), dP(14), dP(100), BitmapFactory.decodeResource(getResources(), R.drawable.health_bar_image_green), BitmapFactory.decodeResource(getResources(), R.drawable.health_bar_image_grey), this.player);
		
		scorePaint = new Paint();
		scorePaint.setARGB(255, 255, 0, 0);
		scorePaint.setTypeface(Typeface.DEFAULT);		
		scorePaint.setTextSize(dP(14));
	}

	@Override
	public void onDraw(Canvas c) {
		
		this.startTime = System.currentTimeMillis();
		
		super.onDraw(c);
				
		
		numTicks += 1;
		
		//call this code every 1000 clock cycles

		/*
		if(numTicks == 100) {
			this.ghostList.add(new Ghost(ghostList.size(), (int)(Math.random() * this.getWidth()), (int)(Math.random() * this.getHeight()), 40, 40, BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image), this.player, 5));
		}
		*/
		if(numTicks % 100 == 0) {
			int ghostX = (int)(Math.random() * this.getWidth());
			int ghostY = (int)(Math.random() * this.getHeight());
			if(ghostX <= (player.getxPosition() + (player.getWidth()/2)) && ghostX >= (player.getxPosition() - (player.getWidth()/2))) {
				ghostX = ghostX + player.getWidth() + dP(10);
			}

			if(ghostY <= (player.getyPosition() + (player.getHeight()/2)) && ghostY >= (player.getyPosition() - (player.getHeight()/2))) {
				ghostY = ghostY + player.getHeight() + dP(10);
			}

			this.ghostList.add(new Ghost(ghostList.size(), ghostX, ghostY, dP(40), dP(40), BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image), this.player, 5));
		}

		//makes the ghosts follow the player
		if(numTicks % 50 == 0) {
			for(int i = 0; i < ghostList.size(); i++) {
				Ghost g = this.ghostList.get(i);
				g.setSpeedX(this.player);
				g.setSpeedY(this.player);
			}
		}

		//conditions for the big ghost
		if((this.player.getScore() > 0) &&(this.player.getScore() % 10 == 0) && (this.numTicks % 150 == 0)) {
			int ghostX = (int)(Math.random() * this.getWidth());
			int ghostY = (int)(Math.random() * this.getHeight());
			if(ghostX <= (player.getxPosition() + (player.getWidth()/2)) && ghostX >= (player.getxPosition() - (player.getWidth()/2))) {
				ghostX = ghostX + player.getWidth() + dP(10);
			}

			if(ghostY <= (player.getyPosition() + (player.getHeight()/2)) && ghostY >= (player.getyPosition() - (player.getHeight()/2))) {
				ghostY = ghostY + player.getHeight() + dP(10);
			}
			this.ghostList.add(new Ghost(ghostList.size(), ghostX, ghostY, dP(60), dP(60), BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image_red), this.player, 10));
		}

		//conditions for making friendly ghosts
		if(this.numTicks % 1000 == 0 && friendlyGhostList.size() == 0) {
			this.friendlyGhostList.add(new FriendlyGhost(friendlyGhostList.size(), (int)(Math.random() * this.getWidth()), 20, dP(40), dP(40), BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image_red)));
		}

		//conditions for the bomb power-up
		if((this.player.getScore() > 25) && (this.player.getScore() % 20 == 0) && (this.numTicks % 300 == 0)) {
			PowerUp bomb = new PowerUp(powerUpType.BOMB, powerUpList.size(), (int)(Math.random() * this.getWidth()), (int)(Math.random() * this.getHeight()), dP(40), dP(40), BitmapFactory.decodeResource(getResources(), R.drawable.bomb_sprite));
			Iterator<GamePiece> iteratorWallList = this.wallList.iterator();
			while(iteratorWallList.hasNext()) {
				GamePiece wall = iteratorWallList.next();
				if(Rect.intersects(bomb.getBounds(), wall.getBounds())) {
					bomb.setxPosition((int)(bomb.getxPosition() + wall.getWidth() + dP(10)));
				}
				if(Rect.intersects(bomb.getBounds(), wall.getBounds())) {
					bomb.setyPosition((int)(bomb.getyPosition() + wall.getHeight() + dP(10)));
				}
			}
			this.powerUpList.add(bomb);
		}

		//conditions to start making heart power-ups
		if((this.player.getScore() >= 15) && (this.player.getScore() % 15 == 0) && (this.numTicks % 200 == 0)) {
			PowerUp health = new PowerUp(powerUpType.HEALTH, powerUpList.size(), (int)(Math.random() * this.getWidth()), (int)(Math.random() * this.getHeight()), dP(40), dP(40), BitmapFactory.decodeResource(getResources(), R.drawable.heart_sprite));
			Iterator<GamePiece> iteratorWallList = this.wallList.iterator();
			while(iteratorWallList.hasNext()) {
				GamePiece wall = iteratorWallList.next();
				if(Rect.intersects(health.getBounds(), wall.getBounds())) {
					health.setxPosition((int)(health.getxPosition() + wall.getWidth() + dP(10)));
				}
				if(Rect.intersects(health.getBounds(), wall.getBounds())) {
					health.setyPosition((int)(health.getyPosition() + wall.getHeight() + dP(10)));
				}
			}
			this.powerUpList.add(health);
		}

		//conditions for the coin power-up
		if((this.player.getScore() > 30) && (this.player.getScore() % 30 == 0) && (this.numTicks % 300 == 0)) {
			PowerUp coin = new PowerUp(powerUpType.COIN, powerUpList.size(), (int)(Math.random() * this.getWidth()), (int)(Math.random() * this.getHeight()), dP(40), dP(40), BitmapFactory.decodeResource(getResources(), R.drawable.coin_sprite));
			Iterator<GamePiece> iteratorWallList = this.wallList.iterator();
			while(iteratorWallList.hasNext()) {
				GamePiece wall = iteratorWallList.next();
				if(Rect.intersects(coin.getBounds(), wall.getBounds())) {
					coin.setxPosition((int)(coin.getxPosition() + wall.getWidth() + dP(10)));
				}
				if(Rect.intersects(coin.getBounds(), wall.getBounds())) {
					coin.setyPosition((int)(coin.getyPosition() + wall.getHeight() + dP(10)));
				}
			}
			this.powerUpList.add(coin);
		}
		
		//check collisions between all ghosts and the player
		Iterator<Ghost> iterator = this.ghostList.iterator();
		while(iterator.hasNext()) {

			Ghost ghost = iterator.next();
			int collisionResult = CollisionBox.checkCollision(this.player, ghost);

			if(collisionResult == 1) {
				//remove ghost from ghostList
				iterator.remove();
				//increase the player's score
				this.player.setScore(this.player.getScore() + 1); //score is total # of ghosts killed

			} else if(collisionResult == 2) {
				//reduce health of player
				this.player.setHealth(this.player.getHealth() - ghost.getDamage());

				//reverse y-direction of the player
				//this.player.setyVelocity((int)(-1 * this.player.getyVelocity()));

				//reverse y-direction of the ghost
				//ghost.setyVelocity((int)(-1 * ghost.getyVelocity()));

			} else if(collisionResult == 3) {
				//reduce health of player
				this.player.setHealth(this.player.getHealth() - ghost.getDamage());

				//reverse x-direction of player
				//this.player.setxVelocity((int)(-1 * this.player.getxVelocity()));

				//reverse x-direction of ghost
				//ghost.setxVelocity((int)(-1 * ghost.getxVelocity()));

			} else if(collisionResult == 4) {
				//reduce health of player
				this.player.setHealth(this.player.getHealth() - ghost.getDamage());

				//reverse x-direction of player
				//this.player.setxVelocity((int)(-1 * this.player.getxVelocity()));

				//reverse x-direction of ghost
				//ghost.setxVelocity((int)(-1 * ghost.getxVelocity()));

			} else if(collisionResult == 5) {
				//reduce health of player
				this.player.setHealth(this.player.getHealth() - ghost.getDamage());

			} else {

			}

		}

		//check collision between friendly ghost and ghosts
		if(friendlyGhostList.size() >0) {
			Iterator<Ghost> iteratorGhostList = this.ghostList.iterator();
			while(iteratorGhostList.hasNext()) {
				Ghost g = iteratorGhostList.next();
				Iterator<FriendlyGhost> i = this.friendlyGhostList.iterator();
				while(i.hasNext()) {
					FriendlyGhost fGhost = i.next();
					boolean collisionResult = CollisionBox.checkCollisionFriendlyGhost(fGhost, g);
					if (collisionResult == true) {
						i.remove();
						iteratorGhostList.remove();
					}
				}
			}
		}
		
		//remove friendly ghost when it gets to the bottom
		if(friendlyGhostList.size() >0) {
			for(GamePiece boundry : this.wallList) {
				if(this.friendlyGhostList.get(0).getBounds().intersect(boundry.getBounds())) {
					this.friendlyGhostList.clear();
					break;
				}
			}
		}

		//check collisions between all walls and player (assume ghost can phase through walls?)
		Iterator<GamePiece> iteratorWallList = this.wallList.iterator();
		while(iteratorWallList.hasNext()) {
			GamePiece wall = iteratorWallList.next();
			int collisionResult = CollisionBox.checkCollision(this.player, wall);

			if(collisionResult == 1) {

				//reverse y-direction of the player
				this.player.setyVelocity((int)(-1 * this.player.getyVelocity()));
				//reset player position outside of wall
				//	-1 at end ensures player is outside in case decimal gets truncated on casting to int if height of wall is odd
				this.player.setyPosition((int)(wall.getyPosition() - wall.getHeight()/2 - this.player.getHeight()/2) /*- 1*/);

			} else if(collisionResult == 2) {

				//reverse y-direction of the player
				this.player.setyVelocity((int)(-1 * this.player.getyVelocity()));
				//reset player position outside of wall
				//	+1 at end ensures player is outside in case decimal gets truncated on casting to int if height of wall is odd
				this.player.setyPosition((int)(wall.getyPosition() + wall.getHeight()/2 + this.player.getHeight()/2) /*+ 1*/);

			} else if(collisionResult == 3) {

				//reverse x-direction of player
				this.player.setxVelocity((int)(-1 * this.player.getxVelocity()));
				//reset player position outside of wall
				//	+1 at end ensures player is outside in case decimal gets truncated on casting to int if height of wall is odd
				this.player.setxPosition((int)(wall.getxPosition() + wall.getWidth()/2  + this.player.getWidth()/2) /*+ 1*/);

			} else if(collisionResult == 4) {

				//reverse x-direction of player
				this.player.setxVelocity((int)(-1 * this.player.getxVelocity()));
				//reset player position outside of wall
				//	-1 at end ensures player is outside in case decimal gets truncated on casting to int if height of wall is odd
				this.player.setxPosition((int)(wall.getxPosition() - wall.getWidth()/2 - this.player.getWidth()/2) /*- 1*/);

			} else {

			}

		}

		//check for power-up collection
		Iterator<PowerUp> powerUpIterator = this.powerUpList.iterator();
		while(powerUpIterator.hasNext()) {
			PowerUp power = powerUpIterator.next();
			powerUpType type = CollisionBox.checkCollision(this.player, power);
			
			//bomb effect
			if(type == powerUpType.BOMB) {
				ghostList.clear();
				player.setScore(player.getScore() + 5);
				powerUpIterator.remove();
			} else if(type == powerUpType.HEALTH) {  //heart effect
				if(player.getHealth() < 800) {
					player.setHealth(player.getHealth() + 200);
					powerUpIterator.remove();
				} else if (player.getHealth() > 800 && player.getHealth() < 1000){
					player.setHealth(1000);
					powerUpIterator.remove();
				} else {
					powerUpIterator.remove();
				}
			} else  if(type == powerUpType.COIN){  //coin effect
				player.setScore(player.getScore() + 10);
				powerUpIterator.remove();
			} else {

			}
		}

		//Update all onScreenObjects in game

		//update player
		this.player.update();
		//update ghosts
		for(Ghost ghost : this.ghostList) {
			ghost.update();
		}
		//update healthBar
		this.healthBar.update();
		if(this.healthBar.changeColor() == 1) {
			this.healthBar.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.health_bar_image_red));
		} else {
			this.healthBar.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.health_bar_image_green));
		}
		
		if(friendlyGhostList.size() >0) {
			for(FriendlyGhost ghost : this.friendlyGhostList) {
				ghost.update();
			}
		}
		//Draw all onScreenObjects in game

		//draw player
		this.player.draw(c);
		//draw ghosts
		for(Ghost ghost : this.ghostList) {
			ghost.draw(c);
		}

		//draw power-ups
		for(PowerUp powerUp: this.powerUpList) {
			powerUp.draw(c);
		}
		//draw the friendly ghost
		if(friendlyGhostList.size() >0) {
			for(FriendlyGhost ghost: this.friendlyGhostList) {
				ghost.draw(c);
			}
		}
		
		//****VERY IMPORTANT FOR AESTHETICS****
		// must draw ghosts before walls so that ghosts will be hidden when they move behind wall

		//draw walls
		for(GamePiece wall : this.wallList) {
			wall.draw(c);
		}
		
		c.drawBitmap(buttonLeftImage, null, buttonLeft, null);
		c.drawBitmap(buttonDownImage, null, buttonDown, null);
		c.drawBitmap(buttonUpImage, null, buttonUp, null);
		c.drawBitmap(buttonRightImage, null, buttonRight, null);
		
		//draw score
		c.drawText("Score: " + this.player.getScore(), dP(200), dP(30), scorePaint);
		c.drawText("Health: " + this.player.getHealth() + "", dP(100), dP(30), scorePaint);
		
		this.healthBar.draw(c);
		
		//update gameOver
		this.gameOver = (this.player.getHealth() <= 0);
		
		//Get system time: endTime
		this.endTime = System.currentTimeMillis();
		
		System.out.println("--- " + (this.endTime - this.startTime));
		
		if(gameOver == false) {
			if((this.endTime - this.startTime) >=  this.minGameTime) {
				invalidate();
			} else {
				while((this.endTime - this.startTime) < this.minGameTime) {
					this.endTime = System.currentTimeMillis();
				}
			}
			invalidate();
		}
	}

	public ArrayList<Ghost> getGhostList() {
		return this.ghostList;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int x = (int)e.getX();
		int y = (int)e.getY();
		
		//each button has its own startTime, just like they have their own click times
		
		//left button touched
		if(buttonLeft.contains(x, y)) {
			//stored time when button is touched (start time)
			this.timeLeftButtonTouched = this.numTicks;
			//totalTimeTime is how many clock cycles or number of ticks we want the player to move
			//when a button is pressed
			
			//set non-zero when current time - start time <= totalMoveTime
			if(this.numTicks - this.timeLeftButtonTouched <= 5) {
				this.player.setxVelocity(-2);
				this.player.setyVelocity(0);
				
				//setting yVelocity to zero means that once button is pressed all other movements canceled
				
			} else {
			//set zero when current time - start time >= totalMoveTime
				this.player.setxVelocity(0);
			}
		}
		//bottum button touched
		if(buttonDown.contains(x, y)) {
			//stored time when button is touched (start time)
			this.timeLeftButtonTouched = this.numTicks;
			//totalTimeTime is how many clock cycles or number of ticks we want the player to move
			//when a button is pressed
			
			//set non-zero when current time - start time <= totalMoveTime
			if(this.numTicks - this.timeLeftButtonTouched <= 5) {
				this.player.setxVelocity(0);
				this.player.setyVelocity(2);
				
				//setting yVelocity to zero means that once button is pressed all other movements canceled
				
			} else {
			//set zero when current time - start time >= totalMoveTime
				this.player.setyVelocity(0);
			}
		}
		//top button touched
		if(buttonUp.contains(x, y)) {
			//stored time when button is touched (start time)
			this.timeLeftButtonTouched = this.numTicks;
			//totalTimeTime is how many clock cycles or number of ticks we want the player to move
			//when a button is pressed
			
			//set non-zero when current time - start time <= totalMoveTime
			if(this.numTicks - this.timeLeftButtonTouched <= 5) {
				this.player.setxVelocity(0);
				this.player.setyVelocity(-2);
				
				//setting yVelocity to zero means that once button is pressed all other movements canceled
				
			} else {
			//set zero when current time - start time >= totalMoveTime
				this.player.setyVelocity(0);
			}
			
			
		}
		//right button touched
		if(buttonRight.contains(x, y)) {
			//stored time when button is touched (start time)
			this.timeLeftButtonTouched = this.numTicks;
			//totalTimeTime is how many clock cycles or number of ticks we want the player to move
			//when a button is pressed
			
			//set non-zero when current time - start time <= totalMoveTime
			if(this.numTicks - this.timeLeftButtonTouched <= 5) {
				this.player.setxVelocity(2);
				this.player.setyVelocity(0);
				
				//setting yVelocity to zero means that once button is pressed all other movements canceled
				
			} else {
			//set zero when current time - start time >= totalMoveTime
				this.player.setxVelocity(0);
			}
		}
		
		
/*		
		for(OnScreenObject oso : this.getOnScreenObjects()) {
			oso.respondToTouch(xCoordinate, yCoordinate);
		}
		
*/
		
		return true;
	}


}
