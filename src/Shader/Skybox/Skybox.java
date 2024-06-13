package Shader.Skybox;

import org.lwjgl.util.vector.Vector3f;

import Engine.Loader;
import Models.Model;

public class Skybox {
	
	private final float size = 500f;
	
	private final float[] vertices = {        
	    -size,  size, -size,
	    -size, -size, -size,
	    size, -size, -size,
	     size, -size, -size,
	     size,  size, -size,
	    -size,  size, -size,

	    -size, -size,  size,
	    -size, -size, -size,
	    -size,  size, -size,
	    -size,  size, -size,
	    -size,  size,  size,
	    -size, -size,  size,

	     size, -size, -size,
	     size, -size,  size,
	     size,  size,  size,
	     size,  size,  size,
	     size,  size, -size,
	     size, -size, -size,

	    -size, -size,  size,
	    -size,  size,  size,
	     size,  size,  size,
	     size,  size,  size,
	     size, -size,  size,
	    -size, -size,  size,

	    -size,  size, -size,
	     size,  size, -size,
	     size,  size,  size,
	     size,  size,  size,
	    -size,  size,  size,
	    -size,  size, -size,

	    -size, -size, -size,
	    -size, -size,  size,
	     size, -size, -size,
	     size, -size, -size,
	    -size, -size,  size,
	     size, -size,  size
	};
	
	private String[] dayTextureFile = {
		"ForReal_Data/Textures/Skybox/right.png", 
		"ForReal_Data/Textures/Skybox/left.png", 
		"ForReal_Data/Textures/Skybox/top.png", 
		"ForReal_Data/Textures/Skybox/bottom.png", 
		"ForReal_Data/Textures/Skybox/back.png", 
		"ForReal_Data/Textures/Skybox/front.png"
	}; 
	
	private String[] nightTextureFile = new String[] {
		"ForReal_Data/Textures/Skybox/right.png", 
		"ForReal_Data/Textures/Skybox/left.png", 
		"ForReal_Data/Textures/Skybox/top.png", 
		"ForReal_Data/Textures/Skybox/bottom.png", 
		"ForReal_Data/Textures/Skybox/back.png", 
		"ForReal_Data/Textures/Skybox/front.png"
	};
	
	private Model cube;
	private Vector3f position, rotation;
	private int dayTexture, nightTexture;
	
	public Skybox() {
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
	}
	
	public Skybox(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
	public Skybox(String[] dayTextureFile, String[] nightTextureFile) {
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		
		this.dayTextureFile = dayTextureFile;
		this.nightTextureFile = nightTextureFile;
	}
	
	public Skybox(Vector3f position, Vector3f rotation, String[] dayTextureFile, String[] nightTextureFile) {
		this.position = position;
		this.rotation = rotation;
		
		this.dayTextureFile = dayTextureFile;
		this.nightTextureFile = nightTextureFile;
	}
	
	public void create(Loader loader) {
		cube = loader.loadToVAO(vertices, 3);
		dayTexture = loader.loadCubeMap(dayTextureFile);
		nightTexture = loader.loadCubeMap(nightTextureFile);
	}

	public Model getCube() {
		return cube;
	}

	public int getDayTexture() {
		return dayTexture;
	}

	public int getNightTexture() {
		return nightTexture;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

}
