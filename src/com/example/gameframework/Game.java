package com.example.gameframework;
//test comment

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

public class Game implements Runnable {
	
	
	Thread game_thread = null;
	
	// input output variables
	Renderer render;
	TouchHandler touch_input;
	public Bitmap loaded_bitmap;
	
	
	volatile boolean running = false;
	
	Movement_UI UI;
	Stage play_area;

	
	
	public static final Point target_screen_size = new Point (1000,500);

	public Game (Context context,Point size){
	
		//Find the amount the locations of things needs to be scaled to fit he screen
		float scaleX = (float)target_screen_size.x / size.x;
		float scaleY = (float)target_screen_size.y / size.y;
		
		//Initialize input output variables
		render = new Renderer(context,scaleX,scaleY);
		touch_input = new TouchHandler(render,scaleX,scaleY);
		
		//setup the HUD
		Point UI_size= new Point(292,292);
		Point UI_at=new Point(170,250);
		UI=new Movement_UI(UI_size,UI_at, Movement_UI.RECTANGLE);//(Point displaySize, Point drawAt, int shape){
		UI.setup_ui(this, context);
		
		//Setup the play area
		Point start_at = new Point(50,50);//set the location the player is to be drawn
		Point display_size= new Point(500, 500);
		play_area=new Stage(display_size,start_at, Stage.RECTANGLE,this,context);
		
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
			play_area.update_frame((next_frame-last_frame)/1000000000);
			// "game" mechanics/*
			if (touch_input.isNewTouch()){
				Point touch = touch_input.getTouch();
				UI.reset_button_click_state();
				if (UI.is_clicked(touch)){
					UI.on_click(play_area,touch,20,touch_input.is_touch_down());
				}
				touch_input.resetNewTouch();
			}

			// update the last frame time to be the newly drawn one
			last_frame=next_frame;
			
			//tell the renderables to draw themselves
			UI.draw(render);
			play_area.draw(render);
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
