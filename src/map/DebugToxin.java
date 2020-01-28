package map;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import game.GameSession;

public class DebugToxin extends Entity {
		
	public DebugToxin(GameSession session, int x, int y, int z){
		super(session, new double[] {x, y, z}, new double[3], 9.8, true);
		BufferedImage purpleImage = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
		Color purple = new Color(255, 0, 255);
		int[] purpleRaster = new int[64];
		Arrays.fill(purpleRaster, purple.getRGB());
		purpleImage.setRGB(0, 0, 8, 8, purpleRaster, 0, 8);
		moveSpeed = 0.001;
		
		parts.add(new Part(this,purpleImage,Part.UP,0,0,8,8,0));
		
		hitboxes.add(new HitBox(0,0,0,32,32,32));
	}
	
	public void run() {
		super.run();
		Player p = session.getPlayer();
		moveForward();
		pointTowards(p.getX(), p.getY(), p.getZ());
		
		double dist = p.getDistFromPlayer(pos[0], pos[1], pos[2]);
		
		if(dist<=10) {
			p.damage(1,"Intoxication of Debug Entity!");
		}
	}
}
