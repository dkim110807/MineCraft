package org.minecraft.block;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.*;
import org.minecraft.annotaions.Shader;
import org.minecraft.block.blocks.Dirt;
import org.minecraft.entity.Camera;
import org.minecraft.object.GameObject;
import org.minecraft.texture.Texture;
import org.minecraft.util.buffer.BufferUtils;
import org.minecraft.util.matrix.MatrixUtils;
import org.minecraft.util.vector.Vector3f;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * <b>Class</b> for Block
 *
 * @author 4347
 */
public class Block extends GameObject implements BlockI, Serializable {

    /**
     * The serial version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The shader for block
     */
    @Shader(name = "Block")
    protected static final BlockShader shader = new BlockShader();

    /**
     * The vertices for default 1, 1, 1 block
     */
    protected static final float[] VERTICES = new float[]{
            //Positive X
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            //Negative X
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,

            //Positive Y
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,

            //Negative Y
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,

            //Positive Z
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            //Negative Z
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f
    };

    /**
     * The default indices
     */
    protected static int[] INDICES = new int[]{
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

    /**
     * The default texture coordinates
     */
    protected static final float[] TCS = new float[]{
            //Positive X
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,

            //Negative X
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,

            //Positive Y
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,

            //Negative Y
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,

            //Positive Z
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,

            //Negative Z
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    /**
     * The default normal vector
     */
    protected static final float[] NORMALS = new float[]{
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

    /**
     * The vaos to clear at the end of program
     *
     * @see #cleanUp()
     */
    private static final List<Integer> vaos = new ArrayList<>();

    /**
     * The vbos to clear at the end of program
     *
     * @see #cleanUp()
     */
    private static final List<Integer> vbos = new ArrayList<>();

    /**
     * The model of this block
     */
    @NotNull
    private final BlockModel model;

    /**
     * The position of this block
     */
    @NotNull
    private final Vector3f position;

    /**
     * The rotation of this block
     */
    @NotNull
    private final Vector3f rotation;

    /**
     * Creates block with texture with position
     * <li>For the vertices it will use default vertices which is {@link #VERTICES}
     * <li>For the indices it will use the default indices which is {@link #INDICES}
     * <li>For the texture coordinates it will use the default tcs which is {@link #TCS}
     *
     * @param texture  The texture of this block
     * @param position The position of the block
     * @author 4347
     */
    public Block(@NotNull BlockTexture texture, @NotNull Vector3f position) {
        this.model = loadModel(VERTICES, INDICES, TCS, texture);
        this.position = position;
        this.rotation = new Vector3f(0, 0, 0);
    }

    /**
     * Creates block with texture with position and rotation
     * <li>For the vertices it will use default vertices which is {@link #VERTICES}
     * <li>For the indices it will use the default indices which is {@link #INDICES}
     * <li>For the texture coordinates it will use the default tcs which is {@link #TCS}
     *
     * @param texture  The texture of this block
     * @param position The position of the block
     * @param rotation The rotation of the block
     * @author 4347
     */
    public Block(@NotNull BlockTexture texture, @NotNull Vector3f position, @NotNull Vector3f rotation) {
        this.model = loadModel(VERTICES, INDICES, TCS, texture);
        this.position = position;
        this.rotation = rotation;
    }

    /**
     * Creates block with model with position
     *
     * @param model    The model of this block
     * @param position The position of the block
     * @author 4347
     */
    public Block(@NotNull BlockModel model, @NotNull Vector3f position) {
        this.model = model;
        this.position = position;
        this.rotation = new Vector3f(0, 0, 0);
    }

    /**
     * Creates block with model with position and rotation
     *
     * @param model    The model of this block
     * @param position The position of this block
     * @param rotation The rotation of this block
     * @author 4347
     */
    public Block(@NotNull BlockModel model, @NotNull Vector3f position, @NotNull Vector3f rotation) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
    }

    /**
     * Returns the name of the block
     *
     * @return The name of the block
     * @author 4347
     */
    @NotNull
    @Override
    public String getName() {
        return "empty";
    }

    /**
     * Returns the width of the block
     * <li>Normally it will return 1f
     *
     * @return The width of the block
     * @author 4347
     */
    @Override
    public float getWidth() {
        return 1f;
    }

    /**
     * Returns the height of the block
     * <li>Normally it will return 1f
     *
     * @return The height of the block
     * @author 4347
     */
    @Override
    public float getHeight() {
        return 1f;
    }

    /**
     * Returns the scale of the block
     * <li>Normally it will return <i>Vector3f(1, 1, 1)</i>
     *
     * @return The scale of the block
     * @author 4347
     */
    @NotNull
    @Override
    public Vector3f getScale() {
        return new Vector3f(1, 1, 1);
    }

    /**
     * Add this block to the render queue
     * <li>It will return <b>true</b> if success <b>false</b> if fail
     *
     * @return <b>true</b> if success <b>false</b> if other
     * @author 4347
     */
    @Override
    public boolean add() {
        addBlock(this);

        return true;
    }

    /**
     * Returns the type of this block
     *
     * @return The type of this block
     * @author 4347
     */
    @NotNull
    @Override
    public Type getType() {
        return Type.AIR;
    }

    /**
     * Returns the model of this block
     *
     * @return The model of this block
     * @author 4347
     */
    @NotNull
    @Override
    public final BlockModel getModel() {
        return model;
    }

    /**
     * Returns the vertices of this block
     *
     * @return The vertices of this block
     * @author 4347
     */
    @Override
    public float[] getVertices() {
        return VERTICES;
    }

    /**
     * Returns the texture coordinates of this block
     *
     * @return The texture coordinates of this block
     * @author 4347
     */
    @Override
    public float[] getTCS() {
        return TCS;
    }

    /**
     * Returns the position of this block
     *
     * @return The position of this block
     * @author 4347
     */
    @NotNull
    public final Vector3f getPosition() {
        return position;
    }

    /**
     * Returns the rotation of this block
     *
     * @return The rotation of this block
     * @author 4347
     */
    @NotNull
    public final Vector3f getRotation() {
        return rotation;
    }

    /**
     * Load the vertices, indices, tcs , texture to vbos
     *
     * @param vertices The vertices to load
     * @param indices  The indices
     * @param tcs      The texture coordinates
     * @param texture  The texture
     * @return The model with the vertices,indices,tcs,texture
     * @author 4347
     * @see BlockModel
     * @see BlockTexture
     */
    @NotNull
    protected static BlockModel loadModel(float[] vertices,
                                          int[] indices,
                                          float[] tcs,
                                          @NotNull BlockTexture texture) {

        int vao = createVAO();

        storeDataInAttributeList(vertices, 0, 3);
        storeDataInAttributeList(tcs, 1, 2);
        bindIndicesBuffer(indices);

        GL30.glBindVertexArray(0);

        return new BlockModel(vao, indices.length, texture);
    }

    @NotNull
    @SuppressWarnings("all")
    protected static BlockModel loadModel(@NotNull float[] vertices,
                                          @NotNull float[] tcs,
                                          @NotNull float[] normals,
                                          @NotNull int[] indices,
                                          @NotNull BlockTexture texture) {
        int vao = createVAO();

        storeDataInAttributeList(vertices, 0, 3);
        storeDataInAttributeList(tcs, 1, 2);
        storeDataInAttributeList(normals, 2, 3);
        bindIndicesBuffer(indices);

        GL30.glBindVertexArray(0);

        return new BlockModel(vao, indices.length, texture);
    }

    /**
     * Load the texture to the vbos
     * <li>The path is only the path in the assets folder
     *
     * @param path The path of the file
     * @return The texture id of the loaded texture
     * @author 4347
     */
    protected static Texture loadTexture(@NotNull String path) {
        return new Texture(path);
    }

    /**
     * Creates the vao and adds to vaos
     * <li>The vaos needs to be cleared at the end of program
     *
     * @return The vao id
     * @author 4347
     */
    private static int createVAO() {
        int vaoID = GL30.glGenVertexArrays();

        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);

        return vaoID;
    }

    /**
     * Store data in attribute list(VBO)
     * <li>Adds to {@link #vbos vbo list} and clears at the end of program
     *
     * @param data       The data to store
     * @param attribute  The attribute number to store
     * @param dimensions The dimensions of the data
     * @author 4347
     */
    private static void storeDataInAttributeList(float[] data, int attribute, int dimensions) {
        int vboID = GL15.glGenBuffers();

        vbos.add(vboID);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(data), GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attribute, dimensions, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }


    /**
     * Bind indices(EBO)
     * <li>Adds to {@link #vbos vbo list} and clears at the end of program
     *
     * @param indices The indices to bind
     * @author 4347
     */
    private static void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();

        vbos.add(vboID);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices), GL15.GL_STATIC_DRAW);
    }


    public static void cleanUp() {
        vaos.forEach(GL30::glDeleteVertexArrays);
        vbos.forEach(GL15::glDeleteBuffers);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return model.equals(block.model) && position.equals(block.position) && rotation.equals(block.rotation);
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     *     method, then calling the {@code hashCode} method on each of
     *     the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link Object#equals(Object)}
     *     method, then calling the {@code hashCode} method on each of the
     *     two objects must produce distinct integer results.  However, the
     *     programmer should be aware that producing distinct integer results
     *     for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * @return a hash code value for this object.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(model, position, rotation);
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * Block{model= , position= , rotation=}
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Block{" +
                "model=" + model +
                ", position=" + position +
                ", rotation=" + rotation +
                '}';
    }

    /**
     * Prepare the shader
     *
     * @author 4347
     */
    public static void prepare() {
        shader.enable();
        shader.setUniform1i("tex", 0);
        shader.setUniformMat4f("pr_matrix", MatrixUtils.createProjectionMatrix());
        shader.disable();

        new Dirt(0, 0, 0);
    }

    private static final Map<BlockModel,List<Block>> blocks = new HashMap<>();

    public static void render(Camera camera) {
        shader.enable();

        shader.setUniform3f("skyColour", 0.5f, 0.5f, 0.5f);
        shader.setUniformMat4f("vw_matrix", MatrixUtils.createViewMatrix(camera));

        for (BlockModel model : blocks.keySet()) {
            GL30.glBindVertexArray(model.getVaoId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);

            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            model.getTexture().getTexture().bind();

            List<Block> batch = blocks.get(model);

            for (Block block : batch) {
                shader.setUniformMat4f("tr_matrix", MatrixUtils.createTransformationMatrix(block.getPosition(), block.getRotation(), block.getScale()));

                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }

            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        }

        blocks.clear();

        shader.disable();
    }

    protected static void addBlock(@NotNull Block block) {
        List<Block> batch = blocks.get(block.getModel());

        if (batch == null) {
            batch = new ArrayList<>();
        }

        batch.add(block);

        blocks.put(block.getModel(), batch);
    }

    /**
     * <b>Enum</b> for {@link Block block} types
     *
     * @author 4347
     * @see Block
     */
    public enum Type {

        /**
         * The {@code enum} for block type <b><i>AIR</i></b>
         */
        AIR("minecraft:air"),

        /**
         * The {@code enum} for block type <b><i>DIRT</i></b>
         */
        DIRT("minecraft:dirt");

        private static final Map<String, Type> keys = new HashMap<>();

        /**
         * The name of the type
         */
        public final String name;

        /**
         * Constructor for <b>Enum</b> {@link Type Type}
         *
         * @param name The name of the type
         * @author 4347
         */
        Type(@NotNull String name) {
            this.name = name;
        }

        /**
         * Returns a string representation of the object. In general, the
         * {@code toString} method returns a string that
         * "textually represents" this object. The result should
         * be a concise but informative representation that is easy for a
         * person to read.
         * It is recommended that all subclasses override this method.
         * <p>
         * The {@code toString} method for class {@code Object}
         * returns a string consisting of the name of the class of which the
         * object is an instance, the at-sign character `{@code @}', and
         * the unsigned hexadecimal representation of the hash code of the
         * object. In other words, this method returns a string equal to the
         * value of:
         * <blockquote>
         * <pre>
         * Type{name=+Type.name+}
         * </pre></blockquote>
         *
         * @return a string representation of the object.
         */
        @Override
        public String toString() {
            return "Type{name"
                    + name
                    + "}";
        }

        static {
            for (Type type : values()) {
                keys.put(type.name, type);
            }
        }

        /**
         * Returns <i>{@link Type type}</i> by the name
         *
         * @param name The name of the type. If it doesn't start with <b>"minecraft:"</b> it will be automatically added
         * @return The <i>{@link Type type}</i> by the name
         * @author 4347
         */
        public static Type getByName(@NotNull String name) {

            name = name.toLowerCase();

            if (!name.startsWith("minecraft:"))
                name = "minecraft:" + name;

            return keys.get(name);
        }

    }

}
