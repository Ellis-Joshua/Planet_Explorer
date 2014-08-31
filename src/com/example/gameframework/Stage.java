package com.example.gameframework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;

public class Stage extends Clickable_Object{
	
	Canvas draw_UI;
	Canvas draw_background;
	Bitmap ground;
	Bitmap clear_ground;
	Bitmap rocky_ground;
	int [][]grid;
	Renderable_Object player;
	int screen_buffer = 64;
	
	public Stage(Point display_size, Point draw_at, int shape,Game game,Context context){
		super(display_size, display_size, draw_at, 1, 1, shape);
		
		this.destination.set(340, 0, 840, 500);
		this.source_location.set(64, 64, 512, 512);
		
		//set up the player 
		game.load_image("player.gif",context);//load the players image into memory
		Point frame_size = new Point(32,32);//set the dominations of each animation frame 
		display_size.set(64,64);//set the dominations of the player to be drawn
		Point start_at = new Point(160,160);//set the location the player is to be drawn
		
		player= new Renderable_Object (game.loaded_bitmap,frame_size,display_size, start_at, 3,3);// creat the player object
		player.set_play(Renderable_Object.FORWARD);// set the player to be animated forwards
		
		game.load_image("clear Ground.png", context);
		clear_ground=game.loaded_bitmap;
		
		game.load_image("Rocky Ground.png", context);
		rocky_ground=game.loaded_bitmap;
		
		grid=new int [9][9];
		for (int y=0;y<=8;y++){
			for (int x=0;x<=8;x++){		
				grid[x][y]=0;
			}
		}
		
		draw_UI=new Canvas();
		draw_UI.setBitmap(image);
		ground=Bitmap.createBitmap(576, 576, Config.ARGB_8888);
		draw_background=new Canvas();
		draw_background.setBitmap(ground);
		build_ground();
		update_frame(0);
	}
	public void move_player(int x,int y){
		player.move(x,y);
		//checking if the player is at the end of the displaying screen
		if (player.get_destination().right+screen_buffer >=source_location.right){
			move_sourec_location(player.get_destination().right+screen_buffer-source_location.right,0);
		}
		if (player.get_destination().bottom+screen_buffer>=source_location.bottom){
			move_sourec_location(0,player.get_destination().bottom+screen_buffer-source_location.bottom);
		}
		
		if (player.get_destination().left-screen_buffer<=source_location.left){
			move_sourec_location(player.get_destination().left-screen_buffer-source_location.left,0);
		}
		if (player.get_destination().top-screen_buffer<=source_location.top){
			move_sourec_location(0,player.get_destination().top-screen_buffer-source_location.top);
		}
		boolean reset_screen=false;
		
		//checking if the screen is on off the stage
		if (source_location.top<0){
			move_sourec_location(0,screen_buffer);	
			player.move(0,screen_buffer);	
			reset_screen=true;
		}
		if (source_location.left<0){
			player.move(screen_buffer,0);
			move_sourec_location(screen_buffer,0);	
			reset_screen=true;
			
		}
		if (source_location.bottom+screen_buffer>ground.getHeight()){
			move_sourec_location(0,-screen_buffer);	
			player.move(0,-screen_buffer);	
			reset_screen=true;
		}
		if (source_location.right+screen_buffer>ground.getWidth()){
			move_sourec_location(-screen_buffer,0);			
			player.move(-screen_buffer,0);	
			reset_screen=true;
		}
		
		//check if we need to reload the surrounding area
		if (reset_screen){
			build_ground();
			
			
		}
		update_frame(0);
	}
	
	public void update_frame(float delta_time){
		draw_UI.drawARGB(0, 0, 0, 0);
		Rect draw_from=new Rect(0, 0, 585, 585);
		draw_UI.drawBitmap(ground, draw_from, draw_from,null);
		player.update_frame(delta_time);
		player.draw(draw_UI);
	}
	
	public void build_ground(){
		draw_background.drawARGB(0, 0, 0, 0);
		Rect plot = new Rect();
		Rect draw_from=new Rect(0, 0, 64, 64);
		for (int y=0;y<=8;y++){
			for (int x=0;x<=8;x++){		
				plot.set(x*64, y*64, (x*64)+64, (y*64)+64);
				//plot.set(0, 0, 576,576);
				switch(grid[x][y]){
					case 0:
						draw_background.drawBitmap(rocky_ground, draw_from, plot, null);
						break;
					case 1:
						draw_background.drawBitmap(clear_ground, draw_from, plot,null);
						break;
					default:
						draw_background.drawBitmap(clear_ground, draw_from, plot,null);
				}
			}			
		}
	}
	
}
