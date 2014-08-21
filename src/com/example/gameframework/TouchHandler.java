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
	boolean isTouched;
	boolean newTouch;
	Point touched;
	int touchType;
	
	//Used move touches from the screens coordinates to the games. 
	float scaleX;
	float scaleY;

	
	public TouchHandler(View view, float scaleX, float scaleY){
		touched = new Point();
		this.scaleX=scaleX;
		this.scaleY=scaleY;
		view.setOnTouchListener(this);
		newTouch=false;
	}
	
	//when a touch event accrues
	public boolean onTouch(View v, MotionEvent event) {
		synchronized(this){
			isTouched = true;
			//figure out what kind of event has happened
			switch (event.getAction()){
				case MotionEvent.ACTION_DOWN:
					touchType = TOUCH_DOWN;
					newTouch=true;
					break;
				case MotionEvent.ACTION_MOVE:
					touchType = TOUCH_DRAGGED;
					newTouch=true;
					break;					
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					touchType = TOUCH_UP;
					isTouched = false;
					newTouch=true;
					break;
			}
			
			//save the location of the new event in the games coordinate system
			touched.x = (int) (event.getX()*scaleX);
			touched.y = (int) (event.getY()*scaleY);
			
			return true;
		}
	}
	// is the screen being touched
	public boolean isTouchDown() {
		return isTouched;
	}
	
	//where is the screen being touched
	public Point getTouch(){
		return touched;
	}

	//is this the first time we've seen this finger no the screen since the last time it was removed
	public boolean isNewTouch(){
		return newTouch;
	}
	
	//set the currently touching finger to have been seen
	public void resetNewTouch(){
		newTouch = false;
	}
}
