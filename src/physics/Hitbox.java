package physics;

public class Hitbox { // TODO: Me.

    private static final int numberOfPlanes = 6;
    private HitPlane[] planes;

    public Hitbox(HitPlane[] planes) {
        this.planes = planes;
        if (this.planes.length != numberOfPlanes)
            this.planes = new HitPlane[numberOfPlanes];
    }
}
