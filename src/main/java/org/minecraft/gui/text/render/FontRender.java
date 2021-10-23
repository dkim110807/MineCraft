package org.minecraft.gui.text.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import org.minecraft.gui.text.mesh.FontType;
import org.minecraft.gui.text.mesh.GUIText;

import java.util.List;
import java.util.Map;

public final class FontRender {

    private final FontShader shader;

    public FontRender() {
        shader = new FontShader();
    }

    public void render(Map<FontType, List<GUIText>> texts) {
        prepare();

        for (FontType font : texts.keySet()) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());

            for (GUIText text : texts.get(font)) {
                shader.loadWidthEdge(0.5f, 0.1f);
                shader.loadBorder(0.7f, 0.1f);
                shader.loadOutlineColour(text.getOutlineColour());
                renderText(text);
            }
        }

        endRendering();

    }

    public void cleanUp() {
        shader.cleanUp();
    }

    private void prepare() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        shader.enable();
    }

    private void renderText(GUIText text) {
        GL30.glBindVertexArray(text.getMesh());

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        shader.loadColour(text.getColour());
        shader.loadTranslation(text.getPosition());

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);

        GL30.glBindVertexArray(0);
    }

    private void endRendering() {
        shader.disable();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

}
