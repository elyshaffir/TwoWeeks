package entities;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Entity3DController {

    private Entity entity;
    private float speed;


    public Entity3DController(Entity entity, float speed) {
        this.entity = entity;
        this.speed = speed;
    }

    public void controller(){
        int forward = Keyboard.KEY_W;
        int backwards = Keyboard.KEY_S;
        int right = Keyboard.KEY_D;
        int left = Keyboard.KEY_A;
        customController(forward, backwards, right, left);
    }

    public void controlByCamera(float sensetivity){
        if (Mouse.isButtonDown(1))
            entity.increaseRotation(0, -Mouse.getDX() * sensetivity, 0);

    }

    private void customController(int forward, int backwards, int right, int left){
        if (Keyboard.isKeyDown(forward)){
            entity.setPosition(new Vector3f(entity.getPosition().x, entity.getPosition().y, entity.getPosition().z + speed));
            entity.setRotation(entity.getRotX(), 90, entity.getRotZ());
        }
        if (Keyboard.isKeyDown(backwards))
        {
            entity.setPosition(new Vector3f(entity.getPosition().x, entity.getPosition().y, entity.getPosition().z - speed));
            entity.setRotation(entity.getRotX(), -90, entity.getRotZ());
        }
        if (Keyboard.isKeyDown(right))
        {
            entity.setPosition(new Vector3f(entity.getPosition().x - speed, entity.getPosition().y, entity.getPosition().z));
            entity.setRotation(entity.getRotX(), 0, entity.getRotZ());
        }
        if (Keyboard.isKeyDown(left))
        {
            entity.setPosition(new Vector3f(entity.getPosition().x + speed, entity.getPosition().y, entity.getPosition().z));
            entity.setRotation(entity.getRotX(), 180, entity.getRotZ());
        }
    }
}
