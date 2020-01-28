package screen.compo;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import assets.Fonts;
import screen.Screen;

public class SubButton extends GameButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7244927868216263168L;

	public SubButton(Screen s, final String name, int x, int y, int width, int height, String actionCommand) {
		super(s, name, x, y, width, height, actionCommand);
		setFont(Fonts.CONTENT);
		s.add(this);
	}

}
