package org.minecraft.block;

import org.jetbrains.annotations.NotNull;
import org.minecraft.util.vector.Vector3f;

/**
 * The interface for {@link Block} class
 *
 * @author 4347
 * @see Block
 */
public interface BlockI {

    /**
     * Returns the name of the block
     *
     * @return The name of the block
     * @author 4347
     */
    @NotNull
    String getName();

    /**
     * Returns the width of the block
     *
     * @return The width of the block
     * @author 4347
     */
    float getWidth();

    /**
     * Returns the height of the block
     *
     * @return The height of the block
     * @author 4347
     */
    float getHeight();

    /**
     * Returns the scale of the block
     * <li>Normally it will return <i>Vector3f(1, 1, 1)</i>
     *
     * @return The scale of the block
     * @author 4347
     */
    @NotNull
    Vector3f getScale();

    /**
     * Add this block to the render queue
     * <li>It will return <b>true</b> if success <b>false</b> if fail
     *
     * @return <b>true</b> if success <b>false</b> if other
     * @author 4347
     */
    boolean add();

    /**
     * Returns the model of this block
     *
     * @return The model of this block
     * @author 4347
     */
    @NotNull
    BlockModel getModel();

    /**
     * Returns the type of this block
     *
     * @return The type of this block
     * @author 4347
     */
    @NotNull
    Block.Type getType();

    /**
     * Returns the vertices of this block
     *
     * @return The vertices of this block
     * @author 4347
     */
    float[] getVertices();

    /**
     * Returns the texture coordinates of this block
     *
     * @return The texture coordinates of this block
     * @author 4347
     */
    float[] getTCS();

}
