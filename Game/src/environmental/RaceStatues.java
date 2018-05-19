package environmental;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;

public class RaceStatues {
    private static Statues statues;

    public static void init(Loader loader){
        RawModel head = OBJLoader.loadObjModel("models/head", loader);

        TexturedModel headStaticModel = new TexturedModel(head, new ModelTexture(loader.loadTexture("misc/face")));
        ModelTexture headTexture = headStaticModel.getTexture();
        headTexture.setShineDamper(10);
        headTexture.setReflectivity(10);

        Entity me = new Entity(headStaticModel, new Vector3f(1584, 53, 1751), 0, 0, 0, 1);

        RawModel musicPlayers = OBJLoader.loadObjModel("models/music_statue", loader);

        TexturedModel musicPlayersStaticModel = new TexturedModel(musicPlayers, new ModelTexture(loader.loadTexture("misc/face")));
        ModelTexture texture = musicPlayersStaticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(10);

        Entity musicPlayersStatue = new Entity(musicPlayersStaticModel, new Vector3f(2914, 29.5f, 2362), 0, 270, 0, 2);

        List<Entity> statueEntities = new ArrayList<>();
        statueEntities.add(me);
        statueEntities.add(musicPlayersStatue);
        List<Integer> spinning = new ArrayList<>();
        spinning.add(0);
        statues = new Statues(statueEntities, spinning);
        // Entity guitarist = new Entity(); // [2914.2588, 10.754899, 2362.5066]
        // Entity saxophonist = new Entity();
    }

    public static void render(MasterRenderer renderer){
        statues.renderAllStatues(renderer);
    }

}
