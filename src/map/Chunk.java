package map;

public class Chunk {
	
	public static final int MAX_COMPONENTS = 8;
	public static final int HEIGHT = 32;
		
	private ChunkComponent[] components;
	
	private int x, z;
	
	public Chunk(int x, int z){
				
		components = new ChunkComponent[Chunk.MAX_COMPONENTS];
		
		this.x = x;
		this.z = z;
		
		for(int i = 0; i < components.length; i++) {
			components[i] = new ChunkComponent();
		}
	}
	
	public void addPart(ChunkPart p, int y) {
		ChunkComponent cc = components[0];
		cc.add(p);
	}
	
	public ChunkComponent getComponent(int y) {
		return components[y];
	}
	
	public void setComponent(ChunkComponent cc, int y){
		components[y] = cc;
	}
   
	public String toString(){
		return x+" "+z;
	}
	
	public int getX() {
		return x;
	}
	
	public int getZ() {
		return z;
	}
}