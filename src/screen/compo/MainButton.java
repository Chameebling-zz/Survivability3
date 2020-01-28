package screen.compo;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import assets.Fonts;
import screen.Screen;

public class MainButton extends GameButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9148564389770265919L;
	
	public MainButton(Screen s, final String name, int x, int y, int width, int height, String actionCommand) {
		super(s, name, x, y, width, height, actionCommand);
		setFont(Fonts.MAIN);
		s.add(this);
	}

}
