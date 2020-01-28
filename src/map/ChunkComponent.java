package map;

import java.util.*;

public class ChunkComponent {
   
	private ArrayList<ChunkPart> parts;
 	private ArrayList<HitBox> hitboxes;  
	
	public ChunkComponent(){
		parts = new ArrayList<ChunkPart>();
		hitboxes = new ArrayList<HitBox>();
	}
	
	public void add(ChunkPart p) {
		parts.add(p);
		hitboxes.add(new HitBox(0, 0, 0, 64, 0, 64));
	}
	
	public Part[] getParts() {
		return parts.toArray(new Part[parts.size()]);
	}
	
	public Part getPart(int index) {
		return parts.get(index);
	}
	
	public HitBox getHitBox(int x, int y, int z){
		for(int i = 0; i < hitboxes.size(); i++){
			HitBox hb = hitboxes.get(i);
			int x1 = hb.getX();
			int y1 = hb.getY();
			int z1 = hb.getZ();
			
			int dx = hb.getDx();
			int dy = hb.getDy();
			int dz = hb.getDz();
			
			int x2 = x1 + dx;
			int y2 = y1 + dy;
			int z2 = z1 + dz;
			
			if( (x>=x1 && x<=x2) && (y>=y1 && y<=y2) && (z>=z1 && z<=z2) ){
				return hb;
			}
		}
		return null;
	}
}
