package screen;

import java.awt.*;
import javax.swing.*;

public class Button {
	
	private JButton button;
	
	private int x, y, width, height;
	
	public Button(String text, int x, int y, int width, int height, int buttonType) {
		button = new JButton(text);
		
		if(buttonType==0) {
			button.setOpaque(false);
		}
			
	}
}
