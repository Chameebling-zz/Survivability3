package screen;

import java.util.*;
import java.awt.*;
import java.awt.Component;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class GameFrame extends JFrame implements ComponentListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 942289975349692315L;
	private ArrayList<Displayable> displayables;
	Screen currScreen;
		
	private boolean fullscreen;
	
	public GameFrame(String title, int width, int height) {
		super(title);
		setLayout(null);
		getContentPane().setPreferredSize(new Dimension(width, height));
		
		displayables = new ArrayList<Displayable>();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setResizable(true);
		setLocationRelativeTo(null);
		
		addComponentListener(this);
		
		setVisible(true);
	}
	
	public void run() {
		
		currScreen.run();
		
	}
	
	public void addScreen(Screen s) {
		getContentPane().add(s);
		displayables.add(s);
		
		if(displayables.size()==1) {
			currScreen = s;
			renderScreen(s.getName());
		}
	}
	
	public Screen getCurrentScreen() {
		if(displayables.size()>=0) {
			return currScreen;
		} else {
			return null;
		}
	}
	
	public Screen getScreen(String name){
		for(int i = 0; i < displayables.size(); i++){
			if(((Component) displayables.get(i)).getName().equals(name)){
				return (Screen) displayables.get(i);
			}
		}
		return null;
	}
	
	public void renderScreen(String name){
		currScreen.endRun();
		for(int i = 0; i < displayables.size(); i++){
			Screen s = (Screen)displayables.get(i);
			if(s.getName().equals(name)){
				currScreen = s;
				currScreen.startRun();
				return;
			}
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		for(int i = 0; i < displayables.size(); i++) {
			displayables.get(i).resize();
		}
	}

	@Override
	public void componentShown(ComponentEvent e) {
		
	}
}
