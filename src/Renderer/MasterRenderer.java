package Renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import Engine.Configuration;
import Entities.Camera;
import Map.Terrain;
import Models.Model;
import Shader.ShadowShader;
import Shader.SkyboxShader;
import Shader.StaticShader;
import Shader.TerrainShader;
import Shader.WaterShader;
import Shader.Light.Light;
import Shader.Skybox.Skybox;

public class MasterRenderer {
	
	private Matrix4f projectionMatrix;
	
	private StaticShader staticShader;
	private SkyboxShader skyboxShader;
	private TerrainShader terrainShader;
	private ShadowShader shadowShader;
	private WaterShader waterShader;
	
	private ModelsRenderer modelsRenderer;
	private SkyboxRenderer skyboxRenderer;
	private TerrainRenderer terrainRenderer;
	private ShadowRenderer shadowRenderer;
	private WaterRenderer waterRenderer;
	
	public MasterRenderer(Camera camera, StaticShader staticShader, SkyboxShader skyboxShader, TerrainShader terrainShader, ShadowShader shadowShader, WaterShader waterShader) {
		this.staticShader = staticShader;
		this.skyboxShader = skyboxShader;
		this.terrainShader = terrainShader;
		this.shadowShader = shadowShader;
		this.waterShader = waterShader;

		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		createProjectionMatrix();
		
		modelsRenderer = new ModelsRenderer(staticShader, projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(skyboxShader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		shadowRenderer = new ShadowRenderer(camera, shadowShader);
		waterRenderer = new WaterRenderer(waterShader, projectionMatrix);
	}
	
	public void render(List<Light> lights, Camera camera, Skybox skybox, Terrain terrain) {
		if(Configuration.SHADOW) {
			List<Model> models = modelsRenderer.getModels();
			shadowRenderer.render(models, lights.get(0));
		}
		
//		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
//		
//		Vector4f clipPlane = new Vector4f(0, 1, 0, -10);
//		
//		waterRenderer.bindReflectionFrameBuffer();
//		staticShader.start();
//		staticShader.loadClipPlane(clipPlane);
//		staticShader.loadLights(lights);
//		staticShader.loadViewMatrix(camera);
//		modelsRenderer.renderModel();
//		staticShader.stop();
//		
//		terrainShader.start();
//		terrainShader.loadClipPlane(clipPlane);
//		terrainShader.loadLights(lights);
//		terrainShader.loadViewMatrix(camera);
//		terrainRenderer.renderTerrain(terrain, shadowRenderer.getToShadowMapSpaceMatrix());
//		terrainShader.stop();
//		
//		//--------------\\
//		
//		clipPlane = new Vector4f(0, -1, 0, 10);
//		
//		waterRenderer.bindRefractionFrameBuffer();
//		staticShader.start();
//		staticShader.loadClipPlane(clipPlane);
//		staticShader.loadLights(lights);
//		staticShader.loadViewMatrix(camera);
//		modelsRenderer.renderModel();
//		staticShader.stop();
//		
//		terrainShader.start();
//		terrainShader.loadClipPlane(clipPlane);
//		terrainShader.loadLights(lights);
//		terrainShader.loadViewMatrix(camera);
//		terrainRenderer.renderTerrain(terrain, shadowRenderer.getToShadowMapSpaceMatrix());
//		terrainShader.stop();
//		
//		waterRenderer.unbindCurrentFrameBuffer();
//		
//		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		
		
		
		
		prepare();
		
		staticShader.start();
		staticShader.loadClipPlane(new Vector4f(0, -1, 0, 100000));
		staticShader.loadLights(lights);
		staticShader.loadViewMatrix(camera);
		modelsRenderer.renderModel();
		modelsRenderer.clearModels();
		staticShader.stop();
		
		terrainShader.start();
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.renderTerrain(terrain, shadowRenderer.getToShadowMapSpaceMatrix());
		terrainShader.stop();
		
		skyboxRenderer.render(skybox, camera);
		
		waterShader.start();
		waterShader.loadViewMatrix(camera);
		waterRenderer.renderWaters();
		waterShader.stop();
		
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
	}

	public void cleanUp() {
		staticShader.cleanUp();
		skyboxShader.cleanUp();
		terrainShader.cleanUp();
		shadowShader.cleanUp();
		shadowRenderer.cleanUp();
		waterShader.cleanUp();
		waterRenderer.cleanUp();
	}
	
	public void prepare() {
		glEnable(GL11.GL_DEPTH_TEST);
		glClearColor(GL_RED, GL_GREEN, GL_BLUE, 1);
		if(Configuration.SHADOW) {
			GL13.glActiveTexture(GL13.GL_TEXTURE5);
			glBindTexture(GL_TEXTURE_2D, getShadowMapTexture());
		}
	}
	
    private void createProjectionMatrix(){
    	projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(Configuration.CAMERA_FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = Configuration.CAMERA_FAR_PLANE - Configuration.CAMERA_NEAR_PLANE;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((Configuration.CAMERA_FAR_PLANE + Configuration.CAMERA_NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * Configuration.CAMERA_NEAR_PLANE * Configuration.CAMERA_FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
    }
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public ModelsRenderer getModelsRenderer() {
		return modelsRenderer;
	}
	
	public WaterRenderer getWaterRenderer() {
		return waterRenderer;
	}
	
	public int getShadowMapTexture() {
		return shadowRenderer.getShadowMap();
	}
}
