package org.minecraft.gui.text.render;

import org.minecraft.gui.text.mesh.FontType;
import org.minecraft.gui.text.mesh.GUIText;
import org.minecraft.gui.text.mesh.TextMeshData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TextMaster {

    private static final Map<FontType, List<GUIText>> texts = new HashMap<>();
    private static FontRender render;

    public static void init() {
        render = new FontRender();
    }

    public static void loadText(GUIText text) {
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);

        int vao = FontLoader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setMeshInfo(vao, data.getVertexCount());

        List<GUIText> textBatch = texts.get(font);
        if (textBatch == null)
            textBatch = new ArrayList<>();

        textBatch.add(text);
        texts.put(font, textBatch);
    }

    public static void render() {
        render.render(texts);
    }

    public static void removeText(GUIText text) {
        List<GUIText> textBatch = texts.get(text.getFont());
        textBatch.remove(text);

        if (textBatch.isEmpty()) {
            texts.remove(text.getFont());
        }
    }

    public static void cleanUp() {
        render.cleanUp();
        FontLoader.cleanUp();
    }

}
