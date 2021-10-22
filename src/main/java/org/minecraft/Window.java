package org.minecraft;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;
import org.minecraft.audio.Sound;
import org.minecraft.block.Block;
import org.minecraft.block.blocks.Dirt;
import org.minecraft.entity.Camera;
import org.minecraft.listener.Keyboard;
import org.minecraft.listener.Mouse;
import org.minecraft.loader.Loader;
import org.minecraft.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public final class Window {

    private static float delta;

    /**
     * The width of the screen
     */
    private int width;
    /**
     * The height of the screen
     */
    private int height;
    /**
     * The title of the screen
     */
    private String title;

    /**
     * The window
     */
    private long glfwWindow;

    private long audioDevice;
    private long audioContext;

    /**
     * The game window
     */
    private static Window window = null;

    private Window() {
        this.width = 1280;
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

        //Destroy the audio context
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);

        //Terminate GLFW and the free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
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
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

        //Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the glfw window.");
        }

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        assert vidmode != null;
        glfwSetWindowPos(
                glfwWindow,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        glfwSetCursorPosCallback(glfwWindow, Mouse::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, Mouse::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, Mouse::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, Keyboard::keyCallback);
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        //Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        //Enable v-sync
        glfwSwapInterval(0);

        //Make the window visible
        glfwShowWindow(glfwWindow);

        //Initial the audio device
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        audioContext = alcCreateContext(audioDevice, attributes);
        alcMakeContextCurrent(audioContext);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        if (!alCapabilities.OpenAL10) {
            throw new IllegalStateException("Could not load OpenAL");
        }

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glfwSetInputMode(glfwWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        Callback debugProc = GLUtil.setupDebugMessageCallback();
    }

    //The game loop
    public void loop() {

        float[] vertices = new float[]{
                //Bottom Left
                0.5f, -0.5f, -1.0f,

                //Top Left
                -0.5f, 0.5f, -1.0f,

                //Top Right
                0.5f, 0.5f, -1.0f,

                //Bottom Right
                -0.5f, -0.5f, -1.0f
        };

        float[] color = new float[]{
                //Bottom Left
                1.0f, 0.0f, 0.0f, 1.0f,

                //Top Left
                0.0f, 1.0f, 0.0f, 1.0f,

                //Top Right
                0.0f, 0.0f, 1.0f, 1.0f,

                //Bottom Right
                1.0f, 0.0f, 1.0f, 1.0f
        };

        float[] textures = new float[]{
                //Bottom Left
                1.0f, 1.0f,

                //Top Left
                0.0f, 0.0f,

                //Top Right
                1.0f, 0.0f,

                //Bottom Right
                0.0f, 1.0f
        };

        //IMPORTANT : Must be in counter - clock wise order
        int[] indices = new int[]{
                2, 1, 0,          //Top Right Triangle
                0, 1, 3,          //Bottom Left Triangle
        };

        Camera camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));

        long time = System.currentTimeMillis();
        int frames = 0;

        Block.prepare();

        Sound sound = new Sound("assets/sounds/Background.ogg",true);

        sound.play();

        List<Block> blocks = new ArrayList<>();

        for (int x = 0; x <= 10; x++)
            for (int z = 0; z <= 10; z++)
                blocks.add(new Dirt(x, 0, z));

        glClearColor(0.1f, 0.8f, 1.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);

        while (!glfwWindowShouldClose(glfwWindow)) {
            //Poll Events
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            camera.move();
            Mouse.refresh();

            blocks.forEach(Block::add);
            Block.render(camera);

            //FPS Counter
            frames++;

            if (time + 1000 <= System.currentTimeMillis()) {
                System.out.println(frames + " fps");
                delta = 1f / frames;
                frames = 0;
                time = System.currentTimeMillis();
            }

            glfwSwapBuffers(glfwWindow);
        }

        Loader.cleanUp();
        Block.cleanUp();
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    public static int getWidth() {
        return get().width;
    }

    public static int getHeight() {
        return get().height;
    }

    public static void setWidth(int width) {
        get().width = width;
    }

    public static void setHeight(int height) {
        get().height = height;
    }

}
