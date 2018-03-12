package engineTester;

import entities.Entity3DController;
import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrain.Terrain;
import textures.ModelTexture;


public class MainGameLoop {

	public static void main(String[] args) {					
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();	
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(10);
		
		Entity dragon = new Entity(staticModel, new Vector3f(250, 1, 250), 0, 0, 0, 1);
		Entity3DController entity3DController = new Entity3DController(dragon, .5f);

		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(0, 1, 0));
		Terrain terrain = new Terrain(0,0, loader, new ModelTexture(loader.loadTexture("image")));
		Camera camera = new Camera(-90);
		
		MasterRenderer renderer = new MasterRenderer();
		
		
		while (!Display.isCloseRequested()){
			camera.move(dragon);
			entity3DController.controlByCamera(.25f);
			renderer.processTerrain(terrain);
			renderer.processEntity(dragon);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();			
		}
		renderer.cleanUp();		
		loader.cleanUp();
		DisplayManager.closeDisplay();		
	}

}
