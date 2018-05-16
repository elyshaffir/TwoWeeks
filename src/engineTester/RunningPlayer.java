package engineTester;

import entities.Camera;
import entities.Light;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import gameCom.Client;
import gameUtil.CarPlayer;
import gameUtil.EndScreen;
import gameUtil.OtherCarPlayers;
import gameUtil.WinnerGetter;
import guis.GuiRenderer;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrain.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.io.File;
import java.util.Objects;

class RunningPlayer extends Thread{
    private String ipToConnectTo = "localhost";
    private String ID;
    private boolean fullScreen;
    private Stage primaryStage;

    RunningPlayer(String ipToConnectTo, String ID, boolean fullScreen, Stage primaryStage) {
        if (!Objects.equals(ipToConnectTo, ""))
            this.ipToConnectTo = ipToConnectTo;
        this.ID = ID;
        this.fullScreen = fullScreen;
        this.primaryStage = primaryStage;
    }

    public void run(){
        DisplayManager.createDisplay("Playing as: " + String.valueOf(ID), fullScreen);
        Loader loader = new Loader();
        TextMaster.init(loader);

        FontType font = new FontType(loader.loadTexture("text/arial"), new File("res/text/arial.fnt"));
        GUIText playingAs = new GUIText("Playing as: " + String.valueOf(ID), .5f, font, new Vector2f(0, 0), 1f, true);

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

        OtherCarPlayers.setClient(new Client(ID, ipToConnectTo));
        OtherCarPlayers.getClient().start();

        boolean disconnectedDisplayed = false;

        while (!Display.isCloseRequested()){
            camera.move(localPlayer.getPlayer(), false, true);
            if (!WinnerGetter.getWinners().contains(ID))
                localPlayer.playLocal("terrain/heightmap", terrain, false);

            if (OtherCarPlayers.getClient().isDisconnected() && !disconnectedDisplayed){
                new GUIText("Disconnected from server.", .5f, font, new Vector2f(0, .1f), 1f, true);
                disconnectedDisplayed = true;
            }

            renderer.processEntity(localPlayer.getPlayer());
            renderer.processEntity(localPlayer.getFrontWheels());
            renderer.processEntity(localPlayer.getBackWheels());
            renderer.processTerrain(terrain);
            renderer.render(light, camera);

            OtherCarPlayers.sendCar(localPlayer, ID);
            OtherCarPlayers.loadAllOtherCars(loader);
            OtherCarPlayers.renderAllOtherCars(renderer);

            WinnerGetter.checkWinners(localPlayer, ID);
            TextMaster.render();

            DisplayManager.updateDisplay();

            if (WinnerGetter.allWon(ID)) break;
            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) break;
        }

        OtherCarPlayers.getClient().setDataToSend("KK");
        guiRenderer.cleanUp();
        renderer.cleanUp();
        TextMaster.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

        Platform.runLater(() -> {
            EndScreen s = new EndScreen();
            try {
                s.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
