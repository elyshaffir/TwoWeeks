package engineTester;

import entities.*;
import gameCom.Client;
import gameCom.Server;
import gameUtil.CarPlayer;
import gameUtil.EndScreen;
import gameUtil.OtherCarPlayers;
import gameUtil.WinnerGetter;
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

	private final static String ID = "13";

	public static void main(String[] args) {

		DisplayManager.createDisplay(String.valueOf(ID), false);
		Loader loader = new Loader();

		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));
		Camera camera = new Camera(-90, 20, 0);

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("terrain/grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("terrain/mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("terrain/raceEnding"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("terrain/path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("terrain/raceblendMap"));

		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "terrain/raceheightmap");

		GuiRenderer guiRenderer = new GuiRenderer(loader);
		MasterRenderer renderer = new MasterRenderer(loader);

		CarPlayer localPlayer = new CarPlayer(loader, "models/chasi", "textures/blankTexture",
				"models/wheels", "textures/blankTexture");

		localPlayer.getPlayer().increaseRotation(new Vector3f(0, 1, 0), 270);
		localPlayer.getFrontWheels().increaseRotation(new Vector3f(0, 1, 0), 270);
		localPlayer.getBackWheels().increaseRotation(new Vector3f(0, 1, 0), 270);

		OtherCarPlayers.setClient(new Client(ID));
		OtherCarPlayers.getClient().start();

		while (!Display.isCloseRequested()){
			camera.move(localPlayer.getPlayer(), false, true);
			if (!WinnerGetter.getWinners().contains(ID))
				localPlayer.playLocal("terrain/heightmap", terrain);

			renderer.processEntity(localPlayer.getPlayer());
			renderer.processEntity(localPlayer.getFrontWheels());
			renderer.processEntity(localPlayer.getBackWheels());
			renderer.processTerrain(terrain);
			renderer.render(light, camera);

			OtherCarPlayers.sendCar(localPlayer, ID);
			OtherCarPlayers.loadAllOtherCars(loader);
			OtherCarPlayers.renderAllOtherCars(renderer);

			WinnerGetter.checkWinners(localPlayer, ID);

			DisplayManager.updateDisplay();
		}

		// System.out.println(WinnerGetter.getWinners());

		OtherCarPlayers.getClient().setDataToSend("KK");
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		EndScreen.main(args);
	}

}