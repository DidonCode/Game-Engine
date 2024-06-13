package Tools;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Key {

	public static boolean isKeyDown(int key) {
		if((Keyboard.isKeyDown(key) || Mouse.isButtonDown(key))) {
			return true;
		}
		return false;
	}
	
	public static int getMouseX() {
		return Mouse.getX();
	}
	
	public static int getMouseY() {
		return -Mouse.getY() + Display.getHeight();
	}
	
	public static int getMouseWheel() {
		return Mouse.getDWheel();
	}
	
}
