package screen.compo;

import assets.Fonts;
import screen.Screen;

public class MainText extends GameText {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8218384374376521913L;

	public MainText(Screen s, String text, int x, int y, int width, int height) {
		super(s, text, x, y, width, height);
		setFont(Fonts.MAIN);
		s.add(this);
	}

}
