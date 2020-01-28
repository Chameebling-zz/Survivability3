package screen.compo;

import javax.swing.SwingConstants;

import assets.Fonts;
import screen.Screen;

public class TitleText extends GameText {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8256503163549077060L;

	public TitleText(Screen s, String text, int x, int y, int width, int height) {
		super(s, text, x, y, width, height);
		setFont(Fonts.MAIN.deriveFont(100F));
		setHorizontalAlignment(SwingConstants.CENTER);
		s.add(this);
	}

}
