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
	public void lockFrame(){
		canvas =  holder.lockCanvas();
		canvas.drawRGB(0,0,0);//wipe the screen
	}
	
	//draw to the screen
	public void draw (Bitmap bitmap, RenderableObject toDraw){
		//scale the incoming destination to the screens coordinate system. 
		dstRect.set((int)(toDraw.getDst().left   / scaleX),
				    (int)(toDraw.getDst().top    / scaleY),
				    (int)(toDraw.getDst().right  / scaleX),
				    (int)(toDraw.getDst().bottom / scaleY));
		
		//draw to the canvas
		canvas.drawBitmap(bitmap, toDraw.getScr(), dstRect, null);//draw the scaled image
	}
	
	//unlock the frame so it can be drawn on the screen
	public void unlockFrame(){
		holder.unlockCanvasAndPost(canvas);
		
	}
	
}
