package com.example.ghosthuntergame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameOverView extends View {
	
	private Rect gameOverRect;
	private Bitmap gameOverBitmap;
	private int score;
	private Paint scorePaint;

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if(w != 0) {
			//integers to create the buttons
			int screenWidth = getWidth();
			int screenHeight = getHeight();

			this.gameOverRect = new Rect(0, GameActivity.dP(20), screenWidth, screenHeight);
			
			this.gameOverBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ooyl_game_over_image), gameOverRect.width(), gameOverRect.height() - GameActivity.dP(20), true);	
		}
	}

	public GameOverView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize(0);
	}

	public GameOverView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(0);
	}

	public GameOverView(Context context, int score) {
		super(context);
		initialize(score);
	}
	
	public void initialize(int score) {
		this.gameOverRect = null;
		this.gameOverBitmap = null;
		this.score = score;
		
		this.setBackgroundColor(Color.WHITE);
		
		scorePaint = new Paint();
		scorePaint.setARGB(255, 255, 0, 0);
		scorePaint.setTypeface(Typeface.DEFAULT);		
		scorePaint.setTextSize(GameActivity.dP(20));
	}
	
	public void onDraw(Canvas c) {
		super.onDraw(c);
		
		c.drawBitmap(this.gameOverBitmap, null, this.gameOverRect, null);
		c.drawText("Your Score: " + String.valueOf(this.score), 0, GameActivity.dP(20), scorePaint);
		
//		Handler h = new Handler();
//		h.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				Intent intent = new Intent(getContext(), Home.class);
//				((Activity)getContext()).startActivity(intent);
//			}
//		}, 5000);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		Intent intent = new Intent(getContext(), Home.class);
		((Activity)getContext()).startActivity(intent);	
		return true;
	}

	
	
	
	
}
