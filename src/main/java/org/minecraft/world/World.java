package org.minecraft.world;

import java.io.Serial;
import java.io.Serializable;

public final class World implements Serializable {

    /**
     * The serial version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The render distance of the world
     */
    private static final int RENDER_DISTANCE = 6;

    public static final int CHUNK_SIZE = 16;

    /**
     * The size of the world to render
     */
    public static final int WORLD_SIZE = RENDER_DISTANCE * CHUNK_SIZE;

    /**
     * The seed of the world
     */
    private final int seed;

    /**
     * The noise generator of the world
     */
    private final PerlinNoise noise;

    /**
     * Generate world with default seed
     *
     * @author 4347
     */
    public World() {
        this.seed = 4347;
        this.noise = new PerlinNoise();
    }

    /**
     * Generate world
     *
     * @param seed The seed of the world
     * @author 4347
     */
    public World(int seed) {
        this.seed = seed;
        this.noise = new PerlinNoise(seed);
    }

    /**
     * Returns the height at the x, z
     *
     * @param x The x position of the world
     * @param z The z position of the world
     * @return The height at the world
     * @author 4347
     */
    public int getHeightAt(int x, int z) {
        return (int) noise.generateHeight(x, z);
    }

}
