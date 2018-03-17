package entities;


import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

public class Player extends Entity {

    private static final float turnSpeed = 70;

    private float speed = 50;
    private float currentSpeed = 0;
    private float currentPitchSpeed = 0;
    private float currentRollSpeed = 0;
    private float currentTurnSpeed = 0;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move2D(){
        checkInputs(0, false, .1f);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) -(distance * Math.cos(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
    }

    public void moveAllAxis(){ // // FIXME: Rotation and position should be relative to the model, not the world! (headless mode).
        // FIXME: The camera is fucked with the roll.
        // FIXME: Roll is rolling the opposite direction when facing the opposite direction.
        // TODO: Fix camera on roll, fix roll, then movement.
        checkInputs(.25f, true, 0);
        super.increaseRotation(0, 0, currentPitchSpeed * DisplayManager.getFrameTimeSeconds());
        super.increaseRoll(currentRollSpeed * DisplayManager.getFrameTimeSeconds());
        // the roll is relative to the world
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) -(distance * Math.cos(Math.toRadians(super.getRotY())));
        float dy = (float) (distance * Math.tan(Math.toRadians(super.getRotZ())));
        float dz = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, -dy, dz);
    }

    private void checkInputs(float mouseSensetivity, boolean controlPitch, float acceleration){
        if (controlPitch)
            if (Keyboard.isKeyDown(Keyboard.KEY_W)){
                currentPitchSpeed = turnSpeed;
            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_S)){
                currentPitchSpeed = -turnSpeed;
            }
            else{
                currentPitchSpeed = 0;
            }
        else
            if (Keyboard.isKeyDown(Keyboard.KEY_W)){
                currentSpeed += acceleration;
            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_S)){
                currentSpeed -= acceleration;
            }
            else{
                currentSpeed = speed;
            }

        if (mouseSensetivity != 0)
            if (Keyboard.isKeyDown(Keyboard.KEY_D)){
                currentRollSpeed = turnSpeed;

            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_A)){
                currentRollSpeed = -turnSpeed;
            }
            else{
                currentRollSpeed = 0;
            }
        else
            if (Keyboard.isKeyDown(Keyboard.KEY_D)){
                currentTurnSpeed = -turnSpeed;

            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_A)){
                currentTurnSpeed = turnSpeed;
            }
            else{
                currentTurnSpeed = 0;
            }

        if (controlPitch)
            currentSpeed = speed;
        if (Mouse.isButtonDown(1) && !controlPitch)
            super.increaseRotation(0, -Mouse.getDX() * mouseSensetivity, Mouse.getDY() * mouseSensetivity);
        else if (Mouse.isButtonDown(1) && mouseSensetivity != 0)
            super.increaseRotation(0, -Mouse.getDX() * mouseSensetivity, 0);
        else if (mouseSensetivity == 0)
            super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public static float getTurnSpeed() {
        return turnSpeed;
    }

    public float getCurrentRollSpeed() {
        return this.currentRollSpeed;
    }
}
