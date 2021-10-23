package org.minecraft.block;

import org.minecraft.util.vector.Vector2f;
import org.minecraft.util.vector.Vector3f;
import org.minecraft.world.chunk.Chunk;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class BlockMesh implements Serializable {

    /**
     * The serial version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The vertices
     */
    private static final float[] VERTICES = new float[]{
            //Positive X
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,

            //Negative X
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            //Positive Y
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            //Negative Y
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,

            //Positive Z
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,

            //Negative Z
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f
    };

    private static final float[] NORMALS = new float[]{
            //Positive X
            0.5f, 0.0f, 0.0f,

            //Negative X
            -0.5f, 0.0f, 0.0f,

            //Positive Y
            0.0f, 0.5f, 0.0f,

            //Negative Y
            0.0f, -0.5f, 0.0f,

            //Positive Z
            0.0f, 0.0f, 0.5f,

            //Negative Z
            0.0f, 0.0f, -0.5f
    };

    private static final int[] INDICES = new int[]{
            //Positive X
            0, 1, 3,
            3, 1, 2,

            //Negative X
            4, 5, 7,
            7, 5, 6,

            //Positive Y
            8, 9, 11,
            11, 9, 10,

            //Negative Y
            12, 13, 15,
            15, 13, 14,

            //Positive Z
            16, 17, 19,
            19, 17, 18,

            //Negative Z
            20, 21, 23,
            23, 21, 22
    };

    public Vector3f origin;

    private List<Block> blocks;
    private Chunk chunk;

    public BlockMesh(Chunk chunk) {
        this.blocks = chunk.getBlocks();
        this.origin = chunk.getOrigin();
        this.chunk = chunk;

        calculate();
    }

    private List<Vertex> vertices = new ArrayList<>();
    private List<Integer> indicesList = new ArrayList<>();

    public float[] positions;
    public float[] tcs;
    public float[] normals;

    public int[] indices;

    private void calculate() {
        for (Block block : blocks) {

            float x = block.getPosition().x;
            float y = block.getPosition().y;
            float z = block.getPosition().z;

            //Positive X
            if (!chunk.isBlockIn(x + 1, y, z)) {

                int current = vertices.size();

                for (int i = 0; i < 4; i++) {
                    vertices.add(new Vertex(
                            new Vector3f(block.getVertices()[i * 3] + x,
                                    block.getVertices()[i * 3 + 1] + y,
                                    block.getVertices()[i * 3 + 2] + z),
                            new Vector2f(block.getTCS()[i * 2],
                                    block.getTCS()[i * 2 + 1]),
                            new Vector3f(0, 0, 0)
                    ));
                }

                indicesList.add(current);
                indicesList.add(current + 1);
                indicesList.add(current + 3);
                indicesList.add(current + 3);
                indicesList.add(current + 1);
                indicesList.add(current + 2);
            }

            //Negative X
            if (!chunk.isBlockIn(x - 1, y, z)) {

                int current = vertices.size();

                for (int i = 4; i < 8; i++) {
                    vertices.add(new Vertex(
                            new Vector3f(block.getVertices()[i * 3] + x,
                                    block.getVertices()[i * 3 + 1] + y,
                                    block.getVertices()[i * 3 + 2] + z),
                            new Vector2f(block.getTCS()[i * 2],
                                    block.getTCS()[i * 2 + 1]),
                            new Vector3f(0, 0, 0)
                    ));
                }

                indicesList.add(current);
                indicesList.add(current + 1);
                indicesList.add(current + 3);
                indicesList.add(current + 3);
                indicesList.add(current + 1);
                indicesList.add(current + 2);
            }

            //Positive Y
            if (!chunk.isBlockIn(x, y + 1, z)) {

                int current = vertices.size();

                for (int i = 8; i < 12; i++) {
                    vertices.add(new Vertex(
                            new Vector3f(block.getVertices()[i * 3] + x,
                                    block.getVertices()[i * 3 + 1] + y,
                                    block.getVertices()[i * 3 + 2] + z),
                            new Vector2f(block.getTCS()[i * 2],
                                    block.getTCS()[i * 2 + 1]),
                            new Vector3f(0, 0, 0)
                    ));
                }

                indicesList.add(current);
                indicesList.add(current + 1);
                indicesList.add(current + 3);
                indicesList.add(current + 3);
                indicesList.add(current + 1);
                indicesList.add(current + 2);
            }

            //Negative Y
            if (!chunk.isBlockIn(x, y - 1, z)) {

                int current = vertices.size();

                for (int i = 12; i < 16; i++) {
                    vertices.add(new Vertex(
                            new Vector3f(block.getVertices()[i * 3] + x,
                                    block.getVertices()[i * 3 + 1] + y,
                                    block.getVertices()[i * 3 + 2] + z),
                            new Vector2f(block.getTCS()[i * 2],
                                    block.getTCS()[i * 2 + 1]),
                            new Vector3f(0, 0, 0)
                    ));
                }

                indicesList.add(current);
                indicesList.add(current + 1);
                indicesList.add(current + 3);
                indicesList.add(current + 3);
                indicesList.add(current + 1);
                indicesList.add(current + 2);
            }

            //Positive Z
            if (!chunk.isBlockIn(x, y, z + 1)) {

                int current = vertices.size();

                for (int i = 16; i < 20; i++) {
                    vertices.add(new Vertex(
                            new Vector3f(block.getVertices()[i * 3] + x,
                                    block.getVertices()[i * 3 + 1] + y,
                                    block.getVertices()[i * 3 + 2] + z),
                            new Vector2f(block.getTCS()[i * 2],
                                    block.getTCS()[i * 2 + 1]),
                            new Vector3f(0, 0, 0)
                    ));
                }

                indicesList.add(current);
                indicesList.add(current + 1);
                indicesList.add(current + 3);
                indicesList.add(current + 3);
                indicesList.add(current + 1);
                indicesList.add(current + 2);
            }

            //Negative Z
            if (!chunk.isBlockIn(x, y, z - 1)) {

                int current = vertices.size();

                for (int i = 20; i < 24; i++) {
                    vertices.add(new Vertex(
                            new Vector3f(block.getVertices()[i * 3] + x,
                                    block.getVertices()[i * 3 + 1] + y,
                                    block.getVertices()[i * 3 + 2] + z),
                            new Vector2f(block.getTCS()[i * 2],
                                    block.getTCS()[i * 2 + 1]),
                            new Vector3f(0, 0, 0)
                    ));
                }

                indicesList.add(current);
                indicesList.add(current + 1);
                indicesList.add(current + 3);
                indicesList.add(current + 3);
                indicesList.add(current + 1);
                indicesList.add(current + 2);
            }
        }

        positions = new float[vertices.size() * 3];
        tcs = new float[vertices.size() * 2];
        normals = new float[vertices.size() * 3];

        indices = new int[indicesList.size()];

        for (int i = 0; i < vertices.size(); i++) {
            Vertex v = vertices.get(i);

            positions[i * 3] = v.position.x;
            positions[i * 3 + 1] = v.position.y;
            positions[i * 3 + 2] = v.position.z;

            tcs[i * 2] = v.texture.x;
            tcs[i * 2 + 1] = v.texture.y;

            normals[i * 3] = v.normal.x;
            normals[i * 3 + 1] = v.normal.y;
            normals[i * 3 + 2] = v.normal.z;
        }

        for (int i = 0; i < indicesList.size(); i++)
            indices[i] = indicesList.get(i);

        vertices.clear();
        indicesList.clear();
    }

}

class Vertex {

    public Vector3f position;
    public Vector2f texture;
    public Vector3f normal;

    public Vertex(Vector3f position, Vector2f texture, Vector3f normal) {
        this.position = position;
        this.texture = texture;
        this.normal = normal;
    }

}
