package org.minecraft.block.blocks;

import org.minecraft.block.Block;
import org.minecraft.block.BlockModel;
import org.minecraft.block.BlockTexture;
import org.minecraft.util.vector.Vector3f;

import java.io.Serial;
import java.io.Serializable;

/**
 * <b>Class</b> for {@code Grass} blocks
 *
 * @author 4347
 */
public final class Grass extends Block implements Serializable {

    /**
     * The serial version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The texture of this block({@link Grass GRASS}).
     *
     * @see BlockTexture
     */
    private static final BlockTexture TEXTURE = new BlockTexture("assets/textures/texture.png");

    /**
     * The type of this block.
     *
     * @see Type#GRASS
     */
    private static final Type TYPE = Type.GRASS;

    /**
     * The name of this block
     *
     * @see Type#name
     */
    private static final String NAME = TYPE.name;

    private static final float[] TCS = new float[]{
            //Positive X
            2 / 25f, 0 / 25f,
            2 / 25f, 1 / 25f,
            3 / 25f, 1 / 25f,
            3 / 25f, 0 / 25f,

            //Negative X
            2 / 25f, 0 / 25f,
            2 / 25f, 1 / 25f,
            3 / 25f, 1 / 25f,
            3 / 25f, 0 / 25f,

            //Positive Y
            2 / 25f, 0 / 25f,
            2 / 25f, 1 / 25f,
            3 / 25f, 1 / 25f,
            3 / 25f, 0 / 25f,

            //Negative Y
            2 / 25f, 0 / 25f,
            2 / 25f, 1 / 25f,
            3 / 25f, 1 / 25f,
            3 / 25f, 0 / 25f,

            //Positive Z
            2 / 25f, 0 / 25f,
            2 / 25f, 1 / 25f,
            3 / 25f, 1 / 25f,
            3 / 25f, 0 / 25f,

            //Negative Z
            2 / 25f, 0 / 25f,
            2 / 25f, 1 / 25f,
            3 / 25f, 1 / 25f,
            3 / 25f, 0 / 25f
    };

    /**
     * The model of this block.
     *
     * @see #loadModel(float[], float[], float[], int[], BlockTexture)
     */
    public static final BlockModel MODEL = loadModel(VERTICES, TCS, NORMALS, INDICES, TEXTURE);


    /**
     * Create a block with the position {@code x}, {@code y}, {@code z} with the default {@link #MODEL model}
     *
     * @param x The x position of the block
     * @param y The y position of the block
     * @param z The z position of the block
     * @author 4347
     */
    public Grass(float x, float y, float z) {
        super(MODEL, new Vector3f(x, y, z));
    }

}
