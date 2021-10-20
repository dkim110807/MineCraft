package org.minecraft.models;

/**
 * Just a temporary class for rendering a simple quad
 * <p>Do not use this for loading blocks</p>
 *
 * @author 4347
 */
public class RawModel {

    private final int vaoID;
    private final int vertexCount;

    public RawModel(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

}
