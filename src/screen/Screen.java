package screen;

import java.util.*;
import java.awt.*;
import java.awt.Component;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

import assets.*;
import screen.compo.GameComponent;

public class Screen extends JPanel implements ActionListener, Displayable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8659340140176518549L;

	public static final int MENU_BUTTON = 0;
	public static final int SUB_BUTTON = 1;
	
	protected JFrame frame;
	protected BufferedImage image;
	protected Graphics2D g2d;
	protected int[] raster;
	
	protected ArrayList<PopUp> popups;
	
	protected ArrayList<JComponent> components;
	protected ArrayList<Rectangle> componentBounds;
	protected ArrayList<Font> componentFonts;
	protected final int gameWidth = 1920, gameHeight = 1080;
	protected int resWidth, resHeight;
	protected int width, height;
	
	protected boolean disabled = false;
	
	protected String name;
	
	protected double sf;
	
	public Screen(JFrame frame, String name, int width, int height) {
		this(name, width, height);
		
		this.frame = frame;
		
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		repaint();
	}
	
	private Screen(String name, int width, int height) {
		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		popups = new ArrayList<PopUp>();
		
		components = new ArrayList<JComponent>();
		componentBounds = new ArrayList<Rectangle>();
		componentFonts = new ArrayList<Font>();
		
		resWidth = width;
		resHeight = height;
		
		this.width = resWidth;
		this.height = resHeight;
		
		g2d = (Graphics2D) image.getGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		raster = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		this.name = name;
		
		setOpaque(true);
		setVisible(false);
		
		setLayout(null);
	}
	
	public Screen(Screen s, String name, int width, int height) {
		this(name, width, height);
		
		repaint();
	}
	
	public void startRun() {
		startup();
		loadComponents();
		requestFocusInWindow();
		resize();
		setFocusable(true);
		setVisible(true);
	}
	
	public void endRun() {
		removeAll();
		setFocusable(false);
		setVisible(false);
	}
	
	public void disableJComponents() {
		for(int i = 0; i < components.size(); i++) {
			components.get(i).setEnabled(false);
		}
		disabled = true;
	}
	
	public void enableJComponents() {
		for(int i = 0; i < components.size(); i++) {
			components.get(i).setEnabled(true);
		}
		disabled = false;
	}
	
	protected void startup() {
		
	}
	
	public void run() {
		repaint();
		revalidate();
		
		for(int i = 0; i < popups.size(); i++) {
			PopUp pi = popups.get(i);
			if(pi.isVisible()) {
				pi.repaint();
			}
		}
	}
	
	public void add(JComponent c) {
		components.add(c);
		componentBounds.add(c.getBounds());
		componentFonts.add(c.getFont());
		if(disabled) {
			c.setEnabled(false);
		}
		super.add(c);
		resize();
	}
	
	public PopUp addPopUp(String title, int width, int height) {
		PopUp newPopUp = new PopUp(this, title, width, height);
		popups.add(newPopUp);
		return newPopUp;
	}
	
	protected double getSF(int originalSize, int targetSize) {
		return (double)targetSize / originalSize;
	}
	
	protected double getSFToFit() {
		double widthSF = getSF(gameWidth, frame.getContentPane().getWidth());
		double heightSF = getSF(gameHeight, frame.getContentPane().getHeight());
				
		return Math.min(widthSF, heightSF);
	}
	
	private Rectangle getBoundsToFit() {
		double sf = getSFToFit();
		
		int sWidth = (int)Math.round(gameWidth * sf);
		int sHeight = (int)Math.round(gameHeight * sf);
		
		int x = (frame.getContentPane().getWidth() - sWidth) / 2;
		int y = (frame.getContentPane().getHeight() - sHeight) / 2;
		
		return new Rectangle(x, y, sWidth, sHeight);
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	// Getter for the BufferedImage field.
	public BufferedImage getImage() {
		return image;
	}
	
	public Graphics2D getGraphics2D() {
		return g2d;
	}
	
	public int[] getRaster() {
		return raster;
	}
	
	public String getName(){
		return name;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void loadComponents() {
		
		for(int i = 0; i < components.size(); i++) {
			super.add((Component) components.get(i));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if(action.equals("start")) {
			GameFrame f = (GameFrame) frame;
			f.renderScreen("Game");
		}
	}
	
	protected void runPrior() {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		runPrior();
		
		super.paintComponent(g);
		g.drawImage(image, 0, 0, super.getWidth(), super.getHeight(), null);
	}

	public void resize() {
		
		Rectangle r = getBoundsToFit();
				
		setBounds(r);
		
		width = r.width;
		height = r.height;
		
		double sf = getSFToFit();
		for(int i = 0; i < components.size(); i++) {
						
			JComponent c = components.get(i);
			Rectangle cb = componentBounds.get(i);
			Font cf = componentFonts.get(i);
			
			int x = cb.x;
			int y = cb.y;
			int width = cb.width;
			int height = cb.height;
			
			int newX = (int)Math.round(x * sf);
			int newY = (int)Math.round(y * sf);
			int newWidth = (int)Math.round(width * sf);
			int newHeight = (int)Math.round(height * sf);
			
			c.setBounds(newX, newY, newWidth, newHeight);
			c.setFont(cf.deriveFont(cf.getSize() * (float)sf));
		}
		
		for(int i = 0; i < popups.size(); i++) {
			PopUp iPopUp = popups.get(i);
			if(iPopUp.isVisible()) {
				iPopUp.resize();
			}
		}
	}
	
	public int getResWidth() {
		return resWidth;
	}
	
	public int getResHeight() {
		return resHeight;
	}
	
	public boolean isDisabled() {
		return disabled;
	}
	
}
