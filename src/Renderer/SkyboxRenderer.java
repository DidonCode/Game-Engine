package Renderer;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import Engine.Maths.Matrix;
import Entities.Camera;
import Shader.SkyboxShader;
import Shader.Skybox.Skybox;

public class SkyboxRenderer {

	private SkyboxShader shader;
	
	public SkyboxRenderer(SkyboxShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(Skybox skybox, Camera camera) {
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadTransformationMatrix(Matrix.createTransformation(skybox.getPosition(), skybox.getRotation(), 1));
		GL30.glBindVertexArray(skybox.getCube().getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, skybox.getDayTexture());
		glDrawArrays(GL_TRIANGLES, 0, skybox.getCube().getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
}
