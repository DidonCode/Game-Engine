package Interface.Guis.Elements;

import org.lwjgl.input.Keyboard;

import Engine.Configuration;
import Interface.Guis.Styles.Style;
import Tools.Key;

public abstract class Element {
	
	protected int x, y;
	protected int width, height;
	protected Style style;
	
	public Element(int x, int y, int width, int height, Style style) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.style = style;
	}
	
	public abstract void update();
	public abstract void render();
	
	public abstract boolean isHover();
	public abstract boolean isClick();

	protected boolean defaultHover() {
		int mx = Key.getMouseX();
		int my = Key.getMouseY();
		
		if(mx >= x && mx <= x + width && my >= y && my <= y + height) {
			return true;
		}
		return false;
	}
	
	protected boolean defaultClick() {
		int mx = Key.getMouseX();
		int my = Key.getMouseY();
		
		if(mx >= x && mx <= x + width && my >= y && my <= y + height) {
			if(Keyboard.isKeyDown(Configuration.PLAYER_LEFT_CLICK)) {
				return true;
			}
		}
		return false;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}
	
}
