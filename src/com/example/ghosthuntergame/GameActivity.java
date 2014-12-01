package com.example.ghosthuntergame;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;

public class GameActivity extends Activity {

	static Context context;
	MediaPlayer player;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GameActivity.context = getApplicationContext();
		
		player = new MediaPlayer();
		final AssetFileDescriptor afd = context.getResources().openRawResourceFd(R.raw.music);

    	player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {				
			@Override
			public void onPrepared(MediaPlayer mp) {
				try {
					player.start();
					afd.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

    	try {
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        player.prepareAsync();
		
		setContentView(new GameView(this));
	}
	
	@Override
	public void onPause(){
		super.onPause();
		
		// kill activity to stop music; maybe deal with pause/resume later?
		finish();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		player.release();
		player = null;
	}

	public static int dP(int pixels) {
		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, r.getDisplayMetrics());
		return (int)px;
	}

}
