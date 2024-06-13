package Renderer;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import Engine.Maths.Matrix;
import Entities.Entity;
import Models.Model;
import Models.Material;
import Shader.StaticShader;

public class ModelsRenderer {
	
	private StaticShader shader;
	
	private List<Model> models = new ArrayList<Model>();
	
	public ModelsRenderer(StaticShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void processModel(Model model) {
		models.add(model);
	}
	
	public void processEntity(Entity entity) {
		models.add(entity.getModel());
	}
	
	public void processModel(List<Model> models) {
		for(Model model : models) {
			models.add(model);
		}
	}
	
	public void processEntities(List<Entity> entities) {
		for(Entity entity : entities) {
			models.add(entity.getModel());
		}
	}
	
	public void renderModel() {
		for(Model model : models) {
			prepareInstance(model);
			bindVao(model.getVaoID());
			
			shader.loadJointTransform(model.getJointTransforms());
			
			Material modelMaterial = model.getMaterial();
			if(modelMaterial != null) bindTexture(modelMaterial.getTextureID(), modelMaterial.getDamper(), modelMaterial.getReflectivity());
			
			render(model.getVertexCount());
		}
		
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
		GL20.glEnableVertexAttribArray(3);
		GL20.glEnableVertexAttribArray(4);
	}
	
	private void render(int vertexCount) {
		glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
	}	
	
	private void unbindVao() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
		GL20.glDisableVertexAttribArray(4);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Model model) {
		Matrix4f transformationMatrix = Matrix.createTransformation(model.getPosition(), model.getRotation(), model.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
	}
	
	public List<Model> getModels(){
		return models;
	}
	
	public void clearModels() {
		models.clear();
	}
}
