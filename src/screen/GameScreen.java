package screen;

import game.*;
import map.*;
import render.*;
import screen.compo.MainButton;
import screen.compo.SubText;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameScreen extends CameraScreen implements ActionListener {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 632046522747878932L;
	
	private GameSession session;
	
	private Player p;
	
	private JLabel causeOfDeath;
	
	private JButton backButton;
	
	private Cursor blankCursor;
	
	public GameScreen(GameMap map, JFrame frame, String name, int width, int height) {
		super(frame, name, width, height);
		
		causeOfDeath = new SubText(this, "Cause of Death: ", 0, 40, resWidth, 50);
		causeOfDeath.setVisible(false);
		causeOfDeath.setHorizontalAlignment(SwingConstants.CENTER);
		
		backButton = new MainButton(this, "BACK TO TITLE SCREEN", 250, 250, 500, 50, "bts");
		backButton.setVisible(false);
	}
	
	public void startup() {
		causeOfDeath.setText("Cause of Death: ");
	}
	
	public void run() {
		
		super.run();
		if(session!=null) {
			session.getPlayer().run();
		}
		
	}
	
	public void setSession(GameSession session){
		this.session = session;
		p = session.getPlayer();
		addKeyListener(p);
		addMouseListener(p);
		addMouseMotionListener(p);
	}
	
	public JLabel getCauseOfDeathLabel() {
		return causeOfDeath;
	}
	
	public JButton getBackButton() {
		return backButton;
	}
	
	public void hideCursor() {
		
	}
	
	public void showCursor() {
		
	}
	
	@Override
	public void runPrior() {
		if(p!=null && g2d!=null) {
			g2d.drawString(p.getHP()+" HP",50, 250);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if(ac.equals("bts")) {
			((GameFrame) frame).renderScreen("Title Screen");
			System.out.println();
		}
	}

}
