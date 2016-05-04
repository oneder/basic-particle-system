package ps;

public class Vector2D {
	
	public double x;
	public double y;
	
	public Vector2D(double num1, double num2){
		this.x = num1;
		this.y = num2;
	}
	
	public void add(double num){
		this.x += num;
		this.y += num;
	}
	
	public void sub(double num){
		this.x -= num;
		this.y -= num;
	}
	
	public void mult(double num){
		this.x *= num;
		this.y *= num;
	}
	
	public void div(double num){
		this.x /= num;
		this.y /= num;
	}
	
	public void add(Vector2D other){
		this.x += other.x;
		this.y += other.y;
	}
	
	public void sub(Vector2D other){
		this.x -= other.x;
		this.y -= other.y;
	}
	
	public void mult(Vector2D other){
		this.x *= other.x;
		this.y *= other.y;
	}
	
	public void div(Vector2D other){
		this.x /= other.x;
		this.y /= other.y;
	}
}
