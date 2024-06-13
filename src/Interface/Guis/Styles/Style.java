package Interface.Guis.Styles;

import java.awt.Font;

import Interface.Guis.Render.Color4f;
import Interface.Guis.Render.Texture;

public class Style {
	
	public static Style DEFAULT_BUTTON = defaultButton();
	public static Style DEFAULT_TEXT = defaultText();
	public static Style DEFAULT_CHECKBOX = defaultCheckbox();
	
	public boolean alignX;
	public boolean alignY;
	
	public Color4f color;
	public Color4f borderColor;
	public Color4f backgroundColor;
	public Texture background;
	
	public int fontSize;
	public int fontType;
	public String fontName;
	
	public Style() {}
	
	public static Style defaultButton() {
		Style buttonStyle = new Style();
		
		buttonStyle.alignX = true;
		buttonStyle.alignY = true;
		
		buttonStyle.backgroundColor = Color4f.WHITE;
		
		return buttonStyle;
	}
	
	public static Style defaultText(){
		Style textStyle = new Style();
		
		textStyle.fontSize = 15;
		textStyle.fontType = Font.BOLD;
		textStyle.fontName = "roboto";
		textStyle.color = Color4f.convertHex("a4f38b");
		
		return textStyle;
	}
	
	public static Style defaultCheckbox(){
		Style checkboxStyle = new Style();
		
		checkboxStyle.borderColor = Color4f.convertHex("#ffffff");
		
		return checkboxStyle;
	}
	
}