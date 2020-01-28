package screen.compo;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JLabel;

import screen.Screen;

public class GameText extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8974514423985332340L;

	public GameText(Screen s, String text, int x, int y, int width, int height) {
		super(text);
		setBounds(x, y, width, height);
		setForeground(Color.WHITE);
	}
}
