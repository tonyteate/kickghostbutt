package com.example.ghosthuntergame;

import java.util.ArrayList;
import java.util.Iterator;

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

	private ArrayList<Ghost> ghostList = new ArrayList<Ghost>();
	private ArrayList<GamePiece> wallList = new ArrayList<GamePiece>();
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
		
		scorePaint = new Paint();
		scorePaint.setARGB(255, 255, 0, 0);
		scorePaint.setTypeface(Typeface.DEFAULT);		
		scorePaint.setTextSize(dP(14));
	}

	@Override
	public void onDraw(Canvas c) {
		super.onDraw(c);
				
		
		numTicks += 1;
		
		//call this code every 1000 clock cycles

		/*
		if(numTicks == 100) {
			this.ghostList.add(new Ghost(ghostList.size(), (int)(Math.random() * this.getWidth()), (int)(Math.random() * this.getHeight()), 40, 40, BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image), this.player, 5));
		}
		*/
		if(numTicks % 100 == 0) {
			this.ghostList.add(new Ghost(ghostList.size(), (int)(Math.random() * this.getWidth()), (int)(Math.random() * this.getHeight()), 40, 40, BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image), this.player, 5));
		}
		
		if(numTicks % 50 == 0) {
			for(int i = 0; i < ghostList.size(); i++) {
				Ghost g = this.ghostList.get(i);
				g.setSpeedX(this.player);
				g.setSpeedY(this.player);
			}
		}
		
		if((this.player.getScore() > 0) &&(this.player.getScore() % 10 == 0) && (this.numTicks % 150 == 0)) {
			this.ghostList.add(new Ghost(ghostList.size(), (int)(Math.random() * this.getWidth()), (int)(Math.random() * this.getHeight()), 60, 60, BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image_red), this.player, 10));
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
		
		
		//Update all onScreenObjects in game
		
		//update player
		this.player.update();
		//update ghosts
		for(Ghost ghost : this.ghostList) {
			ghost.update();
		}
		
		//Draw all onScreenObjects in game
		
		//draw player
		this.player.draw(c);
		//draw ghosts
		for(Ghost ghost : this.ghostList) {
			ghost.draw(c);
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
		
		if (this.player.getHealth() <= 0){
//			Context context = getContext();
//			Intent intent = new Intent(context, GameOverActivity.class);
//			intent.putExtra(PLAYER_SCORE, this.player.getScore());
//			context.startActivity(intent);
			return;
		}
		invalidate();
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
