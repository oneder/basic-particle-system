package ps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Particle {
	
	private Vector2D loc;
	private Vector2D vel;
	private Vector2D accel;
	private Vector2D size;
	private Vector2D max_size;
	private Vector2D growth;
	private Vector2D life;
	private Color color;
	
	private boolean ult_size;
	private boolean default_size;
	
	public Particle(double x, double y, double dx, double dy, double size, double life, Color color){
		this.loc = new Vector2D(x, y);
		this.vel = new Vector2D(dx, dy);
		this.accel = new Vector2D(0, 0);
		this.size = new Vector2D(size, size);
		this.max_size = new Vector2D(0, 0);
		this.growth = new Vector2D(0, 0);
		this.life = new Vector2D(life, life);
		this.color = color;
		
		ult_size = false;
		default_size = false;
	}
	
	public boolean update(){
		vel.add(accel);
		loc.add(vel);
		size.add(growth);
		life.x--;
		
		if(life.x <= 0)
			return true;
		
		if(default_size){
			if(size.x >= max_size.x){
				if(size.y >= max_size.y)
					return true;
				else
					size.x = max_size.x;
			}
			if(size.y >= max_size.y)
				size.y = max_size.y;
			
			if(size.x <= 0){
				if(size.y <= 0)
					return true;
				else
					size.x = 1;
			}
			if(size.y <= 0)
				size.y = 1;
			
			return false;
		}
		if(ult_size){
			if(size.x > max_size.x){
				size.x = max_size.x;
				growth.x *= -1;
			}
			if(size.y > max_size.y){
				size.y = max_size.y;
				growth.y *= -1;
			}
			if(size.x < 0){
				size.x = 1;
				growth.x *= -1;
			}
			if(size.y < 0){
				size.y = 1;
				growth.y *= -1;
			}
		}
		else{
			if(size.x > max_size.x)
				size.x = max_size.x;
			if(size.y > max_size.y)
				size.y = max_size.y;
			if(size.x < 0)
				size.x = 1;
			if(size.y < 0)
				size.y = 1;
		}
		
		return false;
	}
	
	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D)g.create();
		
		g2d.setColor(color);
		g2d.fillRect((int) (loc.x - (size.x / 2)), (int) (loc.y - (size.y / 2)), (int) size.x, (int) size.y);
		
		g2d.dispose();
	}
	
	public void set_loc(double x, double y){
		loc.x = x;
		loc.y = y;
	}
	public void set_vel(double x, double y){
		vel.x = x;
		vel.y = y;
	}
	public void set_accel(double x, double y){
		accel.x = x;
		accel.y = y;
	}
	public void set_size(double x, double y){
		size.x = x;
		size.y = y;
	}
	public void set_maxsize(double x, double y){
		max_size.x = x;
		max_size.y = y;
	}
	public void set_growth(double x, double y){
		growth.x = x;
		growth.y = y;
	}
	public void set_life(double num){
		life.x = num;
		life.y = num;
	}
	public void set_default(boolean b){
		default_size = b;
	}
	public void set_ult(boolean b){
		default_size = false;
		ult_size = b;
	}
	
	public Vector2D get_loc(){
		return loc;
	}
	public Vector2D get_vel(){
		return vel;
	}
	public Vector2D get_accel(){
		return accel;
	}
}
