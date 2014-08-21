package com.example.gameframework;

import android.graphics.Point;

public class ClickableObject extends RenderableObject{
	// Defined shapes for the button
	public static final int RECTANGLE = 0;
	public static final int CIRCLE = 1;
	
	//The shape of this button
	int shape;
	
	public ClickableObject(Point frameDom, Point displaySize, Point drawAt, int frames, int framesPerSecond, int shape) {
		super(frameDom, displaySize, drawAt, frames, framesPerSecond);
		this.shape = shape;
	}
	
	//check to see if the button was clicked
	public boolean isClicked(Point at){
		switch (shape){
		case RECTANGLE:
			return (at.x >= destination.left && at.x <= destination.right) &&
				   (at.y >= destination.top  && at.y <= destination.bottom);
		case CIRCLE:
			return displaySize.x >= (2*Math.pow(Math.pow(center.x - at.x, 2)+Math.pow(center.y - at.y, 2),2));
		default:
			return false;
		}
	}

}
