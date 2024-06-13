package Engine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Engine.Maths.Vector;
import Entities.Camera;
import Entities.Player;
import Interface.Screen;
import Interface.Guis.Hud;
import Interface.Guis.Elements.Button;
import Map.Terrain;
import Map.World;
import Models.Material;
import Models.Model;
import Models.Animation.Animation;
import Models.Animation.AnimationLoader;
import Renderer.MasterRenderer;
import Shader.ShadowShader;
import Shader.SkyboxShader;
import Shader.StaticShader;
import Shader.TerrainShader;
import Shader.WaterShader;
import Shader.Light.Light;
import Shader.Skybox.Skybox;
import Shader.Water.Water;

public class GameLoop {

	public static void main(String[] args) {
		Screen screen = new Screen("ForReal [ENGINE]", 1280, 800, 60, false);
		screen.create();
		
		//-------------------------\\
		
		Button button = new Button(Display.getWidth() - 600, 0, 600, 400);
		
		Hud hud = new Hud();
		hud.addElement(button);
		
		screen.setHud(hud);
		
		//-------------------------\\
		
		Loader loader = new Loader();
		
		Material animatedMaterial = new Material(loader.loadTexture("ForReal_Data/diffuse.png"), 4, 0);
		Material crateMaterial = new Material(loader.loadTexture("ForReal_Data/Textures/Models/box.png"), 4, 0);
		Material stallMaterial = new Material(loader.loadTexture("ForReal_Data/Textures/Models/stallTexture.png"), 4, 0);
		
		Model crateModel = loader.loadObjModel("ForReal_Data/Objects/box.obj");
		Model stallModel = loader.loadObjModel("ForReal_Data/Objects/stall.obj");
		Model roadModel = loader.loadObjModel("ForReal_Data/Objects/road.obj");
		Model animatedModel = loader.loadAnimated("ForReal_Data/model.dae");
		
		Animation animation = AnimationLoader.loadAnimation("ForReal_Data/model.dae");
		animation.toString();
		
		//-------------------------\\
		
		crateModel.setMaterial(crateMaterial);
		roadModel.setMaterial(crateMaterial);
		animatedModel.setMaterial(animatedMaterial);
		stallModel.setMaterial(stallMaterial);
		Mouse.setGrabbed(true);
		stallModel.setPosition(new Vector3f(50, 0, 50));
		
		crateModel.setScale(5);
		
		stallModel.setScale(5);
		
		//-------------------------\\
		
		Camera camera = new Camera();
		Player player = new Player(new Vector3f(5, 15, 5), crateModel, camera);
		camera.setPlayer(player);
		
		//-------------------------\\
		
		Terrain terrain = new Terrain(2, 2, loader, crateMaterial);
		
		Skybox skybox = new Skybox();
		skybox.create(loader);
		
		Water water = new Water(new Vector3f(0, 0, 0), 10, loader);
		
		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(10000, 15000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
		//Light spot = new Light(new Vector3f(20, 10, 20), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f));
		lights.add(sun);
		//lights.add(spot);
		
		StaticShader staticShader = new StaticShader("ForReal_Data/Shaders/Static/StaticVertexShader.txt", "ForReal_Data/Shaders/Static/StaticFragmentShader.txt");
		SkyboxShader skyboxShader = new SkyboxShader("ForReal_Data/Shaders/Skybox/SkyboxVertexShader.txt", "ForReal_Data/Shaders/Skybox/SkyboxFragmentShader.txt");
		ShadowShader shadowShader = new ShadowShader("ForReal_Data/Shaders/Shadow/ShadowVertexShader.txt", "ForReal_Data/Shaders/Shadow/ShadowFragmentShader.txt");
		TerrainShader terrainShader = new TerrainShader("ForReal_Data/Shaders/Terrain/TerrainVertexShader.txt", "ForReal_Data/Shaders/Terrain/TerrainFragmentShader.txt");
		WaterShader waterShader = new WaterShader("ForReal_Data/Shaders/Water/WaterVertexShader.txt", "ForReal_Data/Shaders/Water/WaterFragmentShader.txt");
		
		MasterRenderer masterRenderer = new MasterRenderer(camera, staticShader, skyboxShader, terrainShader, shadowShader, waterShader);
		
		button.getStyle().background = new Interface.Guis.Render.Texture(masterRenderer.getShadowMapTexture(), 0, 0, null);
		System.out.println(button.getStyle().background);
		//-------------------------\\
		
//		Vector3f left = new Vector3f(10, 10, 10);
//		Vector3f right = new Vector3f(5, 5, 5);
//		Vector3f dest = new Vector3f(0, 0, 0);
//		
//		Vector.add(left, right, dest);
//		System.out.println(dest);
		
		World world = new World(masterRenderer, player);
		world.addModel(stallModel);
		world.addEntity(player);
		masterRenderer.getWaterRenderer().processWater(water);
		while(screen.isRunning()) {
			screen.update();
			screen.clear();
			
			world.update();
			camera.update();
			
			world.render(lights, camera, skybox, terrain);
			//screen.render();
		}
		
		
		loader.cleanUp();
		masterRenderer.cleanUp();
		screen.destroy();
	}

}
