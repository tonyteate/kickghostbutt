package com.example.ghosthuntergame;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

	private ArrayList<Ghost> ghostList = new ArrayList<Ghost>();
	private ArrayList<GamePiece> wallList = new ArrayList<GamePiece>();
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
	
	public int dP(int pixels) {
		return GameActivity.dP(pixels);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if(w != 0) {
			//integers to create the buttons
			int padding = dP(10);
			int screenWidth = getWidth() - 5*padding;
			int screenHeight = getHeight();
			int buttonWidth = screenWidth/4;
			
			buttonLeft = new Rect(padding, screenHeight - dP(100), buttonWidth + padding, screenHeight - dP(20));
			buttonDown = new Rect(buttonLeft.right + padding, screenHeight - dP(100), buttonLeft.right + padding + buttonWidth, screenHeight - dP(20));
			buttonUp = new Rect(buttonDown.right + padding, screenHeight - dP(100), buttonDown.right + padding + buttonWidth, screenHeight - dP(20));
			buttonRight = new Rect(buttonUp.right + padding, screenHeight - dP(100), buttonUp.right + padding + buttonWidth, screenHeight - dP(20));
			
			System.out.println(buttonLeft.height());
			System.out.println(buttonLeft.width());

			buttonLeftImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_arrow_left), buttonLeft.width(), buttonLeft.height(), true);
			buttonDownImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_arrow_down), buttonDown.width(), buttonDown.height(), true);
			buttonUpImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_arrow_up), buttonUp.width(), buttonUp.height(), true);
			buttonRightImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_arrow_right), buttonRight.width(), buttonRight.height(), true);
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
		
		this.wallList = new ArrayList<GamePiece>();
		//boundaries: top, bottom, right, left
//		int xPosition = 0;
//		int yPosition = 0;
//		int width = 0;
//		int height = 0;
//		Bitmap bitmap = null;
//		
//		//top
//		xPosition = (int)(getWidth() / 2);
//		yPosition = (int)(0 - this.player.getHeight());
//		width = (int)(this.getWidth() + 4*this.player.getWidth());
//		height = (int)(2*this.player.getHeight());
//		
//		System.out.println("*********** " + getWidth() + " ***************************************************************************");
//		System.out.println("width: " + width);
//		System.out.println("height: " + height);
//		System.out.println("x Pos: " + xPosition);
//		System.out.println("y Pos: " + yPosition);
//		
//		
//		bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wall_image), width, height, true);
//		this.wallList.add(new GamePiece(this.wallList.size(), dP(xPosition), dP(yPosition), dP(width), dP(height), bitmap));
		
		//basic wall
		this.wallList.add(new GamePiece(this.wallList.size(), dP(200), dP(200), dP(30), dP(100), Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wall_image), dP(100), dP(200), true)));
		
		this.numTicks = 0;
	}

	@Override
	public void onDraw(Canvas c) {
		super.onDraw(c);
				
		numTicks += 1;

		//left, top, right, bottom
		
		//left
		c.drawBitmap(buttonLeftImage, null, buttonLeft, null);
		//down
		c.drawBitmap(buttonDownImage, null, buttonDown, null);
		//up
		c.drawBitmap(buttonUpImage, null, buttonUp, null);
		//right
		c.drawBitmap(buttonRightImage, null, buttonRight, null);
		
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
		
		if(this.numTicks % 1000 == 0 && friendlyGhostList.size() == 0) {
			this.friendlyGhostList.add(new FriendlyGhost(friendlyGhostList.size(), (int)(Math.random() * this.getWidth()), 20, 40, 40, BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image_red)));
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
		
		
		//Update all onScreenObjects in game
		
		//update player
		this.player.update();
		//update ghosts
		for(Ghost ghost : this.ghostList) {
			ghost.update();
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
		
		//draw score
		Paint paint = new Paint();
		paint.setARGB(255, 255, 0, 0);
		
		c.drawText("Score: " + this.player.getScore(), dP(200), dP(30), paint);
		c.drawText("Health: " + this.player.getHealth() + "", dP(100), dP(30), paint);
		
		
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
