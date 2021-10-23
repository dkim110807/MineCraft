package org.minecraft.gui.text.render;


import org.minecraft.shader.Shader;
import org.minecraft.util.vector.Vector2f;
import org.minecraft.util.vector.Vector3f;

public final class FontShader extends Shader {

    private static final String VERTEX = "src/org/minecraft/graphics/font/render/font.vert";
    private static final String FRAGMENT = "src/org/minecraft/graphics/font/render/font.frag";

    public FontShader() {
        super(VERTEX, FRAGMENT);
    }

    public void loadColour(Vector3f colour) {
        super.setUniform3f("colour", colour);
    }

    public void loadTranslation(Vector2f translation) {
        super.setUniform2f("translation", translation);
    }

    public void loadWidthEdge(float width, float edge) {
        super.setUniform1f("width", width);
        super.setUniform1f("edge", edge);
    }

    public void loadBorder(float borderWidth, float borderEdge) {
        super.setUniform1f("borderWidth", borderWidth);
        super.setUniform1f("borderEdge", borderEdge);
    }

    public void loadOutlineColour(Vector3f colour) {
        super.setUniform3f("outlineColour", colour);
    }

}
