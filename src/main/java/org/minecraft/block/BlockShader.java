package org.minecraft.block;

import org.minecraft.shader.Shader;

/**
 * The shader for block
 */
public final class BlockShader extends Shader {

    /**
     * The path of the vertex shader
     */
    private static final String VERTEX = "assets/shaders/block.vert";
    /**
     * THe path of the fragment shader
     */
    private static final String FRAGMENT = "assets/shaders/block.frag";

    public BlockShader() {
        super(VERTEX, FRAGMENT);
    }

}
