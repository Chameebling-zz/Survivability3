package screen.compo;

import assets.Fonts;
import screen.Screen;

public class SubText extends GameText {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7972312747016978899L;

	public SubText(Screen s, String text, int x, int y, int width, int height) {
		super(s, text, x, y, width, height);
		setFont(Fonts.CONTENT);
		s.add(this);
	}

}
