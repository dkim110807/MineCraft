package org.minecraft.models;

import org.minecraft.texture.Texture;

public class TexturedModel {

    private RawModel model;
    private Texture texture;

    public TexturedModel(RawModel model, Texture texture) {
        this.model = model;
        this.texture = texture;
    }

    public final RawModel getModel() {
        return model;
    }

    public final Texture getTexture() {
        return texture;
    }

}
