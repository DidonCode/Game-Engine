package Renderer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import Engine.Maths.Matrix;
import Models.Model;
import Shader.WaterShader;
import Shader.Water.Water;
import Shader.Water.WaterFrameBuffers;

public class WaterRenderer {

	private WaterShader shader;
	WaterFrameBuffers fbos;
	
	private List<Water> waters = new ArrayList<Water>();

	public WaterRenderer(WaterShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		this.fbos = new WaterFrameBuffers();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}

	public void processWater(Water water) {
		waters.add(water);
	}
	
	public void renderWaters() {
		prepare();
		
		for (Water water : waters) {
			Model waterModel = water.getModel();
			
			bindVao(waterModel.getVaoID());
			prepareInstance(waterModel);
			render(waterModel.getVertexCount());
		}
		unbindVao();
	}
	
	private void prepare() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getReflectionTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionTexture());
	}
	
	private void bindVao(int vaoID) {
		GL30.glBindVertexArray(vaoID);
		GL20.glEnableVertexAttribArray(0);
	}
	
	private void prepareInstance(Model model) {
		Matrix4f modelMatrix = Matrix.createTransformation(model.getPosition(), model.getRotation(), model.getScale());
		shader.loadModelMatrix(modelMatrix);
	}
	
	private void render(int vertexCount) {
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
	}
	
	private void unbindVao(){
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	public void cleanUp() {
		fbos.cleanUp();
	}
	
	public void bindReflectionFrameBuffer() {
		fbos.bindReflectionFrameBuffer();
	}
	
	public void bindRefractionFrameBuffer() {
		fbos.bindRefractionFrameBuffer();
	}
	
	public void unbindCurrentFrameBuffer() {
		fbos.unbindCurrentFrameBuffer();
	}

}