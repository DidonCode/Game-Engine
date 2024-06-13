package Entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import Engine.Configuration;
import Models.Model;

public class Player extends Entity {

	private float xDir, yDir, zDir;
	private boolean jump = false;
	
	private Camera camera;
	
	public Player(Model model, Camera camera) {
		super(model);
		this.camera = camera;
	}
	
	public Player(Vector3f position, Model model, Camera camera) {
		super(position, model);
		this.camera = camera;
	}
	
	public Player(Vector3f position, Vector3f rotation, Model model, Camera camera) {
		super(position, rotation, model);
		this.camera = camera;
	}

	public void update() {
		if(camera == null) return; 
		
		xDir = 0; yDir = 0; zDir = 0;
		
		if(Keyboard.isKeyDown(Configuration.PLAYER_LEFT)) {
			xDir = -Configuration.PLAYER_SPEED;
		}
		
		if(Keyboard.isKeyDown(Configuration.PLAYER_RIGHT)) {
			xDir = Configuration.PLAYER_SPEED;
		}
		
		if(Keyboard.isKeyDown(Configuration.PLAYER_SPACE)) {
			if(jump) {
				yDir = Configuration.PLAYER_JUMP;
				jump = false;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			yDir = -Configuration.PLAYER_JUMP;
		}
		
		if(Keyboard.isKeyDown(Configuration.PLAYER_FOWARD)) {
			zDir = -Configuration.PLAYER_SPEED;
		}
		
		if(Keyboard.isKeyDown(Configuration.PLAYER_BACK)) {
			zDir = Configuration.PLAYER_SPEED;
		}
		
		float xa = (float) (xDir * Math.cos(Math.toRadians(camera.getYaw())) - zDir * Math.sin(Math.toRadians(camera.getYaw())));
		float ya = yDir;
		float za = (float) (zDir * Math.cos(Math.toRadians(camera.getYaw())) + xDir * Math.sin(Math.toRadians(camera.getYaw())));
		
		move(xa, ya, za);
		
		jump = true;

	}

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
}
