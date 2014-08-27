package com.example.gameframework;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import com.example.gameframework.Game;

public class Frame extends Clickable_Object {
	
	Canvas draw_UI;
	Vector<Clickable_Object> buttons;
	Vector<Renderable_Object> items; 
	Renderer render;

	
	public Frame(Point display_size, Point draw_at, int shape,Context context){
		super(display_size, display_size, draw_at, 1, 1, shape);		
		draw_UI=new Canvas();
		draw_UI.setBitmap(image);
		render=new Renderer(context,1,1);
	}
	public void setup_ui(Game game,Context context,String image_name,Point frame_size,Point button_size,Point display_at){
		//Bitmap tempimage;
		
		//set up Forward
		//game.loadImage(image_name,context);//load the players image into memory
		//tempimage=game.bitmapLoader;// save the players image
		//button_up= new ClickableObject (tempimage,frameDom,button_size, display_at, 2,1, ClickableObject.RECTANGLE);

		Update_UI();
	}
	public void Update_UI( ){
		draw_UI.drawARGB(0, 0, 0, 0);

	}
	
	public void reset_button_click_state(){
	}
	public void on_click(Renderable_Object player, Point touch, int speed, boolean touch_down) {
		
	}
}
