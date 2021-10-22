package org.minecraft.block;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class BlockMesh implements Serializable {

    /**
     * The serial version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The vertices
     */
    private static final float[] VERTICES = new float[]{
            //Positive X
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,

            //Negative X
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            //Positive Y
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            //Negative Y
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,

            //Positive Z
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,

            //Negative Z
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f
    };

    private static final float[] NORMALS = new float[]{
            //Positive X
            0.5f, 0.0f, 0.0f,

            //Negative X
            -0.5f, 0.0f, 0.0f,

            //Positive Y
            0.0f, 0.5f, 0.0f,

            //Negative Y
            0.0f, -0.5f, 0.0f,

            //Positive Z
            0.0f, 0.0f, 0.5f,

            //Negative Z
            0.0f, 0.0f, -0.5f
    };

    private static final int[] INDICES = new int[]{
            //Positive X
            0, 1, 3,
            3, 1, 2,

            //Negative X
            4, 5, 7,
            7, 5, 6,

            //Positive Y
            8, 9, 11,
            11, 9, 10,

            //Negative Y
            12, 13, 15,
            15, 13, 14,

            //Positive Z
            16, 17, 19,
            19, 17, 18,

            //Negative Z
            20, 21, 23,
            23, 21, 22
    };

    private final List<Float> posList = new ArrayList<>();
    private final List<Float> tcsList = new ArrayList<>();
    private final List<Float> normalsList = new ArrayList<>();
    private final List<Integer> indicesList = new ArrayList<>();

}
