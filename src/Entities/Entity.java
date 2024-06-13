package Entities;

import org.lwjgl.util.vector.Vector3f;

import Engine.Configuration;
import Engine.Maths.Vector;
import Models.Hitbox;
import Models.Model;
import Models.Animation.Animation;

public abstract class Entity {

	private Vector3f position, acceleration, rotation;
	protected Model model;
	
	public Entity(Model model) {
		this.position = new Vector3f(0, 0, 0);
		this.acceleration = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.model = model;
	}
	
	public Entity(Vector3f position, Model model) {
		this.position = position;
		this.acceleration = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.model = model;
	}
	
	public Entity(Vector3f position, Vector3f rotation, Model model) {
		this.position = position;
		this.acceleration = new Vector3f(0, 0, 0);
		this.rotation = rotation;
		this.model = model;
	}
	
	public abstract void update();

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
		
		this.model.setPosition(position);
	}
	
	public void setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
		
		this.model.setPosition(position);
	}
	
	public float getPositionX() {
		return position.x;
	}

	public float getPositionY() {
		return position.y;
	}
	
	public float getPositionZ() {
		return position.z;
	}
	
	public void increasePosition(float positionX, float positionY, float positionZ) {
		this.position.x += positionX;
		this.position.y += positionY;
		this.position.z += positionZ;
		
		this.model.setPosition(position);
	}
	
	public void doAnimation(Animation animation) {
		this.model.doAnimation(animation);
	}
	
	public void updateAnimation() {
		this.model.updateAnimation();
	}
	
	public void stopAnimation() {
		this.model.stopAnimation();
	}
	
	public float[] collision(Hitbox hitbox, Vector3f position, Vector3f rotation) {
		boolean hitX = false, hitY = false, hitZ = false;
		float xp = 0, yp = 0, zp = 0;
		
		if(model != null && position != null) {
			hitX = Hitbox.hit(this.model.getHitbox(), Vector.add(this.position, this.acceleration.x, 0, 0), this.rotation, hitbox, position, rotation);
			hitY = Hitbox.hit(this.model.getHitbox(), Vector.add(this.position, 0, this.acceleration.y, 0), this.rotation, hitbox, position, rotation);
			hitZ = Hitbox.hit(this.model.getHitbox(), Vector.add(this.position, 0, 0, this.acceleration.z), this.rotation, hitbox, position, rotation);
		}else {
			return new float[] {acceleration.x, acceleration.y, acceleration.z};
		}
		
		if(!hitX) {
			xp = acceleration.x;	
		}else {
			acceleration.x = 0;
		}
		
		if(!hitY) {
			yp = acceleration.y;	
		}else {
			acceleration.y = 0;
		}
		
		if(!hitZ) {
			zp = acceleration.z;
		}else {
			acceleration.z = 0;
		}
		
		return new float[] {xp, yp, zp};
		
	}
	
	protected void move(float xa, float ya, float za) {
		if(this.position.y <= 0) {
			float elasticity = Math.abs(this.acceleration.y * this.model.getMaterial().getElasticity());
			setPosition(position.x, 0, position.z);
			setAcceleration(acceleration.x, elasticity, acceleration.z);
		}
		
		increaseAcceleration(0, -model.getMaterial().getMass() * Configuration.GRAVITY, 0);
		
		if(xa != 0) setAcceleration(this.acceleration.x + xa, this.acceleration.y, this.acceleration.z);
		if(ya != 0) setAcceleration(this.acceleration.x, this.acceleration.y + ya, this.acceleration.z);
		if(za != 0) setAcceleration(this.acceleration.x, this.acceleration.y, this.acceleration.z + za);

		float viscosity = this.getModel().getMaterial().getViscosity();
		increaseAcceleration(-this.acceleration.x * viscosity, 0, -this.acceleration.z * viscosity);
	}
	
	public Vector3f getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector3f acceleration) {
		this.acceleration = acceleration;
	}
	
	public void setAcceleration(float accelerationX, float accelerationY, float accelerationZ) {
		this.acceleration.x = accelerationX;
		this.acceleration.y = accelerationY;
		this.acceleration.z = accelerationZ;
	}

	public float getAccelerationX() {
		return acceleration.x;
	}

	public float getAccelerationY() {
		return acceleration.y;
	}
	
	public float getAccelerationZ() {
		return acceleration.z;
	}
	
	public void increaseAcceleration(float accelerationX, float accelerationY, float accelerationZ) {
		this.acceleration.x += accelerationX;
		this.acceleration.y += accelerationY;
		this.acceleration.z += accelerationZ;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public float getRotationX() {
		return rotation.x;
	}

	public float getRotationY() {
		return rotation.y;
	}
	
	public float getRotationZ() {
		return rotation.z;
	}
	
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
		
		this.model.setRotation(rotation);
	}
	
	public void increaseRotation(float rotationX, float rotationY, float rotationZ) {
		this.rotation.x += rotationX;
		this.rotation.y += rotationY;
		this.rotation.z += rotationZ;
		
		this.model.setRotation(rotation);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
