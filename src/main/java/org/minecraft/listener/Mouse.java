package org.minecraft.listener;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public final class Mouse {

    private static Mouse instance;

    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;

    private boolean[] mouseButtonPressed = new boolean[3];
    private boolean isDragging;

    private Mouse() {
        this.scrollX = 0;
        this.scrollY = 0;
        this.xPos = 0;
        this.yPos = 0;
        this.lastX = 0;
        this.lastY = 0;
    }

    public static Mouse get() {
        if (Mouse.instance == null)
            Mouse.instance = new Mouse();

        return Mouse.instance;
    }

    public static void mousePosCallback(long window, double xPos, double yPos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;

        get().xPos = xPos;
        get().yPos = yPos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    public static void refresh() {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        get().mouseButtonPressed[button] = action == GLFW_PRESS;
        if (action == GLFW_RELEASE)
            get().isDragging = false;
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void endFrame() {
        get().scrollY = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX() {
        return (float) get().xPos;
    }

    public static float getY() {
        return (float) get().yPos;
    }

    public static float getDX() {
        return (float) (get().lastX - get().xPos);
    }

    public static float getDY() {
        return (float) (get().lastY - get().yPos);
    }

    public static float getScrollX() {
        return (float) (get().scrollX);
    }

    public static float getScrollY() {
        return (float) (get().scrollY);
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    public static boolean isButtonDown(int button) {
        return get().mouseButtonPressed[button];
    }

}
