package org.minecraft.gui.text.mesh;

import java.io.File;

/**
 * Represents a font. It holds the font's texture atlas as well as having the
 * ability to create the quad vertices for any text using this font.
 * <li>Edited by 4347
 *
 * @author Karl
 */
public final class FontType {

    /**
     * The texture id of the font atlas texture
     */
    private final int texture;

    private final TextMeshCreator loader;

    /**
     * Creates a new font and loads up the data about each character from the
     * font file.
     *
     * @param texture  the ID of the font atlas texture.
     * @param fontFile the font file containing information about each character in
     *                 the texture atlas.
     * @author 4347
     */
    public FontType(int texture, File fontFile) {
        this.texture = texture;
        this.loader = new TextMeshCreator(fontFile);
    }

    /**
     * Returns the id of the texture atlas
     *
     * @return The font texture atlas.
     * @author 4347
     */
    public int getTextureAtlas() {
        return texture;
    }

    /**
     * Takes in an unloaded text and calculate all of the vertices for the quads
     * on which this text will be rendered. The vertex positions and texture
     * coords and calculated based on the information from the font file.
     *
     * @param text the unloaded text.
     * @return Information about the vertices of all the quads.
     * @author 4347
     */
    public TextMeshData loadText(GUIText text) {
        return loader.createTextMesh(text);
    }

}
