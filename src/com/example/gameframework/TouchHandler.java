package com.example.gameframework;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchHandler  implements OnTouchListener{
	public static final int TOUCH_DOWN = 0;
	public static final int TOUCH_UP = 1;
	public static final int TOUCH_DRAGGED = 2;
	
	//touch event information
	boolean is_touched;
	boolean new_touch;
	Point touched;
	int touch_type;
	
	//Used move touches from the screens coordinates to the games. 
	float scaleX;
	float scaleY;

	
	public TouchHandler(View view, float scaleX, float scaleY){
		touched = new Point();
		this.scaleX=scaleX;
		this.scaleY=scaleY;
		view.setOnTouchListener(this);
		new_touch=false;
	}
	
	//when a touch event accrues
	public boolean onTouch(View v, MotionEvent event) {
		synchronized(this){
			is_touched = true;
			//figure out what kind of event has happened
			switch (event.getAction()){
				case MotionEvent.ACTION_DOWN:
					touch_type = TOUCH_DOWN;
					new_touch=true;
					break;
				case MotionEvent.ACTION_MOVE:
					touch_type = TOUCH_DRAGGED;
					new_touch=true;
					break;					
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					touch_type = TOUCH_UP;
					is_touched = false;
					new_touch=true;
					break;
			}
			
			//save the location of the new event in the games coordinate system
			touched.x = (int) (event.getX()*scaleX);
			touched.y = (int) (event.getY()*scaleY);
			
			return true;
		}
	}
	// is the screen being touched
	public boolean is_touch_down() {
		return is_touched;
	}
	
	//where is the screen being touched
	public Point getTouch(){
		return touched;
	}

	//is this the first time we've seen this finger no the screen since the last time it was removed
	public boolean isNewTouch(){
		return new_touch;
	}
	
	//set the currently touching finger to have been seen
	public void resetNewTouch(){
		new_touch = false;
	}
}
