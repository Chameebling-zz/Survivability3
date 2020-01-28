package screen.compo;

import java.awt.Font;

public interface GameComponent {
	public int getOrigX();
	public int getOrigY();
	public int getOrigWidth();
	public int getOrigHeight();
	public float getOrigFontSize();
	public Font getOrigFont();
	public void setNewBounds(int x, int y, int width, int height);
	public void setNewFont(Font font);
}
