package com.example.ghosthuntergame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class GameOverView extends View {
	
	private Rect gameOverRect;
	private Bitmap gameOverBitmap;
	
	
//	public int dP(int pixels) {
//		return GameActivity.dP(pixels);
//	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if(w != 0) {
			//integers to create the buttons
			int screenWidth = getWidth();
			int screenHeight = getHeight();

			this.gameOverRect = new Rect(0, 0, screenWidth, screenHeight);
			
			this.gameOverBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ooyl_game_over_image), gameOverRect.width(), gameOverRect.height(), true);	
		}
	}

	public GameOverView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize();
	}

	public GameOverView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public GameOverView(Context context) {
		super(context);
		initialize();
	}
	
	public void initialize() {
		this.gameOverRect = null;
		this.gameOverBitmap = null;
	}
	
	public void onDraw(Canvas c) {
		super.onDraw(c);
		
		c.drawBitmap(this.gameOverBitmap, null, this.gameOverRect, null);
		
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(getContext(), Home.class);
				((Activity)getContext()).startActivity(intent);
			}
		}, 5000);
		
	}

	
	
	
	
}
