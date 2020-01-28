package screen;

import assets.*;
import game.*;
import map.*;
import render.*;
import screen.compo.MainButton;
import screen.compo.MainText;
import screen.compo.SubButton;
import screen.compo.TitleText;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TitleScreen extends CameraScreen implements ActionListener {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 632046522747878932L;
	public static final int MAX_DISTORTION_COUNT = 500;
	
	private Point[] distortions;
		
	private Camera bgCam;
	protected GameMap map;
	
	private double[] bgRot;
	private double viewAngle;
	
	protected PopUp gameSettings, settingsMenu;
	
	public TitleScreen(GameMap map, JFrame frame, String name, int width, int height) {
		super(frame, name, width, height);
		this.map = map;
		distortions = new Point[MAX_DISTORTION_COUNT];		
		
		bgRot = new double[] {Math.PI, Math.PI / 2, 0};
		
		for(int i = 0; i < TitleScreen.MAX_DISTORTION_COUNT; i++) {
			distortions[i] = new Point();
		}
		
		bgCam = new Camera(map, this, new double[] {32, 25, 32}, bgRot, 100);
		setCam(bgCam);
		
		new TitleText(this, "SURVIVABILITY 3", 0, 25, resWidth, 80);
		
		gameSettings = addPopUp("GAME SETTINGS", 1280, 720);
		settingsMenu = addPopUp("SETTINGS",1280,720);
	}
	
	public void run() {
		super.run();
		
		Color distort = new Color(127, 127, 127, 200);
 		Graphics2D g2d = getGraphics2D();
 		
 		
 		for(int i = 0; i < TitleScreen.MAX_DISTORTION_COUNT; i++) {
 			
 			int x = (int)(Math.random() * 1920);
			int y = (int)(Math.random() * 1080);
			
 			g2d.setColor(distort);
			g2d.fillRect(x, y, 45, 1);
			
 		}
 		
 		bgRot[0]+=Math.PI / 60;
 		
 		g2d.setColor(Color.BLACK);
 		g2d.fillRect(0,0,image.getWidth(), image.getHeight());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		
		if(ac.equals("egs")){
			gameSettings.startRun();
		}else if(ac.equals("settings")) {
			settingsMenu.startRun();
		}
	}

}
