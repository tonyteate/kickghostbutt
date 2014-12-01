package com.example.ghosthuntergame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		
		Intent intent = getIntent();
		int score = intent.getIntExtra(GameView.PLAYER_SCORE, 0);
		
		TextView tvScore = (TextView) findViewById(R.id.tv_score);
		tvScore.setText(score);
		
	}
	

}
