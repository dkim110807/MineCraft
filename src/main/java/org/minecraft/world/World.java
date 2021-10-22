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
    private static final int RENDER_DISTANCE = 12;

    /**
     * The size of the world to render
     */
    private static final int WORLD_SIZE = RENDER_DISTANCE * 16;
}
