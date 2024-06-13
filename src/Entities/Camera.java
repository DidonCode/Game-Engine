package Entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import Engine.Configuration;

public class Camera {

	private Vector3f position;
	private float pitch, yaw, roll;
	
	private Player player;
	
	private float distanceFromPlayer = 50, angleAroundPlayer = 0;
	private boolean firstPerson = false;
	
	public Camera() {
		this.position = new Vector3f(0, 0, 0);
		this.player = null;
	}
	
	public Camera(Player player) {
		this.position = player.getPosition();
		this.player = player;
	}
	
	public Camera(Vector3f position, Player player) {
		this.position = position;
		this.player = player;
	}
	
	public void update() {	
		if(player == null) return;
		
		if(Keyboard.isKeyDown(Configuration.PLAYER_SWITCH_VIEW)) {
			firstPerson = !firstPerson;
		}
		
		calculatePitch();
		calculateAngleAroundPlayer();
		
		//if(pitch >= 90) pitch = 90;
		
		if(!firstPerson) {
			//if(pitch <= 0) pitch = 0;
			
			calculateZoom();
			float horizontalDistance = calculateHorizontalDistance();
			float verticalDistance = calculateVerticalDistance();
			calculateCameraPostion(horizontalDistance, verticalDistance);
			this.yaw = 180 - (player.getRotationY() + angleAroundPlayer);
		}
		else {
			//if(pitch <= -90) pitch = -90;
			
			this.yaw = angleAroundPlayer;
			Vector3f playerPosition = player.getPosition();
			position.x = playerPosition.x;
			position.y = playerPosition.y;
			position.z = playerPosition.z;
		}
		
	}
	
	private void calculateCameraPostion(float horizontal, float vertical) {
		float theta = player.getRotationY() + angleAroundPlayer;
		float offsetX = (float) (horizontal * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontal * Math.cos(Math.toRadians(theta)));
		Vector3f playerPosition = player.getPosition();
		position.x = playerPosition.x - offsetX;
		position.y = playerPosition.y + vertical;
		position.z = playerPosition.z - offsetZ;
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch() {
		pitch -= Mouse.getDY() * Configuration.PLAYER_MOUSE_SENSIVITY;
	}
	
	private void calculateAngleAroundPlayer() {
		float angleChange = Mouse.getDX() * Configuration.PLAYER_MOUSE_SENSIVITY;
		angleAroundPlayer -= angleChange;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
