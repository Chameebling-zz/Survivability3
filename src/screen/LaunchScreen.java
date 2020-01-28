package screen;

import javax.swing.JFrame;
import javax.swing.JLabel;

import assets.*;
import screen.compo.MainText;
import screen.compo.SubText;

public class LaunchScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1252912036773618639L;

	public LaunchScreen(JFrame frame, String name, int width, int height) {
		super(frame, name, width, height);
		
		JLabel loadingText = new MainText(this, "SURVIVABILITY 3 IS LOADING...", 100, 100, 500, 50);
		loadComponents();
	}
	
	public void run() {
		super.run();
		
		
	}

}
