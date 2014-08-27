package com.example.gameframework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Renderer extends SurfaceView {
	
	//screen drawing variables
	SurfaceHolder holder;
	Canvas canvas;
	
	//Screen scaling variables
	Rect dstRect;
	float scaleX, scaleY;
	
	
	public Renderer(Context context){
		super(context);
	}
	
	public Renderer(Context context, float scaleX, float scaleY) {
		super(context);
		 holder = getHolder();
		 this.scaleX = scaleX;
		 this.scaleY = scaleY;
		 dstRect = new Rect();
	}
	
	//check to see if the surface is valid for drawing
	public boolean isValid(){
		return holder.getSurface().isValid();
	}
	
	//lock the frame so you can drawn on it
	public void lock_frame(){
		canvas =  holder.lockCanvas();
		canvas.drawRGB(0,0,0);//wipe the screen
	}
	
	//draw to the screen
	public void draw (Bitmap bitmap, Rect draw_from,Rect draw_at){
		//scale the incoming destination to the screens coordinate system. 
		dstRect.set((int)(draw_at.left   / scaleX),
				    (int)(draw_at.top    / scaleY),
				    (int)(draw_at.right  / scaleX),
				    (int)(draw_at.bottom / scaleY));
		
		//draw to the canvas
		canvas.drawBitmap(bitmap, draw_from, dstRect,null);//draw the scaled image
	}
	
	//unlock the frame so it can be drawn on the screen
	public void unlockFrame(){
		holder.unlockCanvasAndPost(canvas);
		
	}
	
}
