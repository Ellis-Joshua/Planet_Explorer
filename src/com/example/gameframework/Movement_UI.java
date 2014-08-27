package com.example.gameframework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import com.example.gameframework.Game;

public class Movement_UI extends Clickable_Object {
	
	Canvas draw_UI;
	
	Clickable_Object button_up;
	Clickable_Object button_down;
	Clickable_Object button_left;
	Clickable_Object button_right;
	Clickable_Object button_stop;

	
	public Movement_UI(Point display_size, Point draw_at, int shape){
		super(display_size, display_size, draw_at, 1, 1, shape);
		
		
	}
	public void setup_ui(Game game,Context context){
		//set up the buttons
		Point frame_size= new Point(64, 64);
		Point button_size= new Point(64, 64);
		
		//set up Forward
		game.load_image("Forward.png",context);//load the players image into memory
		Point button_at = new Point(148,58);//set the location the player is to be drawn
		button_up= new Clickable_Object (game.loaded_bitmap,frame_size,button_size, button_at, 2,1, Clickable_Object.RECTANGLE);
		
		//set up Backward
		game.load_image("Backward.png",context);//load the players image into memory
		button_at.set(148,238);//set the location the player is to be drawn
		button_down= new Clickable_Object (game.loaded_bitmap,frame_size,button_size, button_at, 2,1, Clickable_Object.RECTANGLE);
		
		//set up Turn left
		game.load_image("LeftTurn.png",context);//load the players image into memory
		button_at.set(58,148);//set the location the player is to be drawn
		button_left= new Clickable_Object (game.loaded_bitmap,frame_size,button_size, button_at, 2,1, Clickable_Object.RECTANGLE);
		
		//set up Turn Right
		game.load_image("RightTurn.png",context);//load the players image into memory
		button_at.set(238,148);//set the location the player is to be drawn
		button_right= new Clickable_Object (game.loaded_bitmap,frame_size,button_size, button_at, 2,1, Clickable_Object.RECTANGLE);
		
		//set up Stop
		game.load_image("Stop.png",context);//load the players image into memory
		button_at.set(148,148);//set the location the player is to be drawn
		button_stop= new Clickable_Object (game.loaded_bitmap,frame_size,button_size, button_at, 2,1, Clickable_Object.RECTANGLE);

		draw_UI=new Canvas();
		draw_UI.setBitmap(image);
		Update_UI();
	}
	public void Update_UI( ){
		draw_UI.drawARGB(0, 0, 0, 0);
		//draw to the canvas
		button_up.draw(draw_UI);
		button_down.draw(draw_UI);
		button_left.draw(draw_UI);
		button_right.draw(draw_UI);
		button_stop.draw(draw_UI);
	}
	
	public void reset_button_click_state(){
		button_up.set_frame(0);
		button_down.set_frame(0);
		button_left.set_frame(0);
		button_right.set_frame(0);
		Update_UI();
	}
	public void on_click(Renderable_Object player, Point touch, int speed, boolean touch_down) {
		if (touch_down){
			if (button_up.is_clicked(touch)){
				button_up.set_frame(1);
				Update_UI();
			}else if (button_down.is_clicked(touch)){
				button_down.set_frame(1);
				Update_UI();
			}else if (button_left.is_clicked(touch)){
				button_left.set_frame(1);
				Update_UI();
			}else if (button_right.is_clicked(touch)){
				button_right.set_frame(1);
				Update_UI();
			}			
		}else{

			if (button_up.is_clicked(touch)){
				player.move(0,-speed);
			}else if (button_down.is_clicked(touch)){
				player.move(0,speed);
			}else if (button_left.is_clicked(touch)){
				player.move(-speed,0);
			}else if (button_right.is_clicked(touch)){
				player.move(speed,0);
			}
		}
		
	}
		// button_up;
		// button_down;
		// button_left;
		// button_right;
		// button_stop;
}
