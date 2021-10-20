package org.minecraft.models;

public class TexturedModel {

    private RawModel model;
    private ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture) {
        this.model = model;
        this.texture = texture;
    }

    public final RawModel getModel() {
        return model;
    }

    public final ModelTexture getTexture() {
        return texture;
    }

}
