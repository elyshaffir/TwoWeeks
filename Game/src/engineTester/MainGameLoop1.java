package engineTester;

import entities.*;
import gameCom.Client;
import gameUtil.CarPlayer;
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

public class MainGameLoop1 {

    private final static int ID = 2;

    public static void main(String[] args) {

        DisplayManager.createDisplay(String.valueOf(ID));
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

        GuiRenderer guiRenderer = new GuiRenderer(loader);
        MasterRenderer renderer = new MasterRenderer(loader);

        CarPlayer localPlayer = new CarPlayer(loader, "models/chasi", "textures/blankTexture",
                new Vector3f(250, 100, 250), "models/wheels", "textures/blankTexture",
                new Vector3f(248, 100, 250), new Vector3f(254.5f, 100, 250));

        OtherCarPlayers.setClient(new Client(ID));
        OtherCarPlayers.getClient().start();

        while (!Display.isCloseRequested()){
            camera.move(localPlayer.getPlayer(), false);
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
        System.out.println(WinnerGetter.getWinners());

        OtherCarPlayers.getClient().setDataToSend("KK");
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}