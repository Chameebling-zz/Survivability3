package map;

import java.awt.image.BufferedImage;

public class ChunkPart extends Part {
		
	public ChunkPart(BufferedImage image, int orient, int x, int y, int width, int length, int displacement, int chunkX,
			int chunkY, int chunkZ) {
		super(null, image, orient, x, y, width, length, displacement);
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.chunkZ = chunkZ;
		// TODO Auto-generated constructor stub
	}
}
