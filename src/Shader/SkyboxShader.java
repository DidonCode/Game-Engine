package Shader;

import org.lwjgl.util.vector.Matrix4f;

import Engine.Maths.Matrix;
import Entities.Camera;

public class SkyboxShader extends ShaderProgram {

	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_transformationMatrix;
	
	public SkyboxShader(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
	}

	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadViewMatrix(Camera camera){
		Matrix4f matrix = Matrix.createView(camera);
		super.loadMatrix(location_viewMatrix, matrix);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
}
