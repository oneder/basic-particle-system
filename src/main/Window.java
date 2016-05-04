package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import ps.Particle;
import ps.Vector2D;

public class Window extends JFrame{
	
	private int x;
	private int y;
	private int max_particle;
	private boolean loop;
	private boolean paused;
	private boolean gravity;
	
	private ArrayList<Particle> particles;
	private BufferStrategy buff_strat;
	private Canvas screen;
	private Random rand;
	private Color default_color;
	private Color current_color;
	
	public Window(int width, int height, String title){
		super();
		
		this.x = 0;
		this.y = 0;
		this.max_particle = 500;
		this.loop = true;
		this.paused = false;
		this.gravity = false;
		
		particles = new ArrayList<Particle>(max_particle);
		rand = new Random();
		default_color = Color.BLUE;
		current_color = default_color;
		buff_strat = null;
		
		setTitle(title);
		setIgnoreRepaint(true);
		setResizable(false);
	
		screen = new Canvas();
		screen.setIgnoreRepaint(true);
		int n_width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int n_height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		n_width /= 2;
		n_height /= 2;
		
		setBounds(n_width - (width / 2), n_height - (height / 2), width, height);
		screen.setBounds(n_width - (width / 2), n_height - (height / 2), width, height);
		
		add(screen);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		screen.createBufferStrategy(2);
		buff_strat = screen.getBufferStrategy();
		
	}
	
	public void poll_input(){
		screen.addMouseListener(new MouseListener(){
		
			public void mouseClicked(MouseEvent e){
				
			}
			public void mouseEntered(MouseEvent e){
				
			}
			public void mouseExited(MouseEvent e){
				
			}
			public void mousePressed(MouseEvent e){
				
			}
			public void mouseReleased(MouseEvent e){
				if(!paused){
					for(int i = 0; i < 10; i++){
						add_particle();
					}
				}
			}
			
		});
		
		screen.addKeyListener(new KeyListener(){
			
			public void keyPressed(KeyEvent e){
				int code = e.getKeyCode();
				
				if(!paused){
					if(code == 'S'){
						for(int i = 0; i < 10; i++){
							add_particle();
						}
					}	
				}
				
			}
			public void keyReleased(KeyEvent e){
				int code = e.getKeyCode();
				
				if(code == 'P'){
					if(paused)
						paused = false;
					else
						paused = true;
				}
				if(!paused){
					
					// Random Color
					if(code == 'C'){
						current_color = new Color((int) random_unsigned(255), (int) random_unsigned(255), (int) random_unsigned(255));
					}
					
					// Returns to default Color
					if(code == 'D'){
						current_color = default_color;
					}
					
					// Resets screen/Clean up
					if(code == 'R'){
						for(int i = 0; i < particles.size(); i++){
							particles.get(i).set_life(1);
						}
					}
				}
			}
			public void keyTyped(KeyEvent e){
				
			}
			
		});
	}
	
	public void add_particle(){
		Particle p = new Particle(x, y, 0, 0, 0, 0, current_color);
		
		/*p.set_vel(random(7), random(7));
		p.set_accel(random(0.02), random(0.02));
		p.set_size(random_unsigned(25) + 25, random_unsigned(25) + 25);
		p.set_maxsize(50, 50);
		p.set_growth(random(2), random(2));
		p.set_life(random_unsigned(150) + 150);
		
		p.set_ult(false);*/
		
		/*p.set_vel(random(3),random(3));
        p.set_accel(0, 0);
        p.set_life(random_unsigned(150)+150);
        p.set_size(8, 8);
        p.set_maxsize(26,26);
        p.set_growth(0, 0);
        p.set_default(true);*/
        //p.setUltSize(false);
        
        p.set_vel(random(4),random(4));
        p.set_accel(0,random_unsigned(.2)+.1);
        p.set_life(random_unsigned(150)+150);
        p.set_size(25, 25);
        p.set_maxsize(25,25);
        p.set_growth(-random_unsigned(.2)-.5, -random_unsigned(.2)-.5);
        p.set_default(true);
        //p.setUltSize(false);
		
		/*p.set_vel(random(1),random(1));
        p.set_accel(0,-random_unsigned(.04)-.02);
        p.set_life(random_unsigned(150)+550);
        p.set_size(16, 16);
        p.set_maxsize(25,25);
        p.set_growth(-.1, -.1);
        p.set_default(true);
        //p.setUltSize(false);*/
		
		particles.add(p);
	}
	
	public double random(double num){
		return ((num * 2) * rand.nextDouble()) - num;
	}
	
	public double random_unsigned(double num){
		double temp = ((num * 2) * rand.nextDouble()) - num;
		
		if(temp < 0)
			return temp * -1;
		else
			return temp;
	}
	
	public void game_loop(){
		while(loop){
			if(!paused)
				update();
			render();
			
			try{
				Thread.sleep(1000 / 60);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public void update(){
		Point p = screen.getMousePosition();
		
		if(p != null){
			this.x = p.x;
			this.y = p.y;
		}
		
		for(int i = 0; i < particles.size(); i++){
			Particle part = particles.get(i);
			
			if(part.update())
				particles.remove(i);
			
			if(part != null){
				if(part.get_loc().x <= 0){
					part.get_loc().x = 0;
					part.get_vel().x *= -1;
				}
				if(part.get_loc().x >= screen.getWidth()){
					part.get_loc().x = screen.getWidth();
					part.get_vel().x *= -1;
				}
				
				if(part.get_loc().y <= 0){
					part.get_loc().y = 0;
					part.get_vel().y *= -1;
				}
				if(part.get_loc().y >= screen.getHeight()){
					part.get_loc().y = screen.getHeight();
					part.get_vel().y *= -1;
				}
			}
		}
	}
	
	public void render(){
		do{
			do{
				Graphics2D g2d = (Graphics2D) buff_strat.getDrawGraphics();
				g2d.fillRect(0, 0, screen.getWidth(), screen.getHeight());
				
				render_particles(g2d);
				
				g2d.dispose();
			}while(buff_strat.contentsRestored());
			buff_strat.show();
		}while(buff_strat.contentsLost());
	}
	
	public void render_particles(Graphics2D g2d){
		for(int i = 0; i < particles.size(); i++){
			particles.get(i).render(g2d);
		}
	}
	
}
