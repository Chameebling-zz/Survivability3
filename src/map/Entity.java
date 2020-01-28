package map;

import java.util.*;

import game.GameSession;

public class Entity {
	
	protected ArrayList<Part> parts;
	private ArrayList<Interaction> interactions;
	protected ArrayList<HitBox> hitboxes;
	private double gravity = 9.8;
	private boolean movable, ai, hostile;
	
	protected GameSession session;
	protected GameMap map;
	protected double[] pos, rot, vel;
	protected double moveSpeed, rotSpeed;
	protected int hp;
	
	public Entity(GameSession session, double[] pos, double[] rot, double gravity, boolean movable) {
		this.session = session;
		this.pos = pos;
		this.rot = rot;
		this.map = session.getMap();
		parts = new ArrayList<Part>();
		interactions = new ArrayList<Interaction>();
		hitboxes = new ArrayList<HitBox>();
		pos = new double[3];
		rot = new double[3];
		vel = new double[3];
		
		this.gravity = gravity;
		this.movable = movable;
		
		moveSpeed = 1;
		rotSpeed = 1;
	}
	
	public void run(){
		fall();
	}
	
	public void fall(){
		if(!detectBottom()){
			pos[1]--;
		}
	}
	
	public void updatePos(){
		
	}
	
	// Detects hitboxes right above the entity! #TODO: Make sure this method works!
	public boolean detectTop(){
		for(int i = 0; i < hitboxes.size(); i++){
			HitBox hb = hitboxes.get(i);
			int y = hb.getY() + hb.getDy();
			
			for(int x = hb.getX(); x <= hb.getX() + hb.getDx(); x++){
				for(int z = hb.getZ(); z <= hb.getZ() + hb.getDz(); z++){
					HitBox thb = map.getHitBox(pos[0], pos[1] + 1, pos[2]);
					boolean hitboxIsSelf = false;
					for(int j = 0; j < hitboxes.size(); j++){
						if(thb.equals(hitboxes.get(j))){
							hitboxIsSelf = true;
						}
					}
					if(!hitboxIsSelf){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	// Detects hitboxes right below the entity!
	public boolean detectBottom(){
		for(int i = 0; i < hitboxes.size(); i++){
			HitBox hb = hitboxes.get(i);
			int y = hb.getY() + hb.getDy();
			
			for(int x = hb.getX(); x <= hb.getX() + hb.getDx(); x++){
				for(int z = hb.getZ(); z <= hb.getZ() + hb.getDz(); z++){
					HitBox thb = map.getHitBox(pos[0] + x, pos[1] - 1, pos[2] + z);
					
					if(thb==null){
						continue;
					}
										
					boolean hitboxIsSelf = false;
					if(hitboxes.contains(thb)) {
						hitboxIsSelf = true;
					}
					
					if(!hitboxIsSelf){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void moveForward() {
		pos[0]+=moveSpeed*Math.sin(rot[1]);
		pos[2]+=moveSpeed*Math.cos(rot[1]);
	}
	
	public void pointTowards(double x, double y, double z) {
		double dx = pos[0] - x;
		double dy = pos[1] - y;
		double dz = pos[2] - z;
		double xdp = dx;
		double zdp = dz;
		
		double dist = Math.sqrt( dx*dx + dy*dy + dz*dz );
		
		if(dist==0) {
			return;
		}
		
		rot[1] = Math.acos(xdp/dist);
	}
	
	public Part[] getParts() {
		return parts.toArray(new Part[parts.size()]);
	}
	
	public int getHP() {
		return hp;
	}
	
	public double[] getPos() {
		return pos;
	}
	
	public double getX() {
		return pos[0];
	}
	
	public double getY() {
		return pos[1];
	}
	
	public double getZ() {
		return pos[2];
	}
	
	public double[] getRot() {
		return rot;
	}
}
