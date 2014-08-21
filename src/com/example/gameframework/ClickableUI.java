package com.example.gameframework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class ClickableUI extends ClickableObject {
	Bitmap buttonImage;
	Bitmap up_image;
	Bitmap down_image;
	Bitmap left_image;
	Bitmap right_image;
	Bitmap stop_image;
	Bitmap full_UI;
	
	Canvas draw_UI;
	
	ClickableObject button_up;
	ClickableObject button_down;
	ClickableObject button_left;
	ClickableObject button_right;
	ClickableObject button_stop;
	Renderer render;
	
	
	public ClickableUI(Point displaySize, Point drawAt, int shape){
		super(displaySize, displaySize, drawAt, 1, 1, shape);
		
		
	}
	public void setup_ui(Game game,Context context){
		//set up the buttons
		Point frameDom= new Point(64, 64);
		Point button_size= new Point(64, 64);
		
		//set up Forward
		game.loadImage("Forward.png",context);//load the players image into memory
		up_image=game.bitmapLoader;// save the players image
		Point buttonAt = new Point(148,58);//set the location the player is to be drawn
		button_up= new ClickableObject (frameDom,button_size, buttonAt, 1,1, ClickableObject.RECTANGLE);// creat the button
		
		//set up Backward
		game.loadImage("Backward.png",context);//load the players image into memory
		down_image=game.bitmapLoader;// save the players image
		buttonAt.set(148,238);//set the location the player is to be drawn
		button_down= new ClickableObject (frameDom,button_size, buttonAt, 1,1, ClickableObject.RECTANGLE);// creat the player object
		
		//set up Turn left
		game.loadImage("LeftTurn.png",context);//load the players image into memory
		left_image=game.bitmapLoader;// save the players image
		buttonAt.set(58,148);//set the location the player is to be drawn
		button_left= new ClickableObject (frameDom,button_size, buttonAt, 1,1, ClickableObject.RECTANGLE);// creat the player object
		
		//set up Turn Right
		game.loadImage("RightTurn.png",context);//load the players image into memory
		right_image=game.bitmapLoader;// save the players image
		buttonAt.set(238,148);//set the location the player is to be drawn
		button_right= new ClickableObject (frameDom,button_size, buttonAt, 1,1, ClickableObject.RECTANGLE);// creat the player object
		
		//set up Stop
		game.loadImage("Stop.png",context);//load the players image into memory
		stop_image=game.bitmapLoader;// save the players image
		buttonAt.set(148,148);//set the location the player is to be drawn
		button_stop= new ClickableObject (frameDom,button_size, buttonAt, 1,1, ClickableObject.RECTANGLE);// creat the player object
		
		full_UI=Bitmap.createBitmap(displaySize.x,displaySize.y,Config.ARGB_4444);
		draw_UI=new Canvas(full_UI);
		
		//draw_UI.setBitmap(full_UI);
		render=new Renderer(context,1,1);
	}
	public void Update_UI( ){
		draw_UI.drawRGB(0,0,0);
		
		//draw to the canvas
		drawUI(up_image, button_up);//draw the scaled image
		drawUI(up_image,button_up);
		drawUI(down_image,button_down);
		drawUI(left_image,button_left);
		drawUI(right_image,button_right);
		drawUI(stop_image,button_stop);
	}
	
	public void drawUI (Bitmap bitmap, RenderableObject toDraw){
		//scale the incoming destination to the screens coordinate system. 
		
		//draw to the canvas
		draw_UI.drawBitmap(bitmap, toDraw.getScr(), toDraw.getDst(), null);
	}
	public void onClick(RenderableObject player, Point touch, int speed) {
		// TODO Auto-generated method stub
		if (button_up.isClicked(touch)){
			player.move(0,-speed);
		}else if (button_down.isClicked(touch)){
			player.move(0,speed);
		}else if (button_left.isClicked(touch)){
			player.move(-speed,0);
		}else if (button_right.isClicked(touch)){
			player.move(speed,0);
		}
		
	}
		// button_up;
		// button_down;
		// button_left;
		// button_right;
		// button_stop;
}
