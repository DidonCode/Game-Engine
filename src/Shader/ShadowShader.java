package Shader;

import org.lwjgl.util.vector.Matrix4f;

public class ShadowShader extends ShaderProgram {
	
	private int location_mvpMatrix;

	public ShadowShader(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
	}

	protected void bindAttributes() {
		super.bindAttribute(0, "in_position");
		super.bindAttribute(1, "in_textureCoordinates");
	}

	protected void getAllUniformLocations() {
		location_mvpMatrix = super.getUniformLocation("mvpMatrix");
	}

	public void loadMvpMatrix(Matrix4f mvpMatrix){
		super.loadMatrix(location_mvpMatrix, mvpMatrix);
	}
	

}
