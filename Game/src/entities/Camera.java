package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import java.lang.management.MonitorInfo;


public class Camera {

	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 0;
	private float yaw = 0;
	private float roll = 0;
	private float distanceFromEntity = 50;
	private float baseAngleAroundEntity = 0;
	private float angleAroundEntity = 0;
	private boolean middleMouseButton = false;


	public Camera(){}
	public Camera(float angleAroundEntity){
		this.angleAroundEntity = angleAroundEntity;
		this.baseAngleAroundEntity = angleAroundEntity;
	}


	private void calculateZoom(){
		distanceFromEntity -= Mouse.getDWheel() * .1f;
	}

	private void calculatePitch(){
		if (Mouse.isButtonDown(2)){
			middleMouseButton = true;
			pitch -= Mouse.getDY() * .1f;
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
		if (!Mouse.isButtonDown(2) && middleMouseButton){
			middleMouseButton = false;
			angleAroundEntity = baseAngleAroundEntity;
		}

		float theta = cameraEntity.getRotY() + angleAroundEntity;
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
		position.x = cameraEntity.getPosition().x - offsetX;
		position.y = cameraEntity.getPosition().y + verticalPosition;
		position.z = cameraEntity.getPosition().z - offsetZ;
	}

	public void move(Entity cameraEntity){
		calculateZoom();
		calculatePitch();
		calculateAngleAroundEntity();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(cameraEntity, horizontalDistance, verticalDistance);
		this.yaw = 180 - (cameraEntity.getRotY() + angleAroundEntity);
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
