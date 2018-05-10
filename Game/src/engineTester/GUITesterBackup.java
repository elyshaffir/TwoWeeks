package engineTester;


import guis.GuiRenderer;
import guis.GuiTexture;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import renderEngine.DisplayManager;
import renderEngine.Loader;

import java.util.ArrayList;
import java.util.List;

public class GUITesterBackup{

    private static final float BUTTON_SCALE = .2f;

    private static float getLocalX(float globalX){
        return globalX / DisplayManager.getWidth();
    }

    private static float getLocalY(float globalY){
        return globalY / DisplayManager.getHeight();
    }

    public static void main(String[] args){
        DisplayManager.createDisplay("SS", true);
        Loader loader = new Loader();

        List<GuiTexture> guis = new ArrayList<>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("misc/image"), new Vector2f(0, 0), new Vector2f(BUTTON_SCALE, BUTTON_SCALE));
        GuiTexture gui1 = new GuiTexture(loader.loadTexture("terrain/grassy2"), new Vector2f(1f, .1f), new Vector2f(BUTTON_SCALE, BUTTON_SCALE));
        GuiTexture gui2 = new GuiTexture(loader.loadTexture("terrain/mud"), new Vector2f(.2f, .2f), new Vector2f(BUTTON_SCALE, BUTTON_SCALE));
        GuiTexture gui3 = new GuiTexture(loader.loadTexture("terrain/raceEnding"), new Vector2f(.3f, .3f), new Vector2f(BUTTON_SCALE, BUTTON_SCALE));
        GuiTexture gui4 = new GuiTexture(loader.loadTexture("terrain/path"), new Vector2f(.4f, .4f), new Vector2f(BUTTON_SCALE, BUTTON_SCALE));
        guis.add(gui);
        guis.add(gui1);
        guis.add(gui2);
        guis.add(gui3);
        guis.add(gui4);


        GuiRenderer guiRenderer = new GuiRenderer(loader);

        while (!Display.isCloseRequested()){
            System.out.println(Mouse.getX() + ", " + Mouse.getY());
            guiRenderer.render(guis);
            DisplayManager.updateDisplay();
        }
        guiRenderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
        MainGameLoop.main(new String[]{});
    }
}