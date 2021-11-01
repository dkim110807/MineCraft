package org.minecraft.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.minecraft.util.matrix.MatrixUtils;

import java.util.List;

public final class GuiRender {

    /**
     * The model of the gui
     */
    private final GuiModel quad;

    /**
     * The gui shader
     *
     * @see GuiShader
     */
    private GuiShader shader;

    public GuiRender() {
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = GuiLoader.loadToVAO(positions);
        shader = new GuiShader();
    }

    public void render(List<GuiTexture> guis) {
        shader.enable();
        GL30.glBindVertexArray(quad.getVaoID());

        GL20.glEnableVertexAttribArray(0);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        //render
        for (GuiTexture gui : guis) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
            shader.setUniformMat4f("tr_matrix", MatrixUtils.createTransformationMatrix(gui.getPosition(), gui.getScale()));
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);

        GL20.glDisableVertexAttribArray(0);

        GL30.glBindVertexArray(0);
        shader.disable();
    }

    public void cleanUp() {
        shader.cleanUp();
    }

}
