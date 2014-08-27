package com.example.gameframework;
//test comment

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Game implements Runnable {
	
	
	Thread game_thread = null;
	
	// input output variables
	Renderer render;
	TouchHandler touch_input;
	public Bitmap loaded_bitmap;
	
	
	volatile boolean running = false;
	
	Renderable_Object player;
	Movement_UI UI;

	

	
	
	public static final Point target_screen_size = new Point (1000,500);

	public Game (Context context,Point size){
		
		Bitmap actor;
		
		//Find the amount the locations of things needs to be scaled to fit he screen
		float scaleX = (float)target_screen_size.x / size.x;
		float scaleY = (float)target_screen_size.y / size.y;
		
		
		//Initialize input output variables
		render = new Renderer(context,scaleX,scaleY);
		touch_input = new TouchHandler(render,scaleX,scaleY);
		
		//set up the player 
		load_image("player.gif",context);//load the players image into memory
		actor=loaded_bitmap;// save the players image
		Point frame_size = new Point(32,32);//set the dominations of each animation frame 
		Point display_size = new Point(64,64);//set the dominations of the player to be drawn
		Point start_at = new Point(50,50);//set the location the player is to be drawn
		player= new Renderable_Object (actor,frame_size,display_size, start_at, 3,3);// creat the player object
		player.set_play(Renderable_Object.FORWARD);// set the player to be animated forwards
		
		//setup the UI
		
		Point UI_size= new Point(292,292);//(584,584);//////()
		Point UI_at=new Point(170,250);//(340,542);
		UI=new Movement_UI(UI_size,UI_at, Movement_UI.RECTANGLE);//(Point displaySize, Point drawAt, int shape){
		UI.setup_ui(this, context);
	}
	public Renderer get_renderer(){
		// get the object responsible for drawing the screen
		return render;
	}
	@Override
	public void run() {
		//set the start time
		float last_frame = System.nanoTime();
		float next_frame = last_frame;
		
		//define the amount of movement the player gets from clicking the arrow
		//Point movingSpeed= new Point (0,-20);
		
		//main game loop
		while (running){
			// if we cann't draw to the screen try again
			if(!render.isValid())
		 		{continue;}
			
			//lock the images to be drawn so we have time to draw on it
			render.lock_frame();
			
			// determain the amount of time that has passes since the last frame was drawn
			next_frame = System.nanoTime();
			player.update_frame((next_frame-last_frame)/1000000000);
			
			// "game" mechanics/*
			if (touch_input.isNewTouch()){
				Point touch = touch_input.getTouch();
				UI.reset_button_click_state();
				if (UI.is_clicked(touch)){
					touch.x=touch.x-(UI.center.x-(UI.display_size.x/2));
					touch.y=touch.y-(UI.center.y-(UI.display_size.y/2));
					UI.on_click(player,touch,20,touch_input.is_touch_down());
				}
				touch_input.resetNewTouch();
			}

			// update the last frame time to be the newly drawn one
			last_frame=next_frame;
			
			//tell the renderables to draw themselves
			player.draw(render);
			UI.draw(render);
			
			//allow the newly drawn frame to be drawn to the screen
			render.unlockFrame();
		}
	}
	 public void pause(){
		 running = false;// stop the game from running
		 //do something with that thread
		 while (true){
			 try {
				 game_thread.join();
				 return;
			 }catch(InterruptedException e){
				 
			 }
		 }
	 }
	 
	 public void resume (){
		 running = true;//get the game running again
		 game_thread = new Thread(this);// get a new thread 
		 game_thread.start();// start that thread
	 }
	 
	public void load_image(String file_name,Context context){
		loaded_bitmap=null;// clear out the bitmapLoader
		
		//loading an image stuff
		InputStream input_stream;
		 try{
				AssetManager assetManager = context.getAssets();// get the location of the image folder?
				input_stream = assetManager.open("Pics/"+file_name);// load the image into the inputStream
				loaded_bitmap = BitmapFactory.decodeStream(input_stream);// convert the image into a bitmap?
				input_stream.close();
								
			}catch (IOException e){
				
			}finally{
				
			}
	}
}
