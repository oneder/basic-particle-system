package main;

public class PS_Main{

	public static void main(String[] args) {
		System.out.println("Particle System - MAIN");
		
		Window window = new Window(640, 480, "Particle System");
		window.poll_input();
		window.game_loop();
	}

}
