package screen;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import map.GameMap;
import screen.compo.MainButton;
import screen.compo.MainText;
import screen.compo.SubButton;

public class JoinedScreen extends TitleScreen {

	public JoinedScreen(GameMap map, JFrame frame, String name, int width, int height) {
		super(map, frame, name, width, height);
		// TODO Auto-generated constructor stub
		
		initStartUI(50,250);
		initLANUI(50,500);
		initSettingsUI(50,750);
		
		loadComponents();
	}
	
	private void initStartUI(int x, int y) {
		JButton start = new MainButton(this, "READY",x,y,500,50,"start");
	}
	private void initLANUI(int x, int y) {
		JLabel LAN = new MainText(this, "LAN SERVER:",x+25,y,500,50);
		JButton openLAN = new SubButton(this, "Leave Server...", x, y+50, 500, 50,"open_lan");
	}
	private void initSettingsUI(int x, int y) {
		JButton settings = new MainButton(this, "SETTINGS", x, y, 500, 50, "settings");
	}

}
