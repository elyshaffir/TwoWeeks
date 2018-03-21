package entities;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;


public class Camera {

	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 0;
	private float basePitch = 0;
	private float yaw = 0;
	private float roll = 0;
	private float distanceFromEntity = 50;
	private float baseAngleAroundEntity = 0;
	private float angleAroundEntity = 0;
	private float angleAroundEntityPitch = 0;
	private float angleAroundEntityRoll = 0;
	private boolean middleMouseButton = false;


	public Camera(){}
	public Camera(float angleAroundEntity, float pitch, float roll){
		this.angleAroundEntity = angleAroundEntity;
		this.baseAngleAroundEntity = angleAroundEntity;
		this.pitch = pitch;
		this.basePitch = pitch;
		this.angleAroundEntityPitch = pitch;
		this.roll = roll;
		this.angleAroundEntityRoll = roll;
	}


	private void calculateZoom(){
		distanceFromEntity -= Mouse.getDWheel() * .1f;
	}

	private void calculatePitch(){
		if (Mouse.isButtonDown(2)){
			middleMouseButton = true;
			angleAroundEntityPitch -= Mouse.getDY() * .1f;
		}
	}


	private void calculateAngleAroundEntity(){
		if (Mouse.isButtonDown(2)){
			middleMouseButton = true;
			angleAroundEntity -= Mouse.getDX() * .1f;
		}
	}

	private float calculateHorizontalDistance(){
		return (float) (distanceFromEntity * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance(){
		return (float) (distanceFromEntity * Math.sin(Math.toRadians(pitch)));
	}

	private void calculateCameraPosition(Entity cameraEntity, float horizontalDistance, float verticalPosition){
		if (!Mouse.isButtonDown(2) && middleMouseButton && !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
			middleMouseButton = false;
			angleAroundEntity = baseAngleAroundEntity;
			angleAroundEntityPitch = basePitch;
		}

		float theta = cameraEntity.getRotY() + angleAroundEntity;
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
		position.x = cameraEntity.getPosition().x - offsetX;
		position.y = cameraEntity.getPosition().y + verticalPosition;
		position.z = cameraEntity.getPosition().z - offsetZ;
	}

	public void move(Entity cameraEntity, boolean followPitch){
		calculateZoom();
		calculatePitch();
		calculateAngleAroundEntity();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(cameraEntity, horizontalDistance, verticalDistance);
		this.yaw = 180 - (cameraEntity.getRotY() + angleAroundEntity);
		if (followPitch)
			this.pitch = (cameraEntity.getRotZ() + angleAroundEntityPitch);
		else
			this.pitch = (angleAroundEntityPitch);
		this.roll = (cameraEntity.getRotX() + angleAroundEntityRoll);
	}


	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void setAngleAroundEntity(float angleAroundEntity) {
		this.angleAroundEntity = angleAroundEntity;
	}
}
