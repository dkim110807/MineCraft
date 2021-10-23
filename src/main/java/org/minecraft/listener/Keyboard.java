package org.minecraft.listener;

import static org.lwjgl.glfw.GLFW.*;

public final class Keyboard {

    private static Keyboard instance;

    private boolean[] keyPressed = new boolean[GLFW_KEY_LAST];

    private Keyboard() {

    }

    public static Keyboard get() {
        if (instance == null)
            instance = new Keyboard();

        return instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_UNKNOWN)
            return;

        if (action == GLFW_PRESS)
            get().keyPressed[key] = true;
        else if (action == GLFW_RELEASE)
            get().keyPressed[key] = false;
    }

    public static boolean isKeyPressed(int key) {
        return get().keyPressed[key];
    }

}
