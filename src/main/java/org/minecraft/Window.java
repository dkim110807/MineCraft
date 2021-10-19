package org.minecraft;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.minecraft.listener.Keyboard;
import org.minecraft.listener.Mouse;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public final class Window {

    private int width, height;
    private String title;
    private long glfwWindow;

    private static Window window = null;

    private Window() {
        this.width = 1020;
        this.height = 720;
        this.title = "MineCraft";
    }

    public static Window get() {
        if (Window.window == null)
            Window.window = new Window();

        return Window.window;
    }

    public void run() {
        System.out.println("LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        //Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        //Set up error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW.");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        //Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the glfw window.");
        }

        glfwSetCursorPosCallback(glfwWindow, Mouse::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, Mouse::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, Mouse::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, Keyboard::keyCallback);

        //Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        //Enable v-sync
        glfwSwapInterval(1);

        //Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    //The game loop
    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            //Poll Events
            glfwPollEvents();

            glClearColor(0.4f, 1f, 1f, 1f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }
    }

}
