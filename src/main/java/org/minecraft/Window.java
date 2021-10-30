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
import org.minecraft.block.BlockMesh;
import org.minecraft.block.blocks.Dirt;
import org.minecraft.entity.Camera;
import org.minecraft.listener.Keyboard;
import org.minecraft.listener.Mouse;
import org.minecraft.loader.Loader;
import org.minecraft.util.texture.TextureUtils;
import org.minecraft.util.vector.Vector3f;
import org.minecraft.world.World;
import org.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import static org.minecraft.world.World.CHUNK_SIZE;
import static org.minecraft.world.World.WORLD_SIZE;

public final class Window {

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

    private boolean close = false;

    private volatile List<Block> blocks = new ArrayList<>();

    /**
     * The game window
     */
    private static Window window = null;

    private static float delta = 1f;

    private static final List<BlockMesh> meshes = new ArrayList<>();

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

        TextureUtils.createTextureAtlas();

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
        glfwSwapInterval(1);

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

        Sound sound = new Sound("assets/sounds/calm3.ogg", true);
        sound.play();


        World world = new World();

        List<Vector3f> used = new ArrayList<>();

        new Thread(() -> {
            while (!close) {
                for (int x = (int) (camera.getPosition().x - WORLD_SIZE) / CHUNK_SIZE;
                     x <= (camera.getPosition().x + WORLD_SIZE) / CHUNK_SIZE; x++) {
                    for (int z = (int) (camera.getPosition().z - WORLD_SIZE) / CHUNK_SIZE;
                         z <= (camera.getPosition().z + WORLD_SIZE) / CHUNK_SIZE; z++) {
                        final Vector3f e = new Vector3f(x * CHUNK_SIZE, 0, z * CHUNK_SIZE);
                        if (!used.contains(e)) {
                            used.add(e);

                            List<Block> blocks = new ArrayList<>();

                            for (int i = 0; i < CHUNK_SIZE; i++)
                                for (int j = 0; j < CHUNK_SIZE; j++) {

                                    int height = world.getHeightAt(x * CHUNK_SIZE + i, z * CHUNK_SIZE + j);

                                    blocks.add(new Dirt(i, height, j));
                                    blocks.add(new Dirt(i, height - 1, j));
                                    blocks.add(new Dirt(i, height - 2, j));
                                }

                            meshes.add(new BlockMesh(new Chunk(blocks, e)));
                        }
                    }
                }
            }
        }, "Terrain Thread").start();

        glClearColor(54 / 256f, 199 / 256f, 242 / 256f, 1.0f);
        glEnable(GL_DEPTH_TEST);

        int index = 0;

        while (!glfwWindowShouldClose(glfwWindow)) {

            if (Keyboard.isKeyPressed(GLFW_KEY_ESCAPE))
                break;

            if (index < meshes.size()) {
                BlockMesh mesh = meshes.get(index);
                blocks.add(new Block(Loader.loadModel(mesh.positions, mesh.tcs, mesh.normals, mesh.indices), mesh.origin));
                index++;
            }

            //Poll Events
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            camera.move();
            Mouse.refresh();

            for (Block block : blocks) {
                int dx = (int) Math.abs(block.getPosition().x - camera.getPosition().x);
                int dz = (int) Math.abs(block.getPosition().z - camera.getPosition().z);

                if (dx <= WORLD_SIZE && dz <= WORLD_SIZE)
                    block.add();
            }

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
        close = true;
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
