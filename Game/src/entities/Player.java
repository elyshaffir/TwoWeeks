package entities;


import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import terrain.Terrain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {

    private static final float TURN_SPEED = 140;
    private static final float GRAVITY = -400;

    private float speed = 50;
    private float currentSpeed = 0;
    private float currentPitchSpeed = 0;
    private float currentRollSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;
    private float yExtra = 0;

    private float lastStableX;
    private float lastStableY;
    private float lastStableZ;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float yExtra) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.yExtra = yExtra;
        this.lastStableX = position.x;
        this.lastStableY = position.y;
        this.lastStableZ = position.z;
    }

    public void move2D(Terrain terrain, String heightMap, boolean spin, boolean doTerrainAdjust){
        checkInputs(0, false, spin, 2f, .5f);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) -(distance * Math.cos(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        if (super.getPosition().y < terrainHeight + yExtra){
            upwardsSpeed = 0;
            super.getPosition().y = terrainHeight + yExtra;
            if (doTerrainAdjust)
                terrainAdjust(terrain, heightMap);
        }
    }


    public void moveAllAxis(){ // // FIXME: Rotation and position should be relative to the model, not the world! (headless mode).
        // FIXME: The camera is fucked with the roll.
        // FIXME: Roll is rolling the opposite direction when facing the opposite direction.
        // TODO: Fix camera on roll, fix roll, then movement.
        checkInputs(.25f, true, false, 0, 0);
        super.increaseRotation(0, 0, currentPitchSpeed * DisplayManager.getFrameTimeSeconds());
        super.increaseRoll(currentRollSpeed * DisplayManager.getFrameTimeSeconds());
        // the roll is relative to the world
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) -(distance * Math.cos(Math.toRadians(super.getRotY())));
        float dy = (float) (distance * Math.tan(Math.toRadians(super.getRotZ())));
        float dz = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, -dy, dz);
    }


    private void terrainAdjust(Terrain terrain, String heightMap){ // FIXME: Precision.
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/" + heightMap + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int VERTEX_COUNT = image.getHeight();
        int xIndex = (int) ((super.getPosition().x * VERTEX_COUNT) / Terrain.SIZE);
        int zIndex = (int) ((super.getPosition().z * VERTEX_COUNT) / Terrain.SIZE);
        Vector3f normal = terrain.calculateNormal(xIndex, zIndex, image);
        float targetRotZ = normal.z * 20;
        targetRotZ *= (float) Math.sin(Math.toRadians(super.getRotY()));
        // super.setRotZ(toRotateZ);
        if (super.getRotZ() < targetRotZ)
            if (targetRotZ - super.getRotZ() < 1) {super.setRotZ(targetRotZ);}
            else {super.increaseRotation(0, 0, 1);}
        else if (super.getRotZ() > targetRotZ)
            if (super.getRotZ() - targetRotZ < 1) {super.setRotZ(targetRotZ);}
            else {super.increaseRotation(0, 0, -1);}
    }

    private void checkFailed(){
        if (this.getPosition().getY() == -32.852943f || this.getPosition().getY() == 1.5f){
            setPosition(new Vector3f(lastStableX, lastStableY, lastStableZ));
            currentSpeed = 0;
            currentTurnSpeed = 0;
            currentPitchSpeed = 0;
            currentRollSpeed = 0;
        }
    }

    private void checkInputs(float mouseSensetivity, boolean controlPitch, boolean spin, float acceleration, float drag){

        if (controlPitch)
            if (Keyboard.isKeyDown(Keyboard.KEY_W)){
                currentPitchSpeed = TURN_SPEED;
            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_S)){
                currentPitchSpeed = -TURN_SPEED;
            }
            else{
                currentPitchSpeed = 0;
            }
        else
            if (Keyboard.isKeyDown(Keyboard.KEY_W)){
                currentSpeed += acceleration;
            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_S)){
                currentSpeed -= acceleration / 3;
            }
            else{
                // currentSpeed = speed;
                if (currentSpeed > 0){
                    currentSpeed -= drag;
                    if (currentSpeed < drag)
                        currentSpeed = 0;
                }
                else if (currentSpeed < 0){
                    currentSpeed += drag;
                    if (currentSpeed > drag)
                        currentSpeed = 0;
                }
            }

        if (mouseSensetivity != 0)
            if (Keyboard.isKeyDown(Keyboard.KEY_D)){
                currentRollSpeed = TURN_SPEED;

            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_A)){
                currentRollSpeed = -TURN_SPEED;
            }
            else{
                currentRollSpeed = 0;
            }
        else
            if (Keyboard.isKeyDown(Keyboard.KEY_D)){
                currentTurnSpeed = -TURN_SPEED;

            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_A)){
                currentTurnSpeed = TURN_SPEED;
            }
            else{
                currentTurnSpeed = 0;
            }

        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
            currentSpeed /= 1.01f;

        if (currentSpeed > 300)
            currentSpeed = 300;

        if (controlPitch)
            currentSpeed = speed;
        if (Mouse.isButtonDown(1) && !controlPitch)
            super.increaseRotation(0, -Mouse.getDX() * mouseSensetivity, Mouse.getDY() * mouseSensetivity);
        else if (Mouse.isButtonDown(1) && mouseSensetivity != 0)
            super.increaseRotation(0, -Mouse.getDX() * mouseSensetivity, 0);
        else if (mouseSensetivity == 0)
            super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        if (spin)
            super.increaseRotation(0, 0, currentSpeed * -GRAVITY * DisplayManager.getFrameTimeSeconds());

        checkFailed();
    }

    public void setPosition(Vector3f position)
    {
        super.setPosition(position);
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }
}
