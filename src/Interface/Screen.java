package Interface;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

import Interface.Guis.Gui;
import Interface.Guis.Hud;
import Tools.Logs;

public class Screen {
	
	private String title;
	private int width, height, fps;
	private boolean isFullscreen;
	
	private Gui gui;
	private Hud hud;
	
	private static long lastFrameTime;
	private static float delta;
	
	public Screen(String title, int width, int height, int fps, boolean isFullscreen){
		this.title = title;
		this.width = width;
		this.height = height;
		this.fps = fps;
		this.isFullscreen = isFullscreen;
		
		lastFrameTime = getCurrentTime();
	}
	
	public void create() {
		try{
			
			Display.setTitle(title);
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setResizable(true);
			Display.create(new PixelFormat().withSamples(8));
			
		} catch(LWJGLException e) {
			new Logs("Error for creating display", e, false);
		}
	}
	
	public void update() {
		width = Display.getWidth();
		height = Display.getHeight();
		
		Display.setTitle(title);
		Display.sync(fps);
		Display.update();
		
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
		
		set3DView();
		
		if(hud != null) hud.update();
		if(gui != null) gui.update();
	}

	public void render() {
		set2DView();
		
		if(hud != null) hud.render();
		if(gui != null) gui.render();
	}
	
	public boolean isRunning() {
		return !Display.isCloseRequested();
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void destroy() {
		Display.destroy();
	}
	
	public void setFullscreen() {
		try {
			Display.setFullscreen(isFullscreen);
		} catch (LWJGLException e) {
			new Logs("Error to set fullsreen", e, true);
			return;
		}
	}
	
	private void set2DView() {
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluOrtho2D(0, Display.getWidth(), Display.getHeight(), 0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_LINE_SMOOTH);
	}
	
	private void set3DView() {
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glEnable(GL_TEXTURE_2D);
		glEnable(GL13.GL_MULTISAMPLE);
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
	
	public Gui getGui() {
		return gui;
	}

	public void setGui(Gui gui) {
		this.gui = gui;
	}

	public Hud getHud() {
		return hud;
	}

	public void setHud(Hud hud) {
		this.hud = hud;
	}
}
