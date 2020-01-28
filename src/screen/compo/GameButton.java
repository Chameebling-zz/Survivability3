package screen.compo;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import assets.Fonts;
import screen.Screen;

public class GameButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4185776569128905813L;

	public GameButton(Screen s, final String text, int x, int y, int width, int height, String actionCommand) {
		super(text);
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setOpaque(false);
		setBounds(x, y, width, height);
		setForeground(Color.WHITE);
		setHorizontalAlignment(SwingConstants.LEFT);
		setActionCommand(actionCommand);
		addActionListener(s);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				if(!((Screen) getParent()).isDisabled()) {
					setText(text+" >");
				}
			}
			
			public void mouseExited(MouseEvent e) {
				setText(text);
			}
		});
	}

}
