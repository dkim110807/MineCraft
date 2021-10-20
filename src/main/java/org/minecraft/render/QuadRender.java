package org.minecraft.render;

import org.minecraft.entity.Camera;
import org.minecraft.models.TexturedModel;
import org.minecraft.shader.Shader;
import org.minecraft.util.matrix.MatrixUtils;
import org.minecraft.util.vector.Vector3f;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public final class QuadRender {

    public static final Shader shader = new Shader("assets/shaders/default.vert", "assets/shaders/default.frag");

    public static void render(Camera camera, TexturedModel model) {
        //Bind shader program
        shader.enable();

        shader.setUniformMat4f("vw_matrix", MatrixUtils.createViewMatrix(camera));
        shader.setUniformMat4f("tr_matrix", MatrixUtils.createTransformationMatrix(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)));

        //Bind the vertex array object
        glBindVertexArray(model.getModel().getVaoID());

        //Bind the vertex buffer object
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glActiveTexture(GL_TEXTURE0);
        model.getTexture().bind();

        glDrawElements(GL_TRIANGLES, model.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);

        //Unbind the vertex buffer object
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

        //Unbind the vertex array object
        glBindVertexArray(0);

        //Disable shader
        shader.disable();
    }

}
