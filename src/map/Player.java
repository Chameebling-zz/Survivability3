package map;

import main.*;
import map.*;
import render.*;
import screen.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;

import game.GameSession;

public class Player extends Entity implements KeyListener, MouseListener, MouseMotionListener {
		
	private GameSession session;
	private GameFrame frame;
	private Camera c;
	private GameScreen s;
	private Robot r;
		
	private Point mouseMid;
	
	private boolean[] keys;
	
	private boolean dead;
	
	private double[] eyePos;
		
	public Player(GameSession session, GameScreen s, double x, double y, double z, double rx, double ry, double rz) {
		super(session, new double[] {x, y, z}, new double[3], 9.8, true);
		
		hp = 100;
		
		eyePos = new double[3];
		
		rot[0] = rx;
		rot[1] = ry;
		rot[2] = rz;
		
		updateEyePos();
		
		this.session = session;
		frame = (GameFrame) s.getFrame();
		c = new Camera(map, s, eyePos, rot, 500);
		try {
			r = new Robot();
		}catch(AWTException e) {
			e.printStackTrace();
		}
		
		mouseMid = new Point();
				
		s.setCam(c);
		
		keys = new boolean[4];
		
		hitboxes.add(new HitBox(0, 0, 0, 32, 32, 32));
		
		this.s = s;
		
		centerMouse();
		
		session.getMap().addEntity(session,0,10,0);
	}
	
	public void run() {
		if(!dead) {
			if(pos[1]>=Chunk.MAX_COMPONENTS*Main.IMAGE_SIZE) {
				die("Went too high in the world!");
			}
			super.run();
			handleKeys();
			updateEyePos();
			if(pos[1]<0) {
				damage(100,"Fell out of the world!");
			}
			if(pos[0]<=-Main.IMAGE_SIZE || pos[2]<=-Main.IMAGE_SIZE){
				damage(100,"Suffocation");
			}
			centerMouse();
		} else {
			
		}
	}
	private void updateEyePos() {
		eyePos[0] = pos[0];
		eyePos[1] = pos[1] + 16;
		eyePos[2] = pos[2];
	}
	
	public void damage(int amt, String cause) {
		if(!dead) {
			hp -= amt;
			if(hp<=0) {
				hp = 0;
				die(cause);
			}
		}
	}
	
	private void die(final String cause) {
		dead = true;
		int[] raster = s.getRaster();
		for(int i = 0; i < raster.length; i++) {
			raster[i] += 100;
		}
		final JLabel label = s.getCauseOfDeathLabel();
		label.setVisible(true);
		JButton button = s.getBackButton();
		button.setVisible(true);
		TimerTask task = new TimerTask() {
			int i = 0;
			double textType = 0;
			boolean cursorVisible = true;
			
			public void run() {
				if(textType<cause.length()-1) {
					typewriteCauseOfDeath(label, cause, (int)textType, cursorVisible);
					textType = Math.min(textType+=0.5, cause.length() - 1);
				} else {
					blinkCursor(cause, i);
					i++;
					if(i>8) {
						i = 0;
					}
				}
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 100, 50);
	}
	
	private void typewriteCauseOfDeath(JLabel label, String text, int index, boolean cursorVisible) {
		JLabel causeOfDeath = s.getCauseOfDeathLabel();
		text = (" Cause of Death: " + text).substring(0,17+index+1);
		causeOfDeath.setText(text+"_");
	}
	private void blinkCursor(String text, int i) {
		JLabel causeOfDeath = s.getCauseOfDeathLabel();
		if(i>4) {
			text+=" ";
		} else {
			text += "_";
		}
		causeOfDeath.setText("Cause of Death: "+text);
	}
	
	private void centerMouse() {
		
		int absoluteMidX = frame.getX() + frame.getWidth() / 2;
		int absoluteMidY = frame.getY() + frame.getHeight() / 2;
		
		mouseMid.setLocation(absoluteMidX, absoluteMidY);
		
		r.mouseMove(absoluteMidX, absoluteMidY);
	}
	
	private void handleKeys() {
		double moveSpeed = session.getMoveSpeed();
		if(keys[0]) {
			moveForward();
		}
		if(keys[1]) {
			pos[0]-=moveSpeed*Math.sin(rot[1]);
			pos[2]-=moveSpeed*Math.cos(rot[1]);
		}
	}
	
	private void handleMouseMovement() {
		
		if(!dead) {
			Point mouseP = MouseInfo.getPointerInfo().getLocation();
			
			int mouseX = (int) mouseP.getX();
			int mouseY = (int) mouseP.getY();
			
			double rotSpeed = session.getRotSpeed();
			
			int difX = mouseX - (int)mouseMid.getX();
			int difY = mouseY - (int)mouseMid.getY();
			
			double rotDifX = rotSpeed * difX/2500;
			double rotDifY = rotSpeed * difY/2500;
			
			System.out.println(frame.getContentPane().getBounds());
			
			if(rot[0]>Math.PI/2 || rot[0]+rotDifY>=Math.PI/2) {
				rot[0] = Math.PI/2;
			}
			
			if(rot[0]<-Math.PI/2 || rot[0]+rotDifY<=-Math.PI/2) {
				rot[0] = -Math.PI/2;
			}
			
			rot[0]-=rotDifY;
			rot[1]+=rotDifX;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if(!dead) {
			if(k==KeyEvent.VK_UP){
				keys[0] = true;
			}
			if(k==KeyEvent.VK_DOWN) {
				keys[1] = true;
			}
			if(k==KeyEvent.VK_LEFT) {
				keys[2] = true;
			}
			if(k==KeyEvent.VK_RIGHT) {
				keys[3] = true;
			}
			if(k==KeyEvent.VK_L){
				rot[1]-=0.1;
			}
			if(k==KeyEvent.VK_R){
				rot[1]+=0.1;
			}
			if(k==KeyEvent.VK_1) {
				rot[2]+=0.1;
			}
			if(k==KeyEvent.VK_2) {
				rot[2]-=0.1;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		
		if(k==KeyEvent.VK_UP){
			keys[0] = false;
		}
		if(k==KeyEvent.VK_DOWN) {
			keys[1] = false;
		}
		if(k==KeyEvent.VK_LEFT) {
			keys[2] = false;
		}
		if(k==KeyEvent.VK_RIGHT) {
			keys[3] = false;
		}
		if(k==KeyEvent.VK_SPACE) {
			pos[1]+=25;
		}
	}
	
	public double getDistFromPlayer(double x, double y, double z) {
		return Math.sqrt( Math.pow(pos[0] - x, 2) + Math.pow(pos[1] - y, 2) + Math.pow(pos[2] - z, 2) );
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		handleMouseMovement();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		handleMouseMovement();
	}

}
