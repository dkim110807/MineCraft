package org.minecraft.loader;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.minecraft.models.RawModel;
import org.minecraft.util.buffer.BufferUtils;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public final class Loader {

    private static final List<Integer> vaos = new ArrayList<>();
    private static final List<Integer> vbos = new ArrayList<>();

    public static RawModel loadToVao(float[] vertices, float[] color, int[] indices) {
        //Creates vao
        int vao = glGenVertexArrays();

        vaos.add(vao);

        //Bind the vertex array
        glBindVertexArray(vao);

        //Store the vertices array to vbo 0
        storeDataInVertexBufferObject(vertices, 0, 3);
        //Store the color array to vbo 1
        storeDataInVertexBufferObject(color, 1, 4);
        bindIndicesBuffer(indices);

        return new RawModel(vao, indices.length);
    }

    private static void storeDataInVertexBufferObject(float[] data, int index, int dimensions) {
        //Creates vbo
        int vbo = glGenBuffers();

        vbos.add(vbo);

        //Bind the buffer
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        //Putting the data to the vbo
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(data), GL_STATIC_DRAW);
        //Sets the index and the dimensions
        glVertexAttribPointer(index, dimensions, GL_FLOAT, false, dimensions * 4, 0);
        //Unbind the buffer
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private static void bindIndicesBuffer(int[] indices) {
        //Creates the vbo
        int vbo = glGenBuffers();

        vbos.add(vbo);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices), GL_STATIC_DRAW);
    }

    public static void cleanUp() {
        vaos.forEach(GL30::glDeleteVertexArrays);
        vbos.forEach(GL15::glDeleteBuffers);
    }

}
