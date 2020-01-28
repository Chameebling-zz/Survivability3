package screen;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import game.GameSession;
import map.GameMap;
import screen.compo.MainButton;
import screen.compo.MainText;
import screen.compo.SubButton;

public class UnhostedScreen extends TitleScreen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6649921365232714138L;
	
	private PopUp gameSettings, joinCode;
	
	public UnhostedScreen(GameMap map, JFrame frame, String name, int width, int height) {
		super(map, frame, name, width, height);
		// TODO Auto-generated constructor stub
		
		joinCode = addPopUp("ENTER JOIN CODE",800,400);
		
		initStartUI(50,250);
		initLANUI(50,500);
		initSettingsUI(50,750);
		
		loadComponents();
	}
	
	private void initStartUI(int x, int y) {
		JButton start = new MainButton(this, "START",x,y,500,50,"start");
		JButton editGameSettings = new SubButton(this, "Edit Game Settings...",x,y+50,500,50,"egs");
	}
	private void initLANUI(int x, int y) {
		JLabel LAN = new MainText(this, "LAN SERVER:",x+25,y,500,50);
		JButton openLAN = new SubButton(this, "Click to Host...", x, y+50, 500, 50,"open_lan");
		JButton joinLAN = new SubButton(this, "Click to Join...",x,y+100, 500, 50, "join_lan");
	}
	private void initSettingsUI(int x, int y) {
		JButton settings = new MainButton(this, "SETTINGS", x, y, 500, 50, "settings");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		super.actionPerformed(e);
		
		if(ac.equals("start")){
			GameScreen gameScreen = (GameScreen)((GameFrame)frame).getScreen("Game");
			GameSession session = new GameSession(map, gameScreen, 300);
			((GameFrame)frame).renderScreen("Game");
			gameScreen.setSession(session);
		}else if(ac.equals("join_lan")) {
			joinCode.startRun();
		}
	}
}
