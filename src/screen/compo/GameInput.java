package screen.compo;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class GameInput extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4046170463946645231L;
	
	public GameInput(String max, int x, int y, int width, int height) {
		super(max);
		setBounds(x,y,width,height);
		setForeground(Color.BLACK);
	}
}
