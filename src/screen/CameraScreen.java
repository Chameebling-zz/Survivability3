package screen;

import render.*;

import java.awt.*;
import java.util.Arrays;

import javax.swing.*;

import assets.Fonts;

public class CameraScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5647678227801065020L;
	
	private Camera cam;
	
	public CameraScreen(JFrame frame, String name, int width, int height) {
		super(frame, name, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void setCam(Camera cam) {
		this.cam = cam;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(cam!=null) {
			cam.run();
		}
		super.paintComponent(g);
	}
	
}
