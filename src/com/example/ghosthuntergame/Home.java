package com.example.ghosthuntergame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Button startButton = (Button)findViewById(R.id.buttonStart);
		startButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				launchGame();
				
			}
		});
	}

	public void launchGame() {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
}
