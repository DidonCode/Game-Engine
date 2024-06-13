package Map;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import Engine.Configuration;
import Engine.Maths.Vector;
import Entities.Camera;
import Entities.Entity;
import Entities.Player;
import Models.Model;
import Renderer.MasterRenderer;
import Shader.Light.Light;
import Shader.Skybox.Skybox;

public class World {
	
	private MasterRenderer masterRenderer;
	
	private List<Model> models;
	private List<Entity> entities;
	private Player player;
	
	public World(MasterRenderer masterRenderer, Player player) {
		this.masterRenderer = masterRenderer;
		this.player = player;
		this.models = new ArrayList<Model>();
		this.entities = new ArrayList<Entity>();
	}
	
	public void update() {
		for(Entity entity : entities) {
			entity.update();
			masterRenderer.getModelsRenderer().processEntity(entity);
		}
		float[] collision = player.collision(null, null, null);
		player.increasePosition(collision[0], collision[1], collision[2]);
		for(Model model : getNearbyModel(player.getPosition(), Configuration.RENDER_DISTANCE)) {
			collision = player.collision(model.getHitbox(), model.getPosition(), model.getRotation());
			player.increasePosition(collision[0], collision[1], collision[2]);
			masterRenderer.getModelsRenderer().processModel(model);
		}
	}
	
	public void render(List<Light> light, Camera camera, Skybox skybox, Terrain terrain) {
		masterRenderer.render(light, camera, skybox, terrain);
	}
	
	public void addModel(Model model) {
		models.add(model);
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	private List<Model> getNearbyModel(Vector3f position, int distance) {
		List<Model> nearbyModel = new ArrayList<Model>();	
		
		for(Model model : models) {
			if(Vector.dist(model.getPosition(), position) <= distance) {
				nearbyModel.add(model);
			}
		}
		
		return nearbyModel;
	}
}
