package com.example.gameframework;

import android.graphics.Point;
import android.graphics.Rect;

public class RenderableObject {
	//animation direction constants
	public static final int FORWARD  =  1;
	public static final int BACKWARD = -1;
	
	//animation frame variables
	int frameCount			= 0;	
	int currentFrame		= 0;
	float ticker			= 0;
	float framesPerSecond	= 0;
	int animationDirection 	= 0; 
	int currentAnimation	= 0;
	
	//center and dominations of the image
	Point center         = new Point();
	Point displaySize    = new Point();
	Rect  destination    = new Rect();
	Point sourceSize     = new Point();
	Rect  sourceLocation = new Rect();
	
	public RenderableObject(Point frameDom,Point displaySize, Point drawAt, int frames,int framesPerSecond){
		
		//set up images sizes for bother the source and destination
		frameCount = frames;
		this.sourceSize.set (frameDom.x,frameDom.y);
		this.displaySize.set(displaySize.x,displaySize.y);
		
		//set up remaining class variables. 
		this.framesPerSecond=framesPerSecond;
		moveTo(drawAt);
		updateFrame();
	}
	
	// move time forward
	public void updateFrame(float deltaTime){
		//check if the frame needs updated
		ticker += deltaTime;
		if (ticker*framesPerSecond >=1)
		{
			//reset time and move frame
			ticker = 0;
			currentFrame+=animationDirection;
			
			//Check for the ends of the animations and loop if needed
			if (currentFrame >= frameCount)
				{currentFrame = 0;}
			if (currentFrame < 0)
				{currentFrame = frameCount-1;}
			updateFrame();
		}
	}
	
	//set the direction the animation plays Without calling this the animation will not play
	public void setPlay (int animationDirection){
		this.animationDirection=animationDirection;
	}
	
	// Update the frame for animation
	public void updateFrame(){
		sourceLocation.set(currentFrame*sourceSize.x, currentAnimation * sourceSize.y,(currentFrame+1)*sourceSize.x,(currentAnimation+1) * sourceSize.y );
	}
	
	//set the frame, useful for reseting animations
	public void setFrame(int frame){
		currentFrame=frame;
		updateFrame();
	}
	
	//Change the animation being played
	public void setAnimation(int animation){
		currentAnimation = animation;
		updateFrame();
	}
	
	// get the location on the source to be displayed
	public Rect getScr(){
		return sourceLocation;
	}
	
	//get the location on the screen to display
	public Rect getDst(){
		return destination;
	}
	
	//move the location that the image is being drawn
	public void moveTo(Point center){
		this.center.set( center.x, center.y);
		destination.set(center.x - displaySize.x/2, center.y - displaySize.y/2, center.x+ displaySize.x/2, center.y +displaySize.y/2);
	}
	
	//move the location that the image is being drawn
	public void setDestination(int left, int top, int right ,int bottom){
		//redefine the draw location
		destination.set( left,  top,  right , bottom);

		// update the display size
		displaySize.x= Math.abs(right-left)/2;
		displaySize.y= Math.abs(bottom-top)/2;
		
		//update the center of the image
		center.set((left+right)/2,(top + bottom) /2);
		
	}
	
	//move the location that the image is being drawn
	public void setDestination(Point top, Point bottom){
		setDestination( top.x,  top.y,  bottom.x , bottom.y);	
	}
	
	//move the location that the image is being drawn
	public void setDestination (Rect display){
		setDestination(display.left, display.top, display.right, display.left);
	}
	
	//move the location that the image is being drawn
	public void move(Point amount){
		center.x += amount.x;
		center.y += amount.y;
		
		destination.set(center.x - displaySize.x/2, center.y - displaySize.y/2, center.x+ displaySize.x/2, center.y +displaySize.y/2);
	}
	public void move(int mx, int my){
		center.x += mx;
		center.y += my;
		
		destination.set(center.x - displaySize.x/2, center.y - displaySize.y/2, center.x+ displaySize.x/2, center.y +displaySize.y/2);
	}
}

