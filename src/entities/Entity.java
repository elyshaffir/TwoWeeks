package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;

public class Entity {

	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;

	public Entity(TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {		
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public void increasePosition(float dx, float dy, float dz){
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	public void increaseRotation(Vector3f axis, float w){
		this.rotX += axis.x * w;
		this.rotY += axis.y * w;
		this.rotZ += axis.z * w;
	}

	void increaseRotation(float dx, float dy, float dz){
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public void setRotation(float dx, float dy, float dz){
		this.rotX = dx;
		this.rotY = dy;
		this.rotZ = dz;
	}

	void increaseRoll(float angle){
		float validRotY = this.rotY % 360;
		float toRotateX = (float) -Math.cos(Math.toRadians(validRotY));
		float toRotateZ = (float) Math.sin(Math.toRadians(validRotY));
		increaseRotation(angle * toRotateX, 0, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_R))
			System.out.println(rotY + ": " + toRotateX + ", " + toRotateZ);
	}

	public void stick(Player player, float forward){
		setPosition(
				new Vector3f(
						player.getPosition().x + (float) -(forward * Math.cos(Math.toRadians(player.getRotY()))),
						player.getPosition().y,
						player.getPosition().z + (float) (forward * Math.sin(Math.toRadians(player.getRotY())))
				)
		);
	}

	public TexturedModel getModel() {
		return model;
	}
	
	public void setModel(TexturedModel model) {
		this.model = model;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public float getRotX() {
		return rotX;
	}
	
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}
	
	public float getRotY() {
		return rotY;
	}
	
	public void setRotY(float rotY) {
		this.rotY = rotY;
	}
	
	public float getRotZ() {
		return rotZ;
	}
	
	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
}
