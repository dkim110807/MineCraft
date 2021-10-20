package org.minecraft;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;
import org.minecraft.entity.Camera;
import org.minecraft.gui.ImGUI;
import org.minecraft.listener.Keyboard;
import org.minecraft.listener.Mouse;
import org.minecraft.loader.Loader;
import org.minecraft.models.RawModel;
import org.minecraft.models.TexturedModel;
import org.minecraft.render.QuadRender;
import org.minecraft.shader.Shader;
import org.minecraft.texture.Texture;
import org.minecraft.util.matrix.MatrixUtils;
import org.minecraft.util.vector.Vector3f;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
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

    private ImGUI imgui;

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
        glfwSetWindowSizeCallback(glfwWindow,(w,newWidth,newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        //Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        //Enable v-sync
        glfwSwapInterval(0);

        //Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glfwSetInputMode(glfwWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        Callback debugProc = GLUtil.setupDebugMessageCallback();

        imgui = new ImGUI(glfwWindow);
        imgui.init();
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

        TexturedModel model = new TexturedModel(Loader.loadToVao(vertices, color, textures, indices),new Texture("assets/textures/dirt.png"));
        Camera camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));

        long time = System.currentTimeMillis();
        int frames = 0;

        Shader shader = QuadRender.shader;
        shader.enable();
        shader.setUniformMat4f("pr_matrix", MatrixUtils.createProjectionMatrix());
        shader.setUniform1i("tex", 0);
        shader.disable();

        glClearColor(0.1f, 0.8f, 1.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);

        while (!glfwWindowShouldClose(glfwWindow)) {
            //Poll Events
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            camera.move();

            QuadRender.render(camera, model);

            //FPS Counter
            frames++;

            if (time + 1000 <= System.currentTimeMillis()) {
                System.out.println(frames + " fps");
                delta = 60f / frames;
                frames = 0;
                time = System.currentTimeMillis();
            }

            //imgui.update(1f / frames);
            glfwSwapBuffers(glfwWindow);
            Mouse.refresh();
        }

        Loader.cleanUp();

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
