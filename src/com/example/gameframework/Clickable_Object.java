package com.example.gameframework;

import android.graphics.Bitmap;
import android.graphics.Point;

public class Clickable_Object extends Renderable_Object{
	// Defined shapes for the button
	public static final int RECTANGLE = 0;
	public static final int CIRCLE = 1;
	
	//The shape of this button
	int shape;
	
	public Clickable_Object(Point frame_size, Point display_size, Point draw_at, int frames, int frames_per_second, int shape) {
		super(frame_size, display_size, draw_at, frames, frames_per_second);
		this.shape = shape;
	}
	public Clickable_Object(Bitmap source_image,Point frame_size, Point display_size, Point draw_at, int frames, int frames_per_second, int shape) {
		super(source_image,frame_size, display_size, draw_at, frames, frames_per_second);
		this.shape = shape;
	}
	
	//check to see if the button was clicked
	public boolean is_clicked(Point at){
		switch (shape){
		case RECTANGLE:
			return (at.x >= destination.left && at.x <= destination.right) &&
				   (at.y >= destination.top  && at.y <= destination.bottom);
		case CIRCLE:
			return display_size.x >= (2*Math.pow(Math.pow(center.x - at.x, 2)+Math.pow(center.y - at.y, 2),2));
		default:
			return false;
		}
	}

}
