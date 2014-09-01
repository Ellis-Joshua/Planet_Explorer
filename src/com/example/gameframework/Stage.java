package com.example.gameframework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;

public class Stage extends Clickable_Object{
	
	//player variables 
	Renderable_Object player;
	Point target_player_destination=new Point(160,160);
	int player_max_speed=128;//Pixels per second
	Point player_speed = new Point(0,0);
	float player_x;
	float player_y;
	
	
	Canvas draw_UI;
	Canvas draw_background;
	Bitmap ground;
	Bitmap clear_ground;
	Bitmap rocky_ground;
	int [][]grid;

	int screen_buffer = 64;
	
	public Stage(Point display_size, Point draw_at, int shape,Game game,Context context){
		super(display_size, new Point(800,800), draw_at, 1, 1, shape);
		
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
		image=Bitmap.createBitmap(575, 575, Config.ARGB_8888);
		draw_UI=new Canvas();
		draw_UI.setBitmap(image);
		
		ground=Bitmap.createBitmap(image.getWidth(), image.getHeight(), Config.ARGB_8888);
		draw_background=new Canvas();
		draw_background.setBitmap(ground);
		build_ground();
		update_frame(0);
		player_speed=new Point(0,0);
		target_player_destination=new Point(player.center.x,player.center.y);
		player_speed = new Point(0,0);
		player_x=player.center.x;
		player_y=player.center.y;
	}
	public void set_target_player_destination (int x, int y){
		target_player_destination.set(target_player_destination.x+x, target_player_destination.y+y);
		player_speed.x=0;
		player_speed.y=0;
		if (x<0){
			player_speed.x=-player_max_speed;
		}else if (x>0){
			player_speed.x=player_max_speed;
		}
		if (y>0){
			player_speed.y=player_max_speed;
		}else if (y<0){
			player_speed.y=-player_max_speed;
		}
	
	}
	public void move_player(int in_x,int in_y){
		int x=in_x;
		int y=in_y;
		if (((x>0)&&(player.center.x+x >= target_player_destination.x))||
		    ((x<0)&&(player.center.x+x <= target_player_destination.x))){		
			 x=target_player_destination.x-player.center.x;
			 player_speed.x=0;
			 player_x=target_player_destination.x;
			 
		}
		if (((y>0)&&(player.center.y+y >= target_player_destination.y))||
				((y<0)&&(player.center.y+y <= target_player_destination.y))){
			 y=target_player_destination.y-player.center.y;
			 player_speed.y=0;
			 player_y=target_player_destination.y;
		}
		
		player.move(x,y);
		
		//checking if the player is at the end of the displaying screen
		int moved_display_amount;
		if (player.get_destination().right+screen_buffer >=source_location.right){
			moved_display_amount=player.get_destination().right+screen_buffer-source_location.right;
			move_sourec_location(moved_display_amount,0);
			
		}
		if (player.get_destination().bottom+screen_buffer>=source_location.bottom){
			moved_display_amount=player.get_destination().bottom+screen_buffer-source_location.bottom;
			move_sourec_location(0,moved_display_amount);
		}
		
		if (player.get_destination().left-screen_buffer<=source_location.left){
			moved_display_amount=player.get_destination().left-screen_buffer-source_location.left;
			move_sourec_location(moved_display_amount,0);
		}
		if (player.get_destination().top-screen_buffer<=source_location.top){
			moved_display_amount=player.get_destination().top-screen_buffer-source_location.top;
			move_sourec_location(0,moved_display_amount);
		}

		
		//checking if the screen is on off the stage
		if (source_location.top<0){
			move_sourec_location(0,screen_buffer);	
			player.move(0,screen_buffer);	
			target_player_destination.y+=screen_buffer;
			player_y+=screen_buffer;
			build_ground();
		}
		if (source_location.left<0){
			player.move(screen_buffer,0);
			move_sourec_location(screen_buffer,0);	
			target_player_destination.x+=screen_buffer;
			player_x+=screen_buffer;
			build_ground();
			
		}
		if (source_location.bottom>ground.getHeight()){
			move_sourec_location(0,-screen_buffer);	
			player.move(0,-screen_buffer);	
			target_player_destination.y-=screen_buffer;
			player_y-=screen_buffer;
			build_ground();
		}
		if (source_location.right>ground.getWidth()){
			move_sourec_location(-screen_buffer,0);			
			player.move(-screen_buffer,0);	
			target_player_destination.x-=screen_buffer;
			player_x-=screen_buffer;
			build_ground();
		}
		
		
	}
	
	public void update_frame(float delta_time){
		draw_UI.drawARGB(0, 0, 0, 0);
		Rect draw_from=new Rect(0, 0,ground.getWidth(), ground.getHeight());
		draw_UI.drawBitmap(ground, draw_from, draw_from,null);
		player.update_frame(delta_time);
		//update the player location
					
	  	if (player_speed.x !=0 ||player_speed.y !=0 ){
			player_x+=delta_time* player_speed.x;
			player_y+=delta_time* player_speed.y;
			move_player((int) (player_x - player.center.x),(int) (player_y - player.center.y));
			
		}		
		player.draw(draw_UI);
	}
	public void build_ground(){
		draw_background.drawARGB(0, 0, 0, 0);
		Rect plot = new Rect();
		Rect draw_from=new Rect(0, 0, 63, 63);
		for (int y=0;y<=8;y++){
			for (int x=0;x<=8;x++){		
				plot.set(x*64, y*64, (x*64)+64, (y*64)+64);
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
