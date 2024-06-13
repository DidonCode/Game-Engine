package Shader;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Engine.Configuration;
import Engine.Maths.Matrix;
import Entities.Camera;
import Shader.Light.Light;

public class StaticShader extends ShaderProgram {
	
	private static final int DIFFUSE_TEX_UNIT = 0;
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_diffuseMap;
	private int location_jointTransforms;
	private int location_mvpMatrix;
	private int location_plane;
	
	public StaticShader(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
		loadDiffuseMap();
	}

	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
		super.bindAttribute(3, "jointIndices");
		super.bindAttribute(4, "weights");
	}

	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_diffuseMap = super.getUniformLocation("diffuseMap");
		location_jointTransforms = super.getUniformLocation("jointTransforms");
		location_plane = super.getUniformLocation("plane");
		
		location_lightPosition = new int[Configuration.MAX_LIGHTS];
		location_lightColour = new int[Configuration.MAX_LIGHTS];
		location_attenuation = new int[Configuration.MAX_LIGHTS];
		for(int i = 0; i < Configuration.MAX_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}
	
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadDiffuseMap() {
		super.loadInt(location_diffuseMap, DIFFUSE_TEX_UNIT);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadMvpMatrix(Matrix4f matrix) {
		super.loadMatrix(location_mvpMatrix, matrix);
	}
	
	public void loadClipPlane(Vector4f plane) {
		super.loadVector(location_plane, plane);
	}
	
	public void loadLights(List<Light> lights) {
		for(int i = 0; i < Configuration.MAX_LIGHTS; i++) {
			if(i < lights.size()) {
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_lightColour[i], lights.get(i).getColour());
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
			}else {
				super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Matrix.createView(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadJointTransform(Matrix4f[] jointTransformsMatrix) {
		if(jointTransformsMatrix == null) {
			Matrix4f matrix = new Matrix4f();
			Matrix4f.setZero(matrix);
			location_jointTransforms = super.getUniformLocation("jointTransforms[" + 0 + "]");
			super.loadMatrix(location_jointTransforms, matrix);
		}else{
			for(int i = 0; i < jointTransformsMatrix.length; i++) {
				location_jointTransforms = super.getUniformLocation("jointTransforms[" + i + "]");
				super.loadMatrix(location_jointTransforms, jointTransformsMatrix[i]);
			}
		}
	}
}