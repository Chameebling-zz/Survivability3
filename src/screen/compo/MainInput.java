package screen.compo;

import assets.Fonts;
import screen.Screen;

public class MainInput extends GameInput {

	public MainInput(Screen s, String max, int x, int y, int width, int height) {
		super(max, x, y, width, height);
		s.setFont(Fonts.MAIN);
		s.add(this);
	}

}
