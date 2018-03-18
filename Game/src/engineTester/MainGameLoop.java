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
import textures.TerrainTexture;
import textures.TerrainTexturePack;


public class MainGameLoop {

	public static void main(String[] args) {					
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();	
		
		RawModel model = OBJLoader.loadObjModel("person", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(10);


		Player player = new Player(staticModel, new Vector3f(250, 100, 250), 0, 0, 0, 1);

		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));
		Camera camera = new Camera(-90, 20, 0);

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap");

		MasterRenderer renderer = new MasterRenderer(loader);
		
		
		while (!Display.isCloseRequested()){
			camera.move(player);
			player.move2D(terrain);
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();			
		}
		renderer.cleanUp();		
		loader.cleanUp();
		DisplayManager.closeDisplay();		
	}

}
