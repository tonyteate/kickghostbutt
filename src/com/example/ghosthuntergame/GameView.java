package com.example.ghosthuntergame;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.ghosthuntergame.PowerUp.powerUpType;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

	private boolean gameOver = false;
	private long startTime = 0;
	private long endTime = 0;
	private long minGameTime = 10;
	
	private boolean spawnConflict = true;
	private int numSpawnAttempts = 0;
	private int maxNumSpawnAttempts = 50;
	private ArrayList<Ghost> ghostList = new ArrayList<Ghost>();
	private ArrayList<Wall> wallList = new ArrayList<Wall>();
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
	
	private int deviceScreenWidth;
	private int deviceScreenHeight;
	private int wallWidth;
	private int wallHeight;
	private Bitmap wall1Bitmap;
	private Bitmap wall2Bitmap;
	private Boolean wall2Added = false;
	private Bitmap wall3Bitmap;
	private Boolean wall3Added = false;
	
	private Bitmap topOfBoxBitmap;
	private Bitmap bottomOfBoxBitmap;
	private Bitmap rightOfBoxBitmap;
	private Bitmap leftOfBoxBitmap;
	private boolean wallBoxAdded = false;
	private boolean original3WallsReAdded = false;
	
	private Bitmap verticalWallBitmap;
	private Bitmap horizontalWallBitmap;
	private boolean plusGridLevelDisplayed = false;
	private boolean plusGridLevelReset = false;

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
			
			
			this.deviceScreenHeight = (int)getHeight();
			this.deviceScreenWidth = (int)getWidth();
		
			this.wallHeight = 40;
			this.wallWidth = 200;
		
			this.wall1Bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wall_image), dP(this.wallWidth), dP(this.wallHeight), true);
			this.wall2Bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wall_image), dP(this.wallWidth), dP(this.wallHeight), true);
			this.wall3Bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wall_image), dP(this.wallWidth), dP(this.wallHeight), true);
			
			//create bitmaps for box level
			this.wallHeight = 40;
			this.wallWidth = 300;
			this.topOfBoxBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wall_image), dP(this.wallWidth), dP(this.wallHeight), true);
			this.bottomOfBoxBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wall_image), dP(this.wallWidth), dP(this.wallHeight), true);
			
			this.wallHeight = 400;
			this.wallWidth = 40;
			this.rightOfBoxBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wall_image), dP(this.wallWidth), dP(this.wallHeight), true);
			this.leftOfBoxBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wall_image), dP(this.wallWidth), dP(this.wallHeight), true);
			
			//create bitmaps for plusGrid level
			//create bitmap for vertical wall
			this.wallHeight = this.deviceScreenHeight - 100;
			this.wallWidth = 40;
			this.verticalWallBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.plus_level_wall_image), dP(this.wallWidth), dP(this.wallHeight), true);
			//create bitmap for horizontal wall
			this.wallHeight = 40;
			this.wallWidth = this.deviceScreenWidth;
			this.horizontalWallBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.plus_level_wall_image), dP(this.wallWidth), dP(this.wallHeight), true);

			
			
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

		
		//(int id, int xP, int yP, int height, int width, Bitmap sourceImage, Player player)
		this.healthBar = new HealthBar(2, dP(100), dP(40), dP(14), dP(100), BitmapFactory.decodeResource(getResources(), R.drawable.health_bar_image_green), BitmapFactory.decodeResource(getResources(), R.drawable.health_bar_image_grey), this.player);
	
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
		//this.wallList.add(new Wall(this.wallList.size(), dP(200), dP(200), dP(30), dP(100), Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wall_image), dP(100), dP(200), true)));

		this.numTicks = 0;
	}

	@Override
	public void onDraw(Canvas c) {
		
		this.startTime = System.currentTimeMillis();
		
		//add scaled walls the first time onDraw code is traversed
		if(this.endTime == 0) {
			this.wallHeight = 20;
			this.wallWidth = 150;
			
			//int id, int xP, int yP, int height, int width, Bitmap sourceImage, boolean moving, int initialVelocityX, int initialVelocityY, int xMin, int xMax, int yMin, int yMax)
			this.wallList.add(new Wall(this.wallList.size(), this.deviceScreenWidth/2, dP(180), dP(this.wallHeight), dP(this.wallWidth), this.wall1Bitmap, false));
		}
		
		if((this.player.score >= 10) && !this.wall3Added) {
			this.wallHeight = 20;
			this.wallWidth = 150;
			this.wallList.add(new Wall(this.wallList.size(), this.deviceScreenWidth/2, dP(580), dP(this.wallHeight), dP(this.wallWidth), this.wall3Bitmap, false));
			this.wall3Added = true;
		}
		if((this.player.score >= 20) && !this.wall2Added) {
			this.wallHeight = 20;
			this.wallWidth = 150;
			this.wallList.add(new Wall(this.wallList.size(), this.deviceScreenWidth/2, dP(380), dP(this.wallHeight), dP(this.wallWidth), this.wall2Bitmap, true, 1, 0, (this.deviceScreenWidth/2) - dP(140), (this.deviceScreenWidth/2) + dP(140), dP(380), dP(380)));
			this.wall2Added = true;
		}
		if((this.player.score >= 50) && !this.wallBoxAdded) {
			
			this.wallList.clear();
			
			this.wallHeight = 40;
			this.wallWidth = 300;
			//top of box
			this.wallList.add(new Wall(this.wallList.size(), this.deviceScreenWidth/2, dP(180), dP(this.wallHeight), dP(this.wallWidth), this.topOfBoxBitmap, false));
			//bottom of box
			this.wallList.add(new Wall(this.wallList.size(), this.deviceScreenWidth/2, dP(620), dP(this.wallHeight), dP(this.wallWidth), this.bottomOfBoxBitmap, false));
			
			
			this.wallHeight = 400;
			this.wallWidth = 40;
			//left of box
			this.wallList.add(new Wall(this.wallList.size(), this.deviceScreenWidth/2 - dP(130), dP(400), dP(this.wallHeight), dP(this.wallWidth), this.leftOfBoxBitmap, false));
			//right of box
			this.wallList.add(new Wall(this.wallList.size(), this.deviceScreenWidth/2 + dP(130), dP(400), dP(this.wallHeight), dP(this.wallWidth), this.rightOfBoxBitmap, false));
			
			this.wallBoxAdded = true;
			
			//set player position inside of box
			this.player.setxPosition(this.deviceScreenWidth/2);
			this.player.setyPosition(dP(400));
		}
		if((this.player.score >= 53) && !this.original3WallsReAdded) {
			//remove box walls
			this.wallList.clear();
			
			//add back original 3 walls
			this.wallHeight = 20;
			this.wallWidth = 150;
			this.wallList.add(new Wall(this.wallList.size(), this.deviceScreenWidth/2, dP(180), dP(this.wallHeight), dP(this.wallWidth), this.wall1Bitmap, false));
			this.wallList.add(new Wall(this.wallList.size(), this.deviceScreenWidth/2, dP(580), dP(this.wallHeight), dP(this.wallWidth), this.wall3Bitmap, false));
			this.wallList.add(new Wall(this.wallList.size(), this.deviceScreenWidth/2, dP(380), dP(this.wallHeight), dP(this.wallWidth), this.wall2Bitmap, true, 1, 0, (this.deviceScreenWidth/2) - dP(140), (this.deviceScreenWidth/2) + dP(140), dP(380), dP(380)));
			
			this.original3WallsReAdded = true;
		}
		if((this.player.score >= 30) && !this.plusGridLevelDisplayed) {
			
			//add horizontal wall
			this.wallHeight = dP(40);
			this.wallWidth = this.deviceScreenWidth;
			this.wallList.add(new Wall(this.wallList.size(), this.deviceScreenWidth/2, dP(this.wallHeight/2) + (dP(this.player.getHeight())), dP(this.wallHeight), dP(this.wallWidth), this.horizontalWallBitmap, true, 0, 1, this.deviceScreenWidth/2, this.deviceScreenWidth/2, dP(this.wallHeight/2) + dP(this.player.getHeight()), this.deviceScreenHeight - dP(100) - dP(this.wallHeight/2) - dP(this.player.getHeight())));
			
			//this.wallList.add(new Wall(this.wallList.size(), dP(100), dP(100), dP(100), dP(200), this.horizontalWallBitmap, true, 0, 1, dP(100), dP(100), dP(100), dP(400)));
			
			//add vertical wall
			this.wallHeight = this.deviceScreenHeight - dP(100);
			this.wallWidth = dP(40);
			this.wallList.add(new Wall(this.wallList.size(), dP(this.wallWidth/2) + dP(this.player.getWidth()), this.deviceScreenHeight/2, dP(this.wallHeight), dP(this.wallWidth), this.verticalWallBitmap, true, 1, 0, dP(this.wallWidth/2) + dP(this.player.getWidth()), this.deviceScreenWidth - dP(this.wallWidth/2) - dP(this.player.getWidth()), this.deviceScreenHeight/2, this.deviceScreenHeight/2));
			
			this.plusGridLevelDisplayed = true;
		}
		if((this.player.score >= 40) && !this.plusGridLevelReset) {
			this.wallList.remove(this.wallList.size()-1);
			this.wallList.remove(this.wallList.size()-1);
			this.plusGridLevelReset = true;
		}
		
		
		
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
		
		//spawn normal ghosts
		if(numTicks % 100 == 0) {
			
			int ghostX = 0;
			int ghostY = 0;
			
			//keep trying to generate a new ghost while there is a spawnConflict
			while(this.spawnConflict & (this.numSpawnAttempts <= this.maxNumSpawnAttempts)) {	
				
				//generate random position within screen of game
				ghostX = (int)(Math.random() * this.getWidth());
				ghostY = (int)(Math.random() * this.getHeight());
				
				//create new ghost with random position and add to ghostList
				this.ghostList.add(new Ghost(ghostList.size(), ghostX, ghostY, dP(40), dP(40), BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image), this.player, 5));
				
				//check for collision conflicts with current game objects on screen (collision means spawnConflict is present)
				int numConflicts = 0;
				//ghost and layer
				if(CollisionBox.checkCollision(this.player, this.ghostList.get(this.ghostList.size()-1)) != 0) {
					numConflicts += 1;
				}
				//ghost and walls
				for(Wall wall : this.wallList) {
					if((CollisionBox.checkCollision(this.ghostList.get(this.ghostList.size()-1), wall) != 0)) {
						numConflicts += 1;
					}
				}
				//ghost and other ghosts
				for(int i = 0; i < (this.ghostList.size() - 2); i++) {
					if((CollisionBox.checkCollision(this.ghostList.get(this.ghostList.size()-1), this.ghostList.get(i)) != 0)) {
						numConflicts += 1;
					}
				}
				//ghost and powerUps
				for(PowerUp powerUp : this.powerUpList) {
					if((CollisionBox.checkCollision(this.ghostList.get(this.ghostList.size()-1), powerUp) != 0)) {
						numConflicts += 1;
					}
				}
				//ghost and friendlyGhost
				for(FriendlyGhost friendlyGhost : this.friendlyGhostList) {
					if((CollisionBox.checkCollision(friendlyGhost, this.ghostList.get(this.ghostList.size()-1)) != 0)) {
						numConflicts += 1;
					}
				}
				
				if(numConflicts == 0) {
					this.spawnConflict = false;
					//reset numSpawnAttempts to 0
					this.numSpawnAttempts = 0;
				} else {
					//remove newGhost from end of list
					this.ghostList.remove(this.ghostList.size() -1);
					//increment numSpawnAttempts
					this.numSpawnAttempts += 1;
				}
			}
			//reset spawnConflict so more ghosts can be spawned at next iterations
			if(this.spawnConflict == false) {
				this.spawnConflict = true;
			}
			//reset numSpawnAttempts to 0
			if(this.numSpawnAttempts != 0) {
				this.numSpawnAttempts = 0;
			}

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
		if((this.player.getScore() > 0) && (this.player.getScore() % 10 == 0) && (this.numTicks % 150 == 0)) {
			int ghostX = 0;
			int ghostY = 0;
			
			//keep trying to generate a new ghost while there is a spawnConflict
			while(this.spawnConflict & (this.numSpawnAttempts <= this.maxNumSpawnAttempts)) {	
				
				//generate random position within screen of game
				ghostX = (int)(Math.random() * this.getWidth());
				ghostY = (int)(Math.random() * this.getHeight());
				
				//create new bigGhost with random position and add to ghostList
				this.ghostList.add(new Ghost(ghostList.size(), ghostX, ghostY, dP(60), dP(60), BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image_red), this.player, 10));

				
				//check for collision conflicts with current game objects on screen (collision means spawnConflict is present)
				int numConflicts = 0;
				//ghost and layer
				if(CollisionBox.checkCollision(this.player, this.ghostList.get(this.ghostList.size()-1)) != 0) {
					numConflicts += 1;
				}
				//ghost and walls
				for(Wall wall : this.wallList) {
					if((CollisionBox.checkCollision(this.ghostList.get(this.ghostList.size()-1), wall) != 0)) {
						numConflicts += 1;
					}
				}
				//ghost and other ghosts
				for(int i = 0; i < (this.ghostList.size() - 2); i++) {
					if((CollisionBox.checkCollision(this.ghostList.get(this.ghostList.size()-1), this.ghostList.get(i)) != 0)) {
						numConflicts += 1;
					}
				}
				//ghost and powerUps
				for(PowerUp powerUp : this.powerUpList) {
					if((CollisionBox.checkCollision(this.ghostList.get(this.ghostList.size()-1), powerUp) != 0)) {
						numConflicts += 1;
					}
				}
				//ghost and friendlyGhost
				for(FriendlyGhost friendlyGhost : this.friendlyGhostList) {
					if((CollisionBox.checkCollision(friendlyGhost, this.ghostList.get(this.ghostList.size()-1)) != 0)) {
						numConflicts += 1;
					}
				}
				
				if(numConflicts == 0) {
					this.spawnConflict = false;
					//reset numSpawnAttempts to 0
					this.numSpawnAttempts = 0;
				} else {
					//remove newGhost from end of list
					this.ghostList.remove(this.ghostList.size() -1);
					//increment numSpawnAttempts
					this.numSpawnAttempts += 1;
				}
			}
			//reset spawnConflict so more ghosts can be spawned at next iterations
			if(this.spawnConflict == false) {
				this.spawnConflict = true;
			}
			//reset numSpawnAttempts to 0
			if(this.numSpawnAttempts != 0) {
				this.numSpawnAttempts = 0;
			}
		}

		//conditions for making friendly ghosts
		if(this.numTicks % 1000 == 0 && friendlyGhostList.size() == 0) {
			this.friendlyGhostList.add(new FriendlyGhost(friendlyGhostList.size(), (int)(Math.random() * this.getWidth()), 20, dP(40), dP(40), BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image_red)));
		}

		//conditions for the bomb power-up
		if((this.player.getScore() >= 25) && (this.player.getScore() % 20 == 0) && (this.numTicks % 300 == 0)) {
			
			int bombX = 0;
			int bombY = 0;
			
			//keep trying to generate a new ghost while there is a spawnConflict
			while(this.spawnConflict && (this.numSpawnAttempts <= this.maxNumSpawnAttempts)) {	
				
				//generate random position within screen of game
				bombX = (int)(Math.random() * this.getWidth());
				bombY = (int)(Math.random() * this.getHeight());
				
				//create new bomb with random position and add to ghostList
				this.powerUpList.add(new PowerUp(powerUpType.BOMB, powerUpList.size(), bombX, bombY, dP(40), dP(40), BitmapFactory.decodeResource(getResources(), R.drawable.bomb_sprite)));
				
				//check for collision conflicts with current game objects on screen (collision means spawnConflict is present)
				int numConflicts = 0;
				//player and powerUp
				if(CollisionBox.checkCollision(this.player, this.powerUpList.get(this.powerUpList.size()-1)) != null) {
					numConflicts += 1;
				}
				//walls and powerUp
				for(Wall wall : this.wallList) {
					if((CollisionBox.checkCollision(this.powerUpList.get(this.powerUpList.size()-1), wall) != 0)) {
						numConflicts += 1;
					}
				}
				//other ghosts and powerUp
				for(Ghost ghost : this.ghostList) {
					if(CollisionBox.checkCollision(ghost, this.powerUpList.get(this.powerUpList.size()-1)) != 0) {
						numConflicts += 1;
					}
				}
				//powerUp and other powerUps
				for(int i = 0; i < (this.powerUpList.size() - 2); i++) {
					if((CollisionBox.checkCollision(this.powerUpList.get(this.powerUpList.size()-1), this.powerUpList.get(i)) != 0)) {
						numConflicts += 1;
					}
				}
				//friendlyGhost and powerUp
				for(FriendlyGhost friendlyGhost : this.friendlyGhostList) {
					if((CollisionBox.checkCollision(this.powerUpList.get(this.powerUpList.size()-1), friendlyGhost) != 0)) {
						numConflicts += 1;
					}
				}
				
				if(numConflicts == 0) {
					this.spawnConflict = false;
					this.numSpawnAttempts = 0;
				} else {
					this.powerUpList.remove(this.powerUpList.size() - 1);
					this.numSpawnAttempts += 1;
				}
				
			}
			//reset spawnConflict so more ghosts can be spawned at next iterations
			if(this.spawnConflict == false) {
				this.spawnConflict = true;
			}
			//reset numSpawnAttempts to 0
			if(this.numSpawnAttempts != 0) {
				this.numSpawnAttempts = 0;
			}
			
			
		}

		//conditions to start making heart power-ups
		if((this.player.getScore() >= 15) && (this.player.getScore() % 15 == 0) && (this.numTicks % 300 == 0)) {
			
			int healthX = 0;
			int healthY = 0;
			
			//keep trying to generate a new ghost while there is a spawnConflict
			while(this.spawnConflict && (this.numSpawnAttempts <= this.maxNumSpawnAttempts)) {	
				
				//generate random position within screen of game
				healthX = (int)(Math.random() * this.getWidth());
				healthY = (int)(Math.random() * this.getHeight());
				
				//create new bomb with random position and add to ghostList
				this.powerUpList.add(new PowerUp(powerUpType.HEALTH, powerUpList.size(), healthX, healthY, dP(40), dP(40), BitmapFactory.decodeResource(getResources(), R.drawable.heart_sprite)));
				
				//check for collision conflicts with current game objects on screen (collision means spawnConflict is present)
				int numConflicts = 0;
				//player and powerUp
				if(CollisionBox.checkCollision(this.player, this.powerUpList.get(this.powerUpList.size()-1)) != null) {
					numConflicts += 1;
				}
				//walls and powerUp
				for(Wall wall : this.wallList) {
					if((CollisionBox.checkCollision(this.powerUpList.get(this.powerUpList.size()-1), wall) != 0)) {
						numConflicts += 1;
					}
				}
				//other ghosts and powerUp
				for(Ghost ghost : this.ghostList) {
					if(CollisionBox.checkCollision(ghost, this.powerUpList.get(this.powerUpList.size()-1)) != 0) {
						numConflicts += 1;
					}
				}
				//powerUp and other powerUps
				for(int i = 0; i < (this.powerUpList.size() - 2); i++) {
					if((CollisionBox.checkCollision(this.powerUpList.get(this.powerUpList.size()-1), this.powerUpList.get(i)) != 0)) {
						numConflicts += 1;
					}
				}
				//friendlyGhost and powerUp
				for(FriendlyGhost friendlyGhost : this.friendlyGhostList) {
					if((CollisionBox.checkCollision(this.powerUpList.get(this.powerUpList.size()-1), friendlyGhost) != 0)) {
						numConflicts += 1;
					}
				}
				
				if(numConflicts == 0) {
					this.spawnConflict = false;
					this.numSpawnAttempts = 0;
				} else {
					this.powerUpList.remove(this.powerUpList.size() - 1);
					this.numSpawnAttempts += 1;
				}
				
			}
			//reset spawnConflict so more ghosts can be spawned at next iterations
			if(this.spawnConflict == false) {
				this.spawnConflict = true;
			}
			//reset numSpawnAttempts to 0
			if(this.numSpawnAttempts != 0) {
				this.numSpawnAttempts = 0;
			}
			
		}

		//conditions for the coin power-up (30,30,300)
		if((this.player.getScore() > 30) && (this.player.getScore() % 1 == 30) && (this.numTicks % 300 == 0)) {
//			PowerUp coin = new PowerUp(powerUpType.COIN, powerUpList.size(), (int)(Math.random() * this.getWidth()), (int)(Math.random() * this.getHeight()), dP(40), dP(40), BitmapFactory.decodeResource(getResources(), R.drawable.coin_sprite));
//			Iterator<Wall> iteratorWallList = this.wallList.iterator();
//			while(iteratorWallList.hasNext()) {
//				GamePiece wall = iteratorWallList.next();
//				if(Rect.intersects(coin.getBounds(), wall.getBounds())) {
//					coin.setxPosition((int)(coin.getxPosition() + wall.getWidth() + dP(10)));
//				}
//				if(Rect.intersects(coin.getBounds(), wall.getBounds())) {
//					coin.setyPosition((int)(coin.getyPosition() + wall.getHeight() + dP(10)));
//				}
//			}
//			this.powerUpList.add(coin);
			
			int coinX = 0;
			int coinY = 0;
			
			//keep trying to generate a new ghost while there is a spawnConflict
			while(this.spawnConflict && (this.numSpawnAttempts <= this.maxNumSpawnAttempts)) {	
				
				//generate random position within screen of game
				coinX = (int)(Math.random() * this.getWidth());
				coinY = (int)(Math.random() * this.getHeight());
				
				//create new bomb with random position and add to ghostList
				this.powerUpList.add(new PowerUp(powerUpType.COIN, powerUpList.size(), coinX, coinY, dP(40), dP(40), BitmapFactory.decodeResource(getResources(), R.drawable.coin_sprite)));
				
				//check for collision conflicts with current game objects on screen (collision means spawnConflict is present)
				int numConflicts = 0;
				//player and powerUp
				if(CollisionBox.checkCollision(this.player, this.powerUpList.get(this.powerUpList.size()-1)) != null) {
					numConflicts += 1;
				}
				//walls and powerUp
				for(Wall wall : this.wallList) {
					if((CollisionBox.checkCollision(this.powerUpList.get(this.powerUpList.size()-1), wall) != 0)) {
						numConflicts += 1;
					}
				}
				//other ghosts and powerUp
				for(Ghost ghost : this.ghostList) {
					if(CollisionBox.checkCollision(ghost, this.powerUpList.get(this.powerUpList.size()-1)) != 0) {
						numConflicts += 1;
					}
				}
				//powerUp and other powerUps
				for(int i = 0; i < (this.powerUpList.size() - 2); i++) {
					if((CollisionBox.checkCollision(this.powerUpList.get(this.powerUpList.size()-1), this.powerUpList.get(i)) != 0)) {
						numConflicts += 1;
					}
				}
				//friendlyGhost and powerUp
				for(FriendlyGhost friendlyGhost : this.friendlyGhostList) {
					if((CollisionBox.checkCollision(this.powerUpList.get(this.powerUpList.size()-1), friendlyGhost) != 0)) {
						numConflicts += 1;
					}
				}
				
				if(numConflicts == 0) {
					this.spawnConflict = false;
					this.numSpawnAttempts = 0;
				} else {
					this.powerUpList.remove(this.powerUpList.size() - 1);
					this.numSpawnAttempts += 1;
				}
				
			}
			//reset spawnConflict so more ghosts can be spawned at next iterations
			if(this.spawnConflict == false) {
				this.spawnConflict = true;
			}
			//reset numSpawnAttempts to 0
			if(this.numSpawnAttempts != 0) {
				this.numSpawnAttempts = 0;
			}
			
			
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
					int collisionResult = CollisionBox.checkCollision(fGhost, g);
					if (collisionResult != 0) {
						i.remove();
						iteratorGhostList.remove();
					}
				}
			}
		}
		
		//remove friendly ghost when it gets to the bottom
//		if(friendlyGhostList.size() >0) {
//			for(GamePiece boundry : this.wallList) {
//				if(this.friendlyGhostList.get(0).getBounds().intersect(boundry.getBounds())) {
//					this.friendlyGhostList.clear();
//				}
//			}
//		}

		//check collisions between all walls and player (assume ghost can phase through walls?)
		Iterator<Wall> iteratorWallList = this.wallList.iterator();
		while(iteratorWallList.hasNext()) {
			Wall wall = iteratorWallList.next();
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
		
		//update Walls
		for(Wall wall : this.wallList) {
			wall.update();
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

		//draw score
		Paint paint = new Paint();
		paint.setARGB(255, 255, 0, 0);

		c.drawText("Score: " + this.player.getScore(), dP(200), dP(30), paint);
		c.drawText("Health: " + this.player.getHealth() + "", dP(100), dP(30), paint);


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
		
		if(gameOver == true) {
			
			Handler h = new Handler();
			h.postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(getContext(), GameOverActivity.class);
					((Activity)getContext()).startActivity(intent);
				}
			}, 100);
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
