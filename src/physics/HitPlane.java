package physics;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

class HitPlane {
    private Vector2f dimensions;
    private Vector3f position;
    private Vector3f rotation;
    private List<HitPlane> collidingWith;

    public HitPlane(Vector2f dimensions, Vector3f position, Vector3f rotation) {
        this.dimensions = dimensions;
        this.position = position;
        this.rotation = rotation;
        this.collidingWith = new ArrayList<>();
    }

    public Vector2f getDimensions() {
        return dimensions;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public List<HitPlane> getCollidingWith() {
        return collidingWith;
    }

    public boolean isColliding(HitPlane hitPlane){
        return true; // TODO: Me
    }

    public boolean isColliding(HitPlane[] hitPlanes){
        for (HitPlane hitPlane:hitPlanes)
            if (isColliding(hitPlane))
                return true;
        return false;
    }

    public void addCollider(HitPlane hitPlane){
        collidingWith.add(hitPlane);
    }
}
