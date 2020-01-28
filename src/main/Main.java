package main;


import assets.*;
import map.*;
import render.*;
import screen.*;
import settings.*;

import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Main implements Runnable {
	
	public static final String VERSION = "1.0";
	
	public static final int RUN_DELAY = 60;
	public static final int RENDER_FPS = 60;
	public static final int IMAGE_SIZE = 64;
	
	private GameMap currMap;
	
	private Settings gameSettings;
	private Setting time, difficulty, map, estCoins;
	
	private Thread thread;
	
	// Currency and Profile
	private int coins;
	private long xp;
	
	private boolean running;
	
	// LAN and Hosting Info
	private boolean lan = false;
	private boolean host = false;
	
	private GameFrame frame;
	
	private Screen launch, unhostedTitle, joinedTitle, hostedTitle, settings, game, shop;
	private Camera cam;
	
	private Player player;
	
	public static void main(String[] args) {
		
		Fonts.loadFonts();
		Images.loadImages();
		
		new Main();

	}
	
	public Main() {
		thread = new Thread(this);
		frame = new GameFrame("Survivability 3", 1280, 720);
		
		frame.setBackground(Color.BLACK);
		
		launch = new LaunchScreen(frame, "Launch Screen", 1920, 1080);
		frame.addScreen(launch);		
		
		currMap = new GameMap("Room 319",25,25);
		

		
		initTitleScreen();
		initGameScreen();
		initPlayer();
		
		frame.renderScreen("Title Screen");
		
		// Start the thread!
		start();
	}
	
	private void initTitleScreen() {
		
		unhostedTitle = new UnhostedScreen(currMap, frame, "Title Screen", 1920, 1080);
		joinedTitle = new JoinedScreen(currMap, frame, "Joined Screen", 1920, 1080);
		hostedTitle = new JoinedScreen(currMap, frame, "Host Screen",1920,1080);
		
		frame.addScreen(unhostedTitle);
		frame.addScreen(joinedTitle);
		frame.addScreen(hostedTitle);
		
	}
	
	public void initGameScreen(){
		game = new GameScreen(currMap, frame, "Game", 1920, 1080);
		
		frame.addScreen(game);
	}
	
	public void initPlayer(){
		
	}
	
	private synchronized void start() {
		running = true;
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		
		long upTime = System.nanoTime();
		
		delayRun(upTime);
		renderRun(upTime);
		
	}
	
	private void delayRun(long upTime) {
		double ns = 1E9 / RUN_DELAY;
		double delta = 0;
		
		while(running) {
			
			long current = System.nanoTime();
			delta += (current - upTime) / ns;
			upTime = current;
			
			while(delta >= 1) {
				
				// THINGS TO RUN AT DESIRED FPS \/ \/ \/
				
				frame.run();
								
				delta--;
			}
		}
	}
	private void renderRun(long upTime) {
		double ns = 1E9 / RENDER_FPS;
		double delta = 0;
		frame.requestFocus();
		
		while(running) {
			
			long current = System.nanoTime();
			delta += (current - upTime) / ns;
			upTime = current;
			
			while(delta >= 1) {
				
				// THINGS TO RUN AT DESIRED FPS \/ \/ \/
				
				frame.run();
								
				delta--;
			}
		}
	}
}
