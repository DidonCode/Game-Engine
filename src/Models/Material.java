package Models;

public class Material {
	
	private int textureID;
	
	private float damper = 4;
	private float reflectivity = 0;
	
	private float mass = 1f;
	private float elasticity = 0;
	private float viscosity = 0.1f;
	
	public Material(int textureID) {
		this.textureID = textureID;
	}
	
	public Material(int textureID, float damper, float reflectivity) {
		this.textureID = textureID;
		this.damper = damper;
		this.reflectivity = reflectivity;
	}
	
	public int getTextureID() {
		return this.textureID;
	}

	public float getDamper() {
		return damper;
	}

	public void setDamper(float damper) {
		this.damper = damper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getElasticity() {
		return elasticity;
	}

	public void setElasticity(float elasticity) {
		if(elasticity > 1) { this.elasticity = 1; }
		else if(elasticity < 0) { this.elasticity = 0; }
		else { this.elasticity = elasticity; }
	}

	public float getViscosity() {
		return viscosity;
	}

	public void setViscosity(float viscosity) {
		this.viscosity = viscosity;
	}
	
}
