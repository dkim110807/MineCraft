package org.minecraft.world.chunk;

import org.jetbrains.annotations.NotNull;
import org.minecraft.block.Block;
import org.minecraft.util.vector.Vector3f;
import org.minecraft.world.util.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Chunk {

    private Map<Location, Block> locToBlock = new HashMap<>();

    /**
     * The blocks in the chunk
     */
    private List<Block> blocks;
    private Vector3f origin;

    public Chunk(@NotNull List<Block> blocks, @NotNull Vector3f origin) {
        this.blocks = blocks;
        this.origin = origin;

        blocks.forEach(it -> locToBlock.put(new Location(it.getPosition()), it));
    }

    public boolean isBlockIn(Location loc) {
        return locToBlock.containsKey(loc);
    }

    public boolean isBlockIn(Vector3f loc) {
        return isBlockIn(new Location(loc));
    }

    public boolean isBlockIn(float x,float y,float z) {
        return isBlockIn(new Vector3f(x, y, z));
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public Vector3f getOrigin() {
        return origin;
    }
}
