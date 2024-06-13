package Shader.Water;

import org.lwjgl.util.vector.Vector3f;

import Engine.Loader;
import Models.Model;

public class Water {
	
	private Model model;
	
	public Water(Vector3f position, float size, Loader loader){
		makeModel(loader);
		model.setPosition(position);
		model.setScale(size);
	}
	
	public Water(Vector3f position, Vector3f rotation, float size, Loader loader){
		makeModel(loader);
		model.setPosition(position);
		model.setRotation(rotation);
		model.setScale(size);
	}
	
	private void makeModel(Loader loader) {
		// Just x and z vectex positions here, y is set to 0 in v.shader
		float[] vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };
		model = loader.loadToVAO(vertices, 2);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
