package com.example.ghosthuntergame;

import java.util.ArrayList;

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

	private ArrayList<OnScreenObject> onScreenObjects = new ArrayList<OnScreenObject>();
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

	public void addGamePiece (GamePiece g) {
		this.onScreenObjects.add(g);
	}
	
	public void initialize() {
		this.player = new Player(1, dP(50), dP(50), dP(50), dP(50), BitmapFactory.decodeResource(getResources(), R.drawable.top_down_knight));
		this.addGamePiece(player);
		this.numTicks = 0;
		}

	@Override
	public void onDraw(Canvas c) {
		super.onDraw(c);
				
		numTicks += 1;
		
		Paint paint = new Paint();
		paint.setARGB(255, 255, 0, 0);
		
		
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

		
		if(numTicks%100 == 0) {
			this.onScreenObjects.add(new Ghost(onScreenObjects.size(), (int)(Math.random() * this.getWidth()), (int)(Math.random() * this.getHeight()), dP(40), dP(40), BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image), this.player));
		}
		
		if(numTicks%50 == 0) {
			for(int i = 1; i < onScreenObjects.size(); i++) {
				Ghost g = (Ghost)this.onScreenObjects.get(i);
				g.setSpeedX(this.player);
				g.setSpeedY(this.player);
			}
		}

		
		for(OnScreenObject oso : this.onScreenObjects) {
			oso.update();
		}
		for(OnScreenObject oso : this.onScreenObjects) {
			oso.draw(c);
		}
		
		
		invalidate();
	}

	public ArrayList<OnScreenObject> getOnScreenObjects() {
		return this.onScreenObjects;
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
