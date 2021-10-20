package org.minecraft.entity;

import org.minecraft.Window;
import org.minecraft.listener.Keyboard;
import org.minecraft.listener.Mouse;
import org.minecraft.util.vector.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private static final float SPEED = 0.15f;
    private static final float SENSITIVITY = 0.5f;

    private Vector3f position, rotation;

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void move() {
        
        float speed = SPEED * Window.getFrameTimeSeconds();
        float sensitivity = SENSITIVITY * Window.getFrameTimeSeconds();
        
        float x = (float) Math.sin(-Math.toRadians(rotation.getY())) * speed;
        float z = (float) Math.cos(-Math.toRadians(rotation.getY())) * speed;

        if (Keyboard.isKeyPressed(GLFW_KEY_A)) position = Vector3f.add(position, new Vector3f(-z, 0, x),position);
        if (Keyboard.isKeyPressed(GLFW_KEY_D)) position = Vector3f.add(position, new Vector3f(z, 0, -x),position);
        if (Keyboard.isKeyPressed(GLFW_KEY_W)) position = Vector3f.add(position, new Vector3f(-x, 0, -z),position);
        if (Keyboard.isKeyPressed(GLFW_KEY_S)) position = Vector3f.add(position, new Vector3f(x, 0, z),position);
        if (Keyboard.isKeyPressed(GLFW_KEY_SPACE)) position = Vector3f.add(position, new Vector3f(0, speed, 0),position);
        if (Keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) position = Vector3f.add(position, new Vector3f(0, -speed, 0),position);

        float dx = -Mouse.getDX();
        float dy = -Mouse.getDY();

        rotation = Vector3f.add(rotation, new Vector3f(dy * sensitivity, dx * sensitivity, 0),rotation);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
