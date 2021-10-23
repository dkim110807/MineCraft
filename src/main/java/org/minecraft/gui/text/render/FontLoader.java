package org.minecraft.gui.text.render;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.*;
import org.minecraft.texture.Texture;
import org.minecraft.util.buffer.BufferUtils;

import java.util.ArrayList;
import java.util.List;

public final class FontLoader {

    private static final List<Integer> vaos = new ArrayList<>();
    private static final List<Integer> vbos = new ArrayList<>();

    private FontLoader() {
    }

    public static int loadToVAO(float[] positions, float[] tcs) {
        int vao = createVAO();

        storeDataInAttributeList(positions, 0, 2);
        storeDataInAttributeList(tcs, 1, 2);

        GL30.glBindVertexArray(0);

        return vao;
    }


    /**
     * Creates the vao and adds to vaos
     * <li>The vaos needs to be cleared at the end of program
     *
     * @return The vao id
     * @author 4347
     */
    private static int createVAO() {
        int vaoID = GL30.glGenVertexArrays();

        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);

        return vaoID;
    }


    /**
     * Load the texture to the vbos
     *
     * @param path The path of the file
     * @return The texture id of the loaded texture
     * @author 4347
     */
    public static Texture loadTexture(@NotNull String path) {

        Texture texture = new Texture(path);

        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS,0);

        return texture;
    }

    /**
     * Store data in attribute list
     * <li>Adds to {@link #vbos vbo list} and clears at the end of program
     *
     * @param data       The data to store
     * @param attribute  The attribute number to store
     * @param dimensions The dimensions of the data
     * @author 4347
     */
    @SuppressWarnings("all")
    private static void storeDataInAttributeList(float[] data, int attribute, int dimensions) {
        int vboID = GL15.glGenBuffers();

        vbos.add(vboID);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(data), GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attribute, dimensions, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Cleans all the vaos and vbos at the end of program
     *
     * @author 4347
     */
    public static void cleanUp() {
        vaos.forEach(GL30::glDeleteVertexArrays);
        vbos.forEach(GL15::glDeleteBuffers);
    }

}
