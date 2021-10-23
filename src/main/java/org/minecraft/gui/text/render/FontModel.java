package org.minecraft.graphics.font.render;

public final class FontModel {

    /**
     * The vao id of the model
     */
    private final int vaoID;

    /**
     * The total vertex count of the model
     */
    private final int vertexCount;

    /**
     * Creates a font model with the vao id and the vertex count
     *
     * @param vaoID The vao id of the model
     * @param vertexCount The total vertex count of the model
     * @author 4347
     */
    public FontModel(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    /**
     * Returns the vao id of the model
     *
     * @return The vao id of the model
     * @author 4347
     */
    public int getVaoID() {
        return vaoID;
    }

    /**
     * Returns the vertex count of the model
     *
     * @return The vertex count of the model
     * @author 4347
     */
    public int getVertexCount() {
        return vertexCount;
    }
}
