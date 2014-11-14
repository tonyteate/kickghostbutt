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
	
	private ArrayList<GamePiece> gamePieces = new ArrayList<GamePiece>();

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
		
		GamePiece player = new GamePiece(1, 0, 0, new Rect(0, 0, 50, 50), BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		this.addGamePiece(player);
		
		GamePiece downButton = new GamePiece(2, 200, 200, new Rect(0, 0, 37, 39), BitmapFactory.decodeResource(getResources(), R.drawable.down_button_image));
		this.addGamePiece(downButton);
		// TODO Auto-generated constructor stub
	}

	public void addGamePiece (GamePiece g) {
		this.gamePieces.add(g);
	}

	@Override
	public void onDraw(Canvas c) {
		super.onDraw(c);
		
		//update fields of gamePieces in GameView
		//each GamePiece has a method called update
		//in upd
//		for(GamePiece g : this.gamePieces) {
//			g.update();
//		}
		
		for(GamePiece g : this.gamePieces) {
			g.draw(c);
		}
	}

	
}
