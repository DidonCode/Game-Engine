package Engine;

import org.lwjgl.input.Keyboard;

public class Configuration {
	
	//---------------SHADOW-SETTINGS---------------\\
	
	public static boolean SHADOW = false;
	public static int SHADOW_MAP_SIZE = 32768; // Quality
	public static float SHADOW_DISTANCE = 150;
	
	//------------------GRAPHICS------------------\\
	
	public static int RENDER_DISTANCE = 250;
	public static int MAX_LIGHTS = 4;
	
	//--------------------WORLD--------------------\\
	
	public static float GRAVITY = 0.01f;
	
	//--------------------CAMERA--------------------\\
	
	public static float CAMERA_FOV = 70;
	public static float CAMERA_NEAR_PLANE = 0.1f;
	public static float CAMERA_FAR_PLANE = 1000;
	
	//--------------PLAYER-MOUVEMENTS--------------\\
	
	public static int PLAYER_FOWARD = Keyboard.KEY_Z;
	public static int PLAYER_BACK = Keyboard.KEY_S;
	public static int PLAYER_LEFT = Keyboard.KEY_Q;
	public static int PLAYER_RIGHT = Keyboard.KEY_D;
	public static int PLAYER_SPACE = Keyboard.KEY_SPACE;
	public static int PLAYER_SWITCH_VIEW = Keyboard.KEY_V;
	
	//-----------------PLAYER-MOUSE-----------------\\
	
	public static int PLAYER_LEFT_CLICK = 0;
	public static int PLAYER_RIGHT_CLICK = 1;
	public static int PLAYER_MIDDLE_CLICK = 2;
	
	public static float PLAYER_MOUSE_SENSIVITY = 0.3f;

	//-----------------PLAYER-STATS-----------------\\
	
	public static float PLAYER_SPEED = 0.15f;
	public static float PLAYER_JUMP = 0.3f;
}
