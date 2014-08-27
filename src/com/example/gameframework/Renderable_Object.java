package com.example.gameframework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;

public class Renderable_Object {
	//animation direction constants
	public static final int FORWARD  =  1;
	public static final int BACKWARD = -1;
	
	//animation frame variables
	int frame_count			= 0;	
	int current_frame		= 0;
	int animation_direction = 0; 
	int current_animation	= 0;
	float ticker			= 0;
	float frames_per_second	= 0;
	
	//center and dominations of the image
	Point center        	= new Point();
	Point display_size    	= new Point();
	Point source_size    	= new Point();
	Rect  destination    	= new Rect();
	Rect  source_location	= new Rect();
	
	//Imamge
	Bitmap image;
	
	public void draw (Renderer draw_to){
		
		draw_to.draw(image, source_location,destination);
	}
	public void draw (Canvas draw_to){
		
		draw_to.drawBitmap(image, source_location,destination,null);
	}
	public Renderable_Object(Bitmap source_image, Point frame_size,Point display_size, Point draw_at, int frames,int frames_per_second){
		
		//set up images sizes for bother the source and destination
		image=source_image;
		frame_count = frames;
		this.source_size.set (frame_size.x,frame_size.y);
		this.display_size.set(display_size.x,display_size.y);
		
		//set up remaining class variables. 
		this.frames_per_second=frames_per_second;
		move_to(draw_at);
		update_frame();
	}
public Renderable_Object(Point frame_size,Point display_size, Point draw_at, int frames,int frames_per_second){
		
		//set up images sizes for bother the source and destination
		image=Bitmap.createBitmap(display_size.x, display_size.y, Config.ARGB_8888);
		frame_count = frames;
		this.source_size.set (frame_size.x,frame_size.y);
		this.display_size.set(display_size.x,display_size.y);
		
		//set up remaining class variables. 
		this.frames_per_second=frames_per_second;
		move_to(draw_at);
		update_frame();
	}
	// move time forward
	public void update_frame(float delta_time){
		//check if the frame needs updated
		ticker += delta_time;
		if (ticker*frames_per_second >=1)
		{
			//reset time and move frame
			ticker = 0;
			current_frame+=animation_direction;
			
			//Check for the ends of the animations and loop if needed
			if (current_frame >= frame_count)
				{current_frame = 0;}
			if (current_frame < 0)
				{current_frame = frame_count-1;}
			update_frame();
		}
	}
	
	//set the direction the animation plays Without calling this the animation will not play
	public void set_play (int animation_direction){
		this.animation_direction=animation_direction;
	}
	
	// Update the frame for animation
	public void update_frame(){
		source_location.set(current_frame*source_size.x, current_animation * source_size.y,(current_frame+1)*source_size.x,(current_animation+1) * source_size.y );
	}
	
	//set the frame, useful for reseting animations
	public void set_frame(int frame){
		current_frame=frame;
		update_frame();
	}
	
	//Change the animation being played
	public void set_animation(int animation){
		current_animation = animation;
		update_frame();
	}
	
	// get the location on the source to be displayed
	public Rect get_source_location(){
		return source_location;
	}
	
	//get the location on the screen to display
	public Rect get_destination(){
		return destination;
	}
	
	//move the location that the image is being drawn
	public void move_to(Point center){
		this.center.set( center.x, center.y);
		destination.set(center.x - display_size.x/2, center.y - display_size.y/2, center.x+ display_size.x/2, center.y +display_size.y/2);
	}
	
	//move the location that the image is being drawn
	public void set_size(int left, int top, int right ,int bottom){
		//redefine the draw location
		destination.set( left,  top,  right , bottom);

		// update the display size
		display_size.x= Math.abs(right-left)/2;
		display_size.y= Math.abs(bottom-top)/2;
		
		//update the center of the image
		center.set((left+right)/2,(top + bottom) /2);
		
	}
	
	//move the location that the image is being drawn
	public void set_destination(Point top, Point bottom){
		set_size( top.x,  top.y,  bottom.x , bottom.y);	
	}
	
	//move the location that the image is being drawn
	public void set_destination (Rect display){
		set_size(display.left, display.top, display.right, display.left);
	}
	
	//move the location that the image is being drawn
	public void move(Point amount){
		center.x += amount.x;
		center.y += amount.y;
		
		destination.set(center.x - display_size.x/2, center.y - display_size.y/2, center.x+ display_size.x/2, center.y +display_size.y/2);
	}
	public void move(int mx, int my){
		center.x += mx;
		center.y += my;
		
		destination.set(center.x - display_size.x/2, center.y - display_size.y/2, center.x+ display_size.x/2, center.y +display_size.y/2);
	}
}

