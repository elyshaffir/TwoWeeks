package engineTester;

import entities.*;
import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrain.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;


public class MainGameLoop {

	public static void main(String[] args) {					
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();	
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(10);


		Player player = new Player(staticModel, new Vector3f(250, 3, 250), 0, 180, 0, 1);

		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(0, 1, 0));
		Camera camera = new Camera(-90, 20, 0);

		List<Terrain> terrainList = new ArrayList<>();
		Terrain terrain1 = new Terrain(0,0, loader, new ModelTexture(loader.loadTexture("image")));
		Terrain terrain2= new Terrain(1,0, loader, new ModelTexture(loader.loadTexture("image")));
		Terrain terrain3 = new Terrain(0,1, loader, new ModelTexture(loader.loadTexture("image")));
		Terrain terrain4 = new Terrain(-1,0, loader, new ModelTexture(loader.loadTexture("image")));
		Terrain terrain5 = new Terrain(0,-1, loader, new ModelTexture(loader.loadTexture("image")));
		Terrain terrain6 = new Terrain(1,-1, loader, new ModelTexture(loader.loadTexture("image")));
		Terrain terrain7 = new Terrain(-1,-1, loader, new ModelTexture(loader.loadTexture("image")));
		Terrain terrain8 = new Terrain(-1,1, loader, new ModelTexture(loader.loadTexture("image")));
		Terrain terrain9 = new Terrain(1,1, loader, new ModelTexture(loader.loadTexture("image")));
		terrainList.add(terrain1);
		terrainList.add(terrain2);
		terrainList.add(terrain3);
		terrainList.add(terrain4);
		terrainList.add(terrain5);
		terrainList.add(terrain6);
		terrainList.add(terrain7);
		terrainList.add(terrain8);
		terrainList.add(terrain9);
		
		MasterRenderer renderer = new MasterRenderer();
		
		
		while (!Display.isCloseRequested()){
			camera.move(player);
			player.move2D();
			renderer.processEntity(player);

			for (Terrain terrain:terrainList)
				renderer.processTerrain(terrain);

			renderer.render(light, camera);
			DisplayManager.updateDisplay();			
		}
		renderer.cleanUp();		
		loader.cleanUp();
		DisplayManager.closeDisplay();		
	}

}
