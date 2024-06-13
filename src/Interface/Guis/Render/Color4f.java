package Interface.Guis.Render;

import org.newdawn.slick.Color;

public class Color4f {
	
	public static final Color4f WHITE = new Color4f(1, 1, 1);
	public static final Color4f BLACK = new Color4f(0, 0, 0);
	
	public static final Color4f RED = new Color4f(1, 0, 0);
	public static final Color4f GREEN = new Color4f(0, 1, 0);
	public static final Color4f BLUE = new Color4f(0, 0, 1);
	
	public float r, g, b, a; 
	
	public Color4f(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color4f(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1;
	}
	
	public static Color4f convertHex(String hexColor) {
		int shift = 0;
		
		if(hexColor.charAt(0) == '#') shift = 1;  
		
		int r = Integer.valueOf(hexColor.substring(0 + shift, 2 + shift), 16);
   		int g = Integer.valueOf(hexColor.substring(2 + shift, 4 + shift), 16);
    	int b = Integer.valueOf(hexColor.substring(4 + shift, 6 + shift), 16);
    	
	    return convertRGB(r, g, b);
	}
	
	public Color convertToText() {
		return new Color(this.r, this.g, this.b, this.a);
	}
	
	public static Color4f convertRGB(int r, int g, int b) {
		return new Color4f((float) r / 255, (float) g / 255, (float) b / 255, 1);
	}
	
	public void add(Color4f color) {
		this.r += color.r;
		this.g += color.g;
		this.b += color.b;
	}
	
	public void sub(Color4f color) {
		this.r -= color.r;
		this.g -= color.g;
		this.b -= color.b;
	}
	
	public void mul(Color4f color) {
		this.r *= color.r;
		this.g *= color.g;
		this.b *= color.b;
	}
	
	public String toString() {
		return "RED: " + r + "; GREEN: " + g + "; BLUE:" + b;
	}
}
