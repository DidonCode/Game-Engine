package Models;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Models.Animated.Joint;
import Models.Animation.Animation;
import Models.Animation.Animator;

public class Model {
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private Vector3f rotation = new Vector3f(0, 0, 0);
	private int vaoID; 
	private int vertexCount;
	private float scale = 1;
	private Hitbox hitbox = null;
	private Material material = null;
	private Joint rootJoint = null;
	private int jointCount = 0;
	private Animator animator = null;
	
	public Model(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public Model(int vaoID, int vertexCount, List<Vector3f[]> faces) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.hitbox = new Hitbox(faces);
	}
	
	public Model(int vaoID, int vertexCount, List<Vector3f[]> faces, Material material) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.hitbox = new Hitbox(faces);
		this.material = material;
	}
	
	public Model(int vaoID, int vertexCount, Joint rootJoint, int jointCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.rootJoint = rootJoint;
		this.jointCount = jointCount;
		this.animator = new Animator(this);
	}
	
	public Model(int vaoID, int vertexCount, Material material, Joint rootJoint, int jointCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.material = material;
		this.rootJoint = rootJoint;
		this.jointCount = jointCount;
		this.animator = new Animator(this);
	}
	
	public Model(int vaoID, int vertexCount, List<Vector3f[]> faces, Material material, Joint rootJoint, int jointCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.hitbox = new Hitbox(faces);
		this.material = material;
		this.rootJoint = rootJoint;
		this.jointCount = jointCount;
		this.animator = new Animator(this);
	}
	
	public Matrix4f[] getJointTransforms() {
		if(rootJoint == null) return null;
		
		Matrix4f[] jointMatrices = new Matrix4f[jointCount];
		addJointsToArray(rootJoint, jointMatrices);
		return jointMatrices;
	}

	private void addJointsToArray(Joint headJoint, Matrix4f[] jointMatrices) {
		jointMatrices[headJoint.index] = headJoint.getAnimatedTransform();
		for (Joint childJoint : headJoint.children) {
			addJointsToArray(childJoint, jointMatrices);
		}
	}
	
	public void updateAnimation() {
		if(animator == null) return;
		
		animator.update();
	}
	
	public void doAnimation(Animation animation) {
		if(animator == null) return;
		
		animator.doAnimation(animation);
	}
	
	public void stopAnimation() {
		animator.doAnimation(null);
	}
	
	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
	public Joint getRootJoint() {
		return rootJoint;
	}

	public int getJointCount() {
		return jointCount;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public float getScale() {
		return scale;
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

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public void setTexture(int textureID) {
		this.material = new Material(textureID, material.getDamper(), material.getReflectivity());
	}
	
}
