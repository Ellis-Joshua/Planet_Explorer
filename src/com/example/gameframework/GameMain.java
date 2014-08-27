package com.example.gameframework;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

//this is what makes it an android app rather than just a java program
public class GameMain extends Activity {
	Game game;
	@SuppressLint("NewApi") 
	@Override
	public void onCreate (Bundle savedInstanceState){
		//allways call the super when overriding Activity functions
		super.onCreate(savedInstanceState);
		
		//set to full screen mode
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		//set up the game
		game = new Game(this,size);
		
		//tell Android what object will be drawing the screen
		setContentView(game.get_renderer());
	}
	
	@Override
	public void onResume(){
		super.onResume();
		game.resume();// resume the game
	}
	
	public void onPause(){
		super.onPause();
		game.pause();//pause the game
	}
	

}
