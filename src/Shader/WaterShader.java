package Shader;

import org.lwjgl.util.vector.Matrix4f;

import Engine.Maths.Matrix;
import Entities.Camera;

public class WaterShader extends ShaderProgram {

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private int location_reflectionTexture;
	private int location_refractionTexture;

	public WaterShader(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
	}

	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_modelMatrix = super.getUniformLocation("modelMatrix");
		location_reflectionTexture = super.getUniformLocation("reflectionTexture");
		location_refractionTexture = super.getUniformLocation("refractionTexture");
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_reflectionTexture, 0);
		super.loadInt(location_refractionTexture, 1);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Matrix.createView(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadModelMatrix(Matrix4f modelMatrix){
		super.loadMatrix(location_modelMatrix, modelMatrix);
	}
}

