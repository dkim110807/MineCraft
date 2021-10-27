package org.minecraft.gui;

import org.minecraft.shader.Shader;

public final class GuiShader extends Shader {

    private static final String VERTEX = "src/org/minecraft/gui/gui.vert";
    private static final String FRAGMENT = "src/org/minecraft/gui/gui.frag";

    public GuiShader() {
        super(VERTEX, FRAGMENT);
    }

}
