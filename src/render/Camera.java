package render;

import assets.*;
import main.*;
import map.*;
import screen.*;

import java.awt.*;
import java.util.*;
import java.awt.image.*;

/*
 * Camera.java
 * Assignment: Final Project 2018-19 (Game: Survivability 3)
 * Purpose: Show what you learned in the APCS class (e.g. inheritance, interfaces, ArrayLists, etc.)
 * @version --/--/-- because I'm still working on this class!!!
 * 
 * TODO:
 * - Fix issue where an additional reflected image is being printed.
 * - Make this class handle usable corrdinates.
 */

public class Camera {
	
	private GameMap map;
	private BufferedImage image;
	private int[] pixels;
	private Screen screen;
	
	private double[] pos, rot;
	private double zoom;
	
	public Camera(GameMap map, CameraScreen screen, double[] pos, double[] rot, double zoom) {
		
		long msStart = Calendar.getInstance().getTimeInMillis();
		
		// Initializes all the fields!
		this.map = map;
		this.pos = pos;
		this.rot = rot;
		this.zoom = zoom;
		image = screen.getImage();
		this.screen = screen;
		pixels = ( (DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
	
	public void run() {
		
		int len = pixels.length;
		
		for(int i = 0; i < len; i++) {
			
			int x = i % image.getWidth();
			int y = i / image.getHeight();
			
			
		}
		
	}
	
	

	
	// Sets the 2nd parameter based on the 3D point coordinates for the first one by projecting
	// using vector and rotation matrix formula!
	private void project(Point3D p3d, int i) {
				
		double sx = Math.sin(rot[0]);
		double sy = Math.sin(rot[1]);
		double sz = Math.sin(rot[2]);
		
		double cx = Math.cos(rot[0]);
		double cy = Math.cos(rot[1]);
		double cz = Math.cos(rot[2]);
		
//		double x = p3d.getX() - posToUse[0];
//		double y = p3d.getY() - posToUse[1];
//		double z = p3d.getZ() - posToUse[2];
		
//		double syx = sz*y + cz*x;
//		double cyx = cz*y - sz*x;
//		
//		double dx = cy*syx-sy*z;
//		double dy = sx*( cy*z + sy*syx ) + cx*cyx;
//		double dz = cx*( cy*z + sy*syx ) - sx*cyx;
		
//		if(dz<0) {
//			currentDraw = false;
//		}
		
//		double px = surface[2] / dz * dx + surface[0] + width / 2;
//		double py = surface[2] / dz * dy + surface[1] + height / 2;
//		
//		projCornersX[i] = (int)px;
//		projCornersY[i] = (int)py;
	}
	
	public double[] getPos(){
		return pos;
	}
	
	public double[] getRot(){
		return rot;
	}
	
	public String rotToDeg() {
		return "["+Math.toDegrees(rot[0])+(char)176+","+Math.toDegrees(rot[1])+(char)176+","+Math.toDegrees(rot[2])+(char)176+"]";
	}
}
