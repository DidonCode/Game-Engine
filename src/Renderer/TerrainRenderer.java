package Renderer;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import Engine.Maths.Matrix;
import Map.Terrain;
import Models.Model;
import Models.Material;
import Shader.TerrainShader;

public class TerrainRenderer {
	
	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public void renderTerrain(Terrain terrain, Matrix4f toShadowSpace) {
		shader.loadToShadowMapSpaceMatrix(toShadowSpace);
		
		bindVao(terrain.getModel().getVaoID());
		prepareInstance(terrain.getModel());
		
		Material modelMaterial = terrain.getModel().getMaterial();
		if(modelMaterial != null) bindTexture(modelMaterial.getTextureID(), modelMaterial.getDamper(), modelMaterial.getReflectivity());
		
		render(terrain.getModel().getVertexCount());

		unbindVao();
	}
	
	private void bindTexture(int textureID, float damper, float reflectivity) {
		shader.loadShineVariables(damper, reflectivity);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, textureID);
	}
	
	private void bindVao(int vaoID) {
		GL30.glBindVertexArray(vaoID);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
	}
	
	private void render(int vertexCount) {
		glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
	}	
	
	private void unbindVao() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Model model) {
		Matrix4f transformationMatrix = Matrix.createTransformation(model.getPosition(), model.getRotation(), model.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
	}
}
