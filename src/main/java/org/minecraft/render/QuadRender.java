package org.minecraft.render;

import org.minecraft.entity.Camera;
import org.minecraft.models.RawModel;
import org.minecraft.shader.Shader;
import org.minecraft.util.matrix.MatrixUtils;
import org.minecraft.util.vector.Vector3f;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public final class QuadRender {

    public static final Shader shader = new Shader("assets/shaders/default.vert", "assets/shaders/default.frag");

    public static void render(Camera camera, RawModel model) {
        //Bind shader program
        shader.enable();

        shader.setUniformMat4f("vw_matrix", MatrixUtils.createViewMatrix(camera));
        shader.setUniformMat4f("tr_matrix", MatrixUtils.createTransformationMatrix(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)));

        //Bind the vertex array object
        glBindVertexArray(model.getVaoID());

        //Bind the vertex buffer object
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);

        //Unbind the vertex buffer object
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        //Unbind the vertex array object
        glBindVertexArray(0);

        //Disable shader
        shader.disable();
    }

}
