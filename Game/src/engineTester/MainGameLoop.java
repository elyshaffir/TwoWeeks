package engineTester;

import entities.*;
import gameUtil.CarPlayer;
import guis.GuiRenderer;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrain.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {					
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();

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

		CarPlayer localPlayer = new CarPlayer(loader, "models/chasi", "",
				new Vector3f(250, 100, 250), "models/wheels", "",
				new Vector3f(248, 100, 250), new Vector3f(254.5f, 100, 250));

		while (!Display.isCloseRequested()){
			camera.move(localPlayer.getPlayer(), false);
			localPlayer.playLocal("terrain/heightmap", terrain);
			renderer.processEntity(localPlayer.getPlayer());
			renderer.processEntity(localPlayer.getFrontWheels());
			renderer.processEntity(localPlayer.getBackWheels());
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
