package org.minecraft.util.vector;

import java.nio.FloatBuffer;

public interface ReadableVector {

    /**
     * Returns the length of the vector
     *
     * @return The length of the vector
     * @author 4347
     */
    float length();

    /**
     * Returns the squared length of the vector
     *
     * @return The squared length of the vector
     * @author 4347
     */
    float lengthSquared();

    /**
     * Store this vector to float buffer
     *
     * @param buffer The buffer to store
     * @return The vector
     * @author 4347
     */
    Vector store(FloatBuffer buffer);

}