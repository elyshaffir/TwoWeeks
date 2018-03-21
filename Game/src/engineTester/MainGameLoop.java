package engineTester;

import entities.*;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrain.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;


public class MainGameLoop {

	public static void main(String[] args) {					
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();

		RawModel model = OBJLoader.loadObjModel("models/chasi", loader);
		// RawModel model = OBJLoader.loadObjModel("models/wheels", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("skybox/back")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(1);
		texture.setReflectivity(1);

		Player player = new Player(staticModel, new Vector3f(250, 100, 250), 0, 0, 0, 1, 1.5f);

		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));
		Camera camera = new Camera(-90, 20, 0);

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("terrain/grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("terrain/mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("terrain/grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("terrain/path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("terrain/blendMap"));

		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "terrain/heightmap");

		// List<GuiTexture> guis = new ArrayList<>();
		// GuiTexture gui = new GuiTexture(loader.loadTexture("misc/image"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		// guis.add(gui);


		GuiRenderer guiRenderer = new GuiRenderer(loader);
		MasterRenderer renderer = new MasterRenderer(loader);
		
		
		while (!Display.isCloseRequested()){
			camera.move(player, false);
			player.move2D(terrain, "terrain/heightmap", false);
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.render(light, camera);
			// guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
		guiRenderer.cleanUp();
		renderer.cleanUp();		
		loader.cleanUp();
		DisplayManager.closeDisplay();		
	}

}
