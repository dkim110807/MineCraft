package org.minecraft.util.texture;

import org.minecraft.block.BlockTexture;
import org.minecraft.texture.Texture;
import org.minecraft.util.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TextureUtils {

    public static final int WIDTH = 25;
    public static final int HEIGHT = 25;

    //String => Path of the file
    //Integer => The position
    //Integer => The position
    private static final List<Pair<String, Pair<Integer, Integer>>> textures = new ArrayList<>();

    //Private constructor
    private TextureUtils() {
    }

    public static void createTextureAtlas() {
        BufferedImage atlas = new BufferedImage(WIDTH * 16, HEIGHT * 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = atlas.createGraphics();

        g2d.setBackground(Color.WHITE);

        for (Pair<String, Pair<Integer, Integer>> texture : textures) {
            String path = texture.first;
            int x = texture.second.first * 16;
            int y = texture.second.second * 16;

            try {
                BufferedImage image = ImageIO.read(new File("assets/textures/block/" + path));
                g2d.drawImage(image, x, y, null);
            } catch (IOException e) {
                System.err.println("Missing texture " + path + "!");
            }
        }

        try {
            ImageIO.write(atlas, "png", new File("assets/textures/test.png"));
        } catch (IOException e) {
            System.err.println("Something went wrong while writing atlas texture!");
            e.printStackTrace();
        }

        System.out.println("Successfully created atlas texture!");
    }

    static {
        textures.add(new Pair<>("grass_block_side.png", new Pair<>(0, 0)));
        textures.add(new Pair<>("grass_block_top.png", new Pair<>(1, 0)));
        textures.add(new Pair<>("dirt.png", new Pair<>(2, 0)));
        textures.add(new Pair<>("stone.png", new Pair<>(3, 0)));
    }

}
