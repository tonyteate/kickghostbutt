package com.example.ghosthuntergame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
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
		// TODO Auto-generated constructor stub
	}

public void addGamePiece (GamePiece g) {
	this.gamePieces.add(g);
}

@Override
public void onDraw(Canvas c) {
	super.onDraw(c);
	for(GamePiece g : this.gamePieces) {
		g.draw(c);
	}
}
	
	
}
