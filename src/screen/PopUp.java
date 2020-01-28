package screen;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import screen.compo.MainButton;
import screen.compo.MainText;

public class PopUp extends Screen {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1471927673208728357L;
	
	private Screen s;
	
	private boolean clickOutsideToExit, disableOutsideComponents;
	
	private MainText popupTitle;
	private MainButton closeButton;
	
	public PopUp(Screen s, String name, int width, int height) {
		super(s, name, width, height);
		
		this.s = s;
						
		setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		s.add(this);
				
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,0,resWidth,resHeight);
		
		initTopBar();
	}
	
	private void initTopBar() {
		popupTitle = new MainText(this, name, 10, 0, resWidth - 10, 50);
		closeButton = new MainButton(this, "X", resWidth - 50, 0, 50, 50, "px");
	}
	
	public void startRun() {
		super.startRun();
		s.disableJComponents();
	}
	
	public void endRun() {
		super.endRun();
		s.enableJComponents();
	}
	
	protected double getSFToFit() {
		double widthSF = getSF(gameWidth, s.getSize().width);
		double heightSF = getSF(gameHeight, s.getSize().height);
				
		return Math.min(widthSF, heightSF);
	}
	
	public Rectangle getBoundsToFit() {
		double sf = getSFToFit();
		
		int sWidth = (int)Math.round(1.0 * s.getSize().width / gameWidth * resWidth);
		int sHeight = (int)Math.round(1.0 * s.getSize().height / gameHeight * resHeight);
		
		int x = (s.getSize().width - sWidth) / 2;
		int y = (s.getSize().height - sHeight) / 2;
		
		return new Rectangle(x, y, sWidth, sHeight);
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
	}
	
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		
		String action = e.getActionCommand();
		
		if(action.equals("px")) {
			endRun();
		}
	}
}
