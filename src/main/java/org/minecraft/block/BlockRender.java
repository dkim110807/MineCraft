package org.minecraft.block;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.minecraft.entity.Camera;
import org.minecraft.shader.Shader;
import org.minecraft.util.matrix.MatrixUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BlockRender {

    /**
     * The shader
     */
    protected static final Shader shader = new Shader("assets/shaders/block.vert", "assets/shaders/block.frag");

    private static final Map<BlockModel, List<Block>> blocks = new HashMap<>();

    public static void render(Camera camera) {

        shader.enable();
        shader.setUniform3f("skyColour", 0.5f, 0.5f, 0.5f);
        shader.setUniformMat4f("vw_matrix", MatrixUtils.createViewMatrix(camera));

        for (BlockModel model : blocks.keySet()) {
            GL30.glBindVertexArray(model.getVaoId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);

            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            model.getTexture().getTexture().bind();

            List<Block> batch = blocks.get(model);

            for (Block block : batch) {
                shader.setUniformMat4f("tr_matrix", MatrixUtils.createTransformationMatrix(block.getPosition(), block.getRotation(), block.getScale()));

                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }

            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        }

        blocks.clear();

        shader.disable();
    }

    protected void addBlock(@NotNull Block block) {
        List<Block> batch = blocks.get(block.getModel());

        if (batch == null) {
            batch = new ArrayList<>();
        }

        batch.add(block);

        blocks.put(block.getModel(), batch);
    }

}
