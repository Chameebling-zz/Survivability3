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

public class OldCamera {
	
	public final int CHUNK_LENGTH = 4;
	public final int MAX_ENTITY_DISTANCE = 100;
	
	public final AlphaComposite src_over = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
	public final AlphaComposite dst_over = AlphaComposite.getInstance(AlphaComposite.DST_OVER);
	
	private GameMap map;
	private int[] pixels;
	private Graphics2D g2d;
	
	private double[] pos, posToUse, rot, surface;
		
	private Chunk[][] chunks;
	private ArrayList<Entity> entities;
	
	private Point3D[] currentMapPixCorners;
	
	private Point3D[] currentPartCorners;
	
	private int[] projCornersX, projCornersY;
	
	private Point currentChunk;
	
	private int width, height;
	
	private boolean currentDraw;
	
	private ArrayList<Part> orderedParts;
	
	public OldCamera(GameMap map, CameraScreen screen, double[] pos, double[] rot, double dist) {
		
		long msStart = Calendar.getInstance().getTimeInMillis();
		
		// Initializes all the fields!
		this.map = map;
		this.pos = pos;
		posToUse = new double[3];
		this.rot = rot;
		surface = new double[] {0, 0, dist};
		BufferedImage image = screen.getImage();
		pixels = ( (DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		// - Gets and configures the Graphics2D class properly!
		Graphics g = image.getGraphics();
		g2d = (Graphics2D) g;
		g2d.setComposite(src_over);
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		
		// - Initializes object fields by constructing them so we don't have to deal w/ this later!
		chunks = new Chunk[CHUNK_LENGTH][CHUNK_LENGTH];
		entities = new ArrayList<Entity>();
		currentMapPixCorners = new Point3D[4];
		currentPartCorners = new Point3D[4];
		projCornersX = new int[4];
		projCornersY = new int[4];
		currentChunk = new Point();
		width = image.getWidth();
		height = image.getHeight();
		orderedParts = new ArrayList<Part>();
		
		// Fills Arrays with constructed objects!
		for(int i = 0; i < 4; i++) {
			currentMapPixCorners[i] = new Point3D();
			currentPartCorners[i] = new Point3D();
		}
				
		long msDone = Calendar.getInstance().getTimeInMillis();
	}
	
	public void run() {
		
		long msStart = Calendar.getInstance().getTimeInMillis();
						
		updateChunks();
		updateEntities();
		
		orderParts();
		
		projectMap();
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).run();
		}
				
		long msDone = Calendar.getInstance().getTimeInMillis();
				
		//System.out.println("This run took "+(msDone - msStart)+"ms!");
		
	}
	
	private void setPosToUse(){
		posToUse[0] = pos[0];
		posToUse[1] = -pos[1];
		posToUse[2] = pos[2];
	}
	
	// Sets the Point parameter the index in a Point(x, z) based on the position of the camera!
	private void currentChunkIndex(Point p){
		p.setLocation((int)pos[0]/Main.IMAGE_SIZE, (int)pos[2]/Main.IMAGE_SIZE);
	}
	
	// Updates the chunks to a buffer for what the camera will render, based on pos!
	private void updateChunks(){
		
		currentChunkIndex(currentChunk);
		
		for(int i = 0; i < chunks.length; i++) {
			for(int j = 0; j < chunks.length; j++) {
				chunks[i][j] = null;
			}
		}
		
		for(int i = -CHUNK_LENGTH / 2; i < CHUNK_LENGTH / 2; i++){
			for(int j = -CHUNK_LENGTH / 2; j < CHUNK_LENGTH / 2; j++){
				
				int refX = (int)currentChunk.getX()+i + 1;
				int refZ = (int)currentChunk.getY()+j + 1;
				int renderX = i+CHUNK_LENGTH/2;
				int renderZ = j+CHUNK_LENGTH/2;
				
				if(refX >= 0 && refZ >= 0){
					chunks[renderX][renderZ] = map.getChunk(refX,refZ);
				}
			}
		}
		
	}
	
	private void updateEntities() {
		ArrayList<Entity> mapEntities = map.getEntities();
		for(int i = 0; i < mapEntities.size(); i++) {
			Entity e = mapEntities.get(i);
			double dist = getDistTo(e.getX(), e.getY(), e.getZ());
			if(dist<=250) {
				entities.add(e);
			}
		}
	};
	
	private void orderParts() {
		orderedParts.clear();
		orderPartsInChunks();
		orderPartsInEntities();
	}
	
	private void orderPartsInChunks() {
		for(int i = 0; i < chunks.length; i++) {
			for(int j = 0; j < chunks.length; j++) {
				Chunk c = chunks[i][j];
				
				if(c==null) {
					continue;
				}
				
				for(int k = 0; k < Chunk.MAX_COMPONENTS; k++) {
					ChunkComponent cc = c.getComponent(k);
					Part[] ps = cc.getParts();
					
					for(int l = 0; l < ps.length; l++) {
						Part part = ps[l];
						
						double dist = getAvgDist(part);
						
						int curIndex = 0;
						if(orderedParts.size()>0) {
							int size = orderedParts.size();
							curIndex = size - 1;
							Part ip = orderedParts.get(curIndex);
							double indexDist = getAvgDist(ip);
							while(dist>indexDist && curIndex>0) {
								ip = orderedParts.get(curIndex);
								indexDist = getAvgDist(ip);
								curIndex--;
							}
						}
						orderedParts.add(curIndex, part);
					}
				}
			}
		}
	}
	
	private void orderPartsInEntities() {
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			Part[] ps = e.getParts();
			
			for(int l = 0; l < ps.length; l++) {
				Part part = ps[l];
				
				double dist = getAvgDist(part);
				
				int curIndex = 0;
				if(orderedParts.size()>0) {
					int size = orderedParts.size();
					curIndex = size - 1;
					Part ip = orderedParts.get(curIndex);
					double indexDist = getAvgDist(ip);
					while(dist>indexDist && curIndex>0) {
						ip = orderedParts.get(curIndex);
						indexDist = getAvgDist(ip);
						curIndex--;
					}
				}
				orderedParts.add(curIndex, part);
			}
		}
	}
		
	private void projectPart(Part part) {
				
		if(part==null) {
			return;
		}
		BufferedImage image = part.getImage();
		int orient = part.getOrient();
		int x = part.getX();
		int y = part.getY();
		int width = part.getWidth();
		int length = part.getLength();
		int displacement = part.getDisplacement();
		
		int curX = 0;
		int curY = 0;
		int curZ = 0;
		
		if(orient==0) {
			curY = -part.getChunkY() * Main.IMAGE_SIZE - displacement + part.getEntityY();
		} else if(orient==1) {
			curY = -part.getChunkY() * Main.IMAGE_SIZE + displacement + part.getEntityY();
		} // TODO: Add other orientations!
		
		for(int ix = 0; ix < width; ix++) {
			if(orient==0) {
				curX = part.getChunkX() * Main.IMAGE_SIZE + x + ix + part.getEntityX();
			} else if(orient==1){
				curX = part.getChunkY() * Main.IMAGE_SIZE + width - ix + x + part.getEntityX();
			} // TODO: Add other orientations!
			
			for(int iy = 0; iy < length; iy++) {
				if(orient==0) {
					curZ = part.getChunkZ() * Main.IMAGE_SIZE + y + iy + part.getEntityZ();
				} else if(orient==1){
					curZ = part.getChunkZ() * Main.IMAGE_SIZE + width - iy + y + part.getEntityZ();
				}
				
				if(orient==0 || orient==1) {
					currentMapPixCorners[0].setLocation(curX, curY, curZ);
					currentMapPixCorners[1].setLocation(curX + 1, curY, curZ);
					currentMapPixCorners[2].setLocation(curX + 1, curY, curZ + 1);
					currentMapPixCorners[3].setLocation(curX, curY, curZ + 1);
				} // TODO: Add other orientations!
				
				currentDraw = true;
				
				for(int c = 0; c < 4; c++) {
					project(currentMapPixCorners[c], c);
				}
				
				if(currentDraw) {
					
					int pixX = 0;
					int pixY = 0;
					
					if(orient==0) {
						pixX = ix;
						pixY = iy;
					} else if(orient==1) {
						pixX = width - ix - 1;
						pixY = length - iy - 1;
					} // TODO: Add other orientations!
					
					
					
					g2d.setColor(new Color(image.getRGB(pixX, pixY)));
					g2d.fillPolygon(projCornersX, projCornersY, 4);
					
					g2d.setColor(Color.GREEN);
				} else {
					g2d.setColor(Color.RED);
				}
				
				g2d.fillRect(0, 0, 50, 50);
			}
		}
	}
	
	private void projectMap() {
		
		// Makes the entire screen black before reprojecting!
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, 1920, 1080);
		
		for(int i = orderedParts.size() - 1; i >= 0; i--) {
			Part part = orderedParts.get(i);
			projectPart(part);
		}
		
	}

	// Gets the average distance of the part that it accepts!
	private double getAvgDist(Part part) {
		int orient = part.getOrient();
		int perpValue = 0;
		if(orient==0) {
			perpValue = -part.getChunkY() * Main.IMAGE_SIZE - part.getDisplacement();
		} else if(orient==1){
			perpValue = -part.getChunkY() * Main.IMAGE_SIZE + part.getDisplacement();
		}
		
		int x = part.getX();
		int y = part.getY();
		int width = part.getWidth();
		int length = part.getLength();
		
		double totalDist = 0;
		
		for(int l = 0; l < 4; l++) {
			double dist = getDistTo(part.getChunkX() * Main.IMAGE_SIZE + x + (l % 2) * width, perpValue, part.getChunkZ() * Main.IMAGE_SIZE + y + (l / 2) * length);
			totalDist += dist;
		}
		
		return totalDist / 4;
		
	}
	
	// Returns true if there a point in the arrays that can be shown!
	private boolean hasIndexWithinImage(int[] x, int[] y){
		for(int i = 0; i < x.length; i++) {
			
			int ix = (int)x[i];
			int iy = (int)y[i];
			
			if( (ix>=0 && ix<width) && (iy>=0 && iy<height) ) {
				return true;
			}
		}
		return false;
	}
	
	private boolean withinImage(int[] x, int[] y) {
		for(int i = 0; i < x.length; i++) {
			
			int ix = x[i];
			int iy = y[i];
			
			if( (ix<0 || ix>=width) || (iy<0 || iy>=height) ) {
				return false;
			}
		}
		return true;
	}
	
	// Gets the distance between two three-dimensional points!
	private double getDistTo(double x, double y, double z){
		return Math.sqrt( Math.pow(pos[0] - x, 2) + Math.pow(pos[1] - y, 2) + Math.pow(pos[2] - z, 2) );
	}

	
	// Sets the 2nd parameter based on the 3D point coordinates for the first one by projecting
	// using vector and rotation matrix formula!
	private void project(Point3D p3d, int i) {
		
		setPosToUse();
		
		double sx = Math.sin(rot[0]);
		double sy = Math.sin(rot[1]);
		double sz = Math.sin(rot[2]);
		
		double cx = Math.cos(rot[0]);
		double cy = Math.cos(rot[1]);
		double cz = Math.cos(rot[2]);
		
		double x = p3d.getX() - posToUse[0];
		double y = p3d.getY() - posToUse[1];
		double z = p3d.getZ() - posToUse[2];
		
		double syx = sz*y + cz*x;
		double cyx = cz*y - sz*x;
		
		double dx = cy*syx-sy*z;
		double dy = sx*( cy*z + sy*syx ) + cx*cyx;
		double dz = cx*( cy*z + sy*syx ) - sx*cyx;
		
		if(dz<0) {
			currentDraw = false;
		}
		
		double px = surface[2] / dz * dx + surface[0] + width / 2;
		double py = surface[2] / dz * dy + surface[1] + height / 2;
		
		projCornersX[i] = (int)px;
		projCornersY[i] = (int)py;
	}
	
	public Graphics2D getGraphics2D() {
		return g2d;
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
