package com.example.ghosthuntergame;

/*
 * Team ID: 102-08
 * 
 * Team Members: name (computing ID)
 * 		Bradley Barrett (btb8db)
 * 		Tony Teate (aat4aj)
 * 		Garnall Travis (gqt4ev)
 * 		Haley Bowler (hjb2dq)
 * 
 * Sources Cited:
 * 		http://www.unixstickers.com/stickers/retro-gaming-stickers/pacman-ghost-light-blue-inky-shaped-sticker
 * 		http://www.unixstickers.com/stickers/retro-gaming-stickers/pacman-ghost-red-shaped-sticker
 * 		http://imgarcade.com/1/green-pacman-ghost/
 * 		http://stackoverflow.com/questions/5089944/get-current-system-time
 * 		http://developer.android.com/reference/android/graphics/Bitmap.html
 * 		http://developer.android.com/reference/android/view/View.html
 * 		http://stackoverflow.com/questions/4605527/converting-pixels-to-dp
 * 		http://www.javacodegeeks.com/tutorials/android-tutorials/android-game-tutorials/
 * 		http://weknowmemes.com/2012/05/ooyl-only-once-you-live/
 * 		http://www.genealogyintime.com/GenealogyResources/Wallpaper/Brick-Wall-Images/brick-wall-images-page01.html
 * 		http://developer.android.com/reference/android/graphics/Rect.html
 * 		http://developer.android.com/reference/android/graphics/Canvas.html
 * 		http://stackoverflow.com/questions/4039713/how-to-draw-text-on-canvas
 *		http://www.giantbomb.com/forums/general-discussion-30/gb-community-game-night-6725/
 *		http://pixgood.com/super-mario-bros-coin.html
 *		http://makepixelart.com/artists/shagedelic/scary-monsters-and-nice-sprites
 * 
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				launchHome();
			}
		}, 2000);
	}
	
	public void launchHome() {
		Intent intent = new Intent(this, Home.class);
		startActivity(intent);
	}

}
