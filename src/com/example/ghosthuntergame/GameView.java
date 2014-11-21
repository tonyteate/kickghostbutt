package com.example.ghosthuntergame;

import java.util.ArrayList;

import android.content.Context;
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

	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GameView(Context context) {
		super(context);
		this.player = new Player(1, 0, 0, 50, 50, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		this.addGamePiece(player);
		this.numTicks = 0;
		
		
		// TODO Auto-generated constructor stub
	}

	public void addGamePiece (GamePiece g) {
		this.onScreenObjects.add(g);
	}

	@Override
	public void onDraw(Canvas c) {
		super.onDraw(c);
		

		numTicks += 1;
		
		Paint paint = new Paint();
		paint.setARGB(255, 255, 0, 0);
		
		
		//left, top, right, bottom
		//each button is a 50x50 pixel rectangle
		
		//left
		c.drawRect(new Rect(10, this.getHeight() - 60, 60, this.getHeight() - 10 ), paint);
		//down
		c.drawRect(new Rect(70, this.getHeight() - 60, 120, this.getHeight() - 10 ), paint);
		//up
		c.drawRect(new Rect(130, this.getHeight() - 60, 180, this.getHeight() - 10 ), paint);
		//right
		c.drawRect(new Rect(190, this.getHeight() - 60, 240, this.getHeight() - 10 ), paint);
		
		//call this code every 1000 clock cycles
		
		if(numTicks%100 == 0) {
			this.onScreenObjects.add(new Ghost(onScreenObjects.size(), (int)(Math.random() * this.getWidth()), (int)(Math.random() * this.getHeight()), 40, 40, BitmapFactory.decodeResource(getResources(), R.drawable.ghost_object_image), this.player));
			
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
		if((x > 10) && (x < 60) && (y < this.getHeight()-10) && (y > this.getHeight()-60)) {
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
		if((x > 70) && (x < 120) && (y < this.getHeight()-10) && (y > this.getHeight()-60)) {
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
		if((x > 130) && (x < 180) && (y < this.getHeight()-10) && (y > this.getHeight()-60)) {
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
		if((x > 190) && (x < 240) && (y < this.getHeight()-10) && (y > this.getHeight()-60)) {
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
