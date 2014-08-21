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
	
	
	Thread gameThread = null;
	
	// input output variables
	Renderer render;
	TouchHandler touchInput;
	public Bitmap bitmapLoader;
	
	
	volatile boolean running = false;
	
	//Player variables
	RenderableObject player;
	Bitmap actor;
	ClickableUI UI;
	//Button variables
	

	
	
	public static final Point targetScreenSize = new Point (1000,500);

	public Game (Context context,Point size){
		
		
		
		//Find the amount the locations of things needs to be scaled to fit he screen
		float scaleX = (float)targetScreenSize.x / size.x;
		float scaleY = (float)targetScreenSize.y / size.y;
		
		
		//Initialize input output variables
		render = new Renderer(context,scaleX,scaleY);
		touchInput = new TouchHandler(render,scaleX,scaleY);
		
		//set up the player 
		loadImage("player.gif",context);//load the players image into memory
		actor=bitmapLoader;// save the players image
		Point frameDom = new Point(32,32);//set the dominations of each animation frame 
		Point displaySize = new Point(64,64);//set the dominations of the player to be drawn
		Point startAt = new Point(50,50);//set the location the player is to be drawn
		player= new RenderableObject (frameDom,displaySize, startAt, 3,3);// creat the player object
		player.setPlay(RenderableObject.FORWARD);// set the player to be animated forwards
		
		//setup the UI
		
		Point UI_size= new Point(292,292);//(584,584);//////()
		Point UI_at=new Point(170,250);//(340,542);
		UI=new ClickableUI(UI_size,UI_at, ClickableUI.RECTANGLE);//(Point displaySize, Point drawAt, int shape){
		UI.setup_ui(this, context);
	}
	public Renderer getrenderer(){
		// get the object responsible for drawing the screen
		return render;
	}
	@Override
	public void run() {
		//set the start time
		float lastFrame = System.nanoTime();
		float nextFrame = lastFrame;
		
		//define the amount of movement the player gets from clicking the arrow
		//Point movingSpeed= new Point (0,-20);
		
		//main game loop
		while (running){
			// if we cann't draw to the screen try again
			if(!render.isValid())
		 		{continue;}
			
			//lock the images to be drawn so we have time to draw on it
			render.lockFrame();
			
			// determain the amount of time that has passes since the last frame was drawn
			nextFrame = System.nanoTime();
			player.updateFrame((nextFrame-lastFrame)/1000000000);
			
			// "game" mechanics/*
			if (touchInput.isNewTouch()){
				touchInput.resetNewTouch();
				Point touch = touchInput.getTouch();
				if (UI.isClicked(touch)){
					touch.x=touch.x-(UI.center.x-(UI.displaySize.x/2));
					touch.y=touch.y-(UI.center.y-(UI.displaySize.y/2));
					UI.onClick(player,touch,20);
				}
				
			}

			// update the last frame time to be the newly drawn one
			lastFrame=nextFrame;
			
			//tell the renderables to draw it'selves
			render.draw(actor, player);
			UI.Update_UI();
			render.draw(UI.full_UI,UI);
			
			//allow the newly drawn frame to be drawn to the screen
			render.unlockFrame();
		}
	}
	 public void pause(){
		 running = false;// stop the game from running
		 //do something with that thread
		 while (true){
			 try {
				 gameThread.join();
				 return;
			 }catch(InterruptedException e){
				 
			 }
		 }
	 }
	 
	 public void resume (){
		 running = true;//get the game running again
		 gameThread = new Thread(this);// get a new thread 
		 gameThread.start();// start that thread
	 }
	 
	public void loadImage(String filename,Context context){
		bitmapLoader=null;// clear out the bitmapLoader
		
		//loading an image stuff
		InputStream inputStream;
		 try{
				AssetManager assetManager = context.getAssets();// get the location of the image folder?
				inputStream = assetManager.open("Pics/"+filename);// load the image into the inputStream
				bitmapLoader = BitmapFactory.decodeStream(inputStream);// convert the image into a bitmap?
				inputStream.close();
								
			}catch (IOException e){
				
			}finally{
				
			}
	}
}
