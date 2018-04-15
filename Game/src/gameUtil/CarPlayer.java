package gameUtil;


import entities.Player;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import terrain.Terrain;
import textures.ModelTexture;

public class CarPlayer {

    private RawModel chasi;
    private TexturedModel chasiStaticModel;
    private ModelTexture chasiTexture;
    private Player player;
    private RawModel wheels;
    private TexturedModel wheelsStaticModel;
    private ModelTexture wheelsTexture;
    private Player frontWheels;
    private Player backWheels;

    private String chasiModelString;
    private String chasiTextureString;
    private String wheelsModelString;
    private String wheelsTextureString;

    public CarPlayer(Loader loader, String chasiModel, String chasiTexture, Vector3f playerLocation, String wheelsModel,
                     String wheelsTexture, Vector3f frontWheelsLocation, Vector3f backWheelsLocation){

        this.chasi = OBJLoader.loadObjModel(chasiModel, loader);
        this.chasiStaticModel = new TexturedModel(chasi, new ModelTexture(loader.loadTexture(chasiTexture)));
        this.chasiTexture = chasiStaticModel.getTexture();
        this.chasiTexture.setShineDamper(1);
        this.chasiTexture.setReflectivity(1);
        this.chasiModelString = chasiModel;
        this.chasiTextureString = chasiTexture;

        this.player = new Player(chasiStaticModel, playerLocation, 0, 0, 0, 1, 1.5f);

        this.wheels = OBJLoader.loadObjModel(wheelsModel, loader);
        this.wheelsStaticModel = new TexturedModel(wheels, new ModelTexture(loader.loadTexture(wheelsTexture)));
        this.wheelsTexture = wheelsStaticModel.getTexture();
        this.wheelsTexture.setShineDamper(1);
        this.wheelsTexture.setReflectivity(1);
        this.wheelsModelString = wheelsModel;
        this.wheelsTextureString = wheelsTexture;

        this.frontWheels = new Player(wheelsStaticModel, frontWheelsLocation, 0, 0, 0, 1, 1.5f);
        this.backWheels = new Player(wheelsStaticModel, backWheelsLocation, 0, 0, 0, 1, 1.5f);
    }

    public void playLocal(String heightMap, Terrain terrain){
        player.move2D(terrain, heightMap, false, true);
        frontWheels.move2D(terrain, heightMap, true, true);
        backWheels.move2D(terrain, heightMap, true, true);
        frontWheels.stick(player, 2);
        backWheels.stick(player, -4.5f);
    }

    public Player getFrontWheels() {
        return frontWheels;
    }

    public Player getBackWheels() {
        return backWheels;
    }

    public Player getPlayer() {
        return player;
    }

    String getChasiModelString() {
        return chasiModelString;
    }

    String getChasiTextureString() {
        return chasiTextureString;
    }

    String getWheelsModelString() {
        return wheelsModelString;
    }

    String getWheelsTextureString() {
        return wheelsTextureString;
    }
}
