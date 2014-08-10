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
	Bitmap bitmapLoader;
	
	
	volatile boolean running = false;
	
	//Player variables
	RenderableObject player;
	Bitmap actor;
	
	//Button variables
	ClickableObject button_up;
	ClickableObject button_down;
	
	Bitmap buttonImage;
	Bitmap up_image;
	Bitmap down_image;
	Bitmap left;
	Bitmap right;
	
	
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
		Point displaySize = new Point(100,100);//set the dominations of the player to be drawn
		Point startAt = new Point(50,50);//set the location the player is to be drawn
		player= new RenderableObject (frameDom,displaySize, startAt, 3,3);// creat the player object
		player.setPlay(RenderableObject.FORWARD);// set the player to be animated forwards
		
		//set up the buttons
		loadImage("Forward.png",context);//load the players image into memory
		up_image=bitmapLoader;// save the players image
		frameDom.set(64, 64);
		Point buttonSize = new Point(100,100);//set the dominations of the player to be drawn
		Point buttonAt = new Point(950,450);//set the location the player is to be drawn
		button_up= new ClickableObject (frameDom,buttonSize, buttonAt, 1,1, ClickableObject.RECTANGLE);// creat the player object

		//set up the buttons
		loadImage("Backward.png",context);//load the players image into memory
		down_image=bitmapLoader;// save the players image
		frameDom.set(64, 64);
		buttonSize = new Point(100,100);//set the dominations of the player to be drawn
		buttonAt = new Point(750,450);//set the location the player is to be drawn
		button_down= new ClickableObject (frameDom,buttonSize, buttonAt, 1,1, ClickableObject.RECTANGLE);// creat the player object

		
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
		Point movingSpeed= new Point (0,-20);
		
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
			
			// "game" mechanics
			if (touchInput.isNewTouch())
			{
				touchInput.resetNewTouch();
				if (button_up.isClicked(touchInput.getTouch())){
					player.move(movingSpeed);//move the player with the button click
				}else if (button_down.isClicked(touchInput.getTouch())){
					player.move(0,20);//move the player with the button click){
						
				}else{
					player.moveTo(touchInput.getTouch());//move the player to the touch event
				}
				
			}

			// update the last frame time to be the newly drawn one
			lastFrame=nextFrame;
			
			//tell the renderables to draw it'selves
			render.draw(actor, player);
			render.draw(up_image,button_up);
			render.draw(down_image,button_down);
			
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
	 
	private void loadImage(String filename,Context context){
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
