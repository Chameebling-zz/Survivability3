package assets;

import java.io.*;
import java.awt.*;
/*
 * Fonts.java
 * Assignment: Final Project 2018-19 (Game: Survivability 3)
 * Purpose: Show what you learned in the APCS class (e.g. inheritance, interfaces, ArrayLists, etc.)
 * @version 6/20/2019
 */

public class Fonts {
	
	// The main larger font
	public static Font MAIN;
	
	// The smaller description font
	public static Font CONTENT;
	
	// Initializes the fonts for the static fields. Have to do them in a method because the methods involved
	// need to be surrounded with try/catch.
	public static void loadFonts() {
		try {
			MAIN = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/blue highway linocut.ttf")).deriveFont(40F);
			CONTENT = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/PTM55FT.ttf")).deriveFont(20F);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
