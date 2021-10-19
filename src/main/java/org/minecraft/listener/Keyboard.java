package org.minecraft.listener;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public final class Keyboard {

    private static Keyboard instance;

    private boolean[] keyPressed = new boolean[350];

    private Keyboard() {

    }

    public static Keyboard get() {
        if (instance == null)
            instance = new Keyboard();

        return instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        get().keyPressed[key] = (action == GLFW_PRESS);
    }

    public static boolean isKeyPressed(int key) {
        return get().keyPressed[key];
    }

}
