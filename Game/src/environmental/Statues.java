package environmental;

import entities.Entity;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.MasterRenderer;

import java.util.ArrayList;
import java.util.List;

class Statues {
    private ArrayList<Entity> statues;
    private ArrayList<Integer> spinning;

    Statues(List<Entity> statues, List<Integer> spinning){
        this.statues = new ArrayList<>(statues);
        this.spinning = new ArrayList<>(spinning);
    }

    void renderAllStatues(MasterRenderer renderer){
        int index = 0;
        for (Entity statue:statues) {
            if (spinning.contains(index))
                statue.increaseRotation(new Vector3f(0, 1, 0), 1);
            renderer.processEntity(statue);
            index++;
        }
    }
}
