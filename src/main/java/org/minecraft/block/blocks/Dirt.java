package org.minecraft.block.blocks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.minecraft.block.Block;
import org.minecraft.block.BlockModel;
import org.minecraft.block.BlockTexture;
import org.minecraft.util.texture.TextureUtils;
import org.minecraft.util.vector.Vector3f;

import java.io.Serial;
import java.io.Serializable;

/**
 * <b>Class</b> for {@code Dirt} blocks
 *
 * @author 4347
 */
public final class Dirt extends Block implements Serializable {

    /**
     * The serial version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The texture of this block({@link Dirt DIRT}).
     *
     * @see BlockTexture
     */
    private static final BlockTexture TEXTURE = new BlockTexture("assets/textures/test.png");

    /**
     * The type of this block.
     *
     * @see Type#DIRT
     */
    private static final Type TYPE = Type.DIRT;

    /**
     * The name of this block.
     *
     * @see Type#name
     */
    private static final String NAME = TYPE.name;

    /**
     * The texture coordinates of this block
     * <li>This is with using {@link #INDICES indices}
     *
     * @see #INDICES
     */
    private static final float[] TCS = new float[] {
            //Positive X
            2 / 25f, 0 / 25f,
            2 / 25f, 1 / 25f,
            3 / 25f, 1 / 25f,
            3 / 25f, 0 / 25f,

            //Negative X
            2 / 25f, 0 / 25f,
            2 / 25f, 1 / 25f,
            3 / 25f, 1 / 25f,
            3 / 25f, 0 / 25f,

            //Positive Y
            2 / 25f, 0 / 25f,
            2 / 25f, 1 / 25f,
            3 / 25f, 1 / 25f,
            3 / 25f, 0 / 25f,

            //Negative Y
            2 / 25f, 0 / 25f,
            2 / 25f, 1 / 25f,
            3 / 25f, 1 / 25f,
            3 / 25f, 0 / 25f,

            //Positive Z
            2 / 25f, 0 / 25f,
            2 / 25f, 1 / 25f,
            3 / 25f, 1 / 25f,
            3 / 25f, 0 / 25f,

            //Negative Z
            2 / 25f, 0 / 25f,
            2 / 25f, 1 / 25f,
            3 / 25f, 1 / 25f,
            3 / 25f, 0 / 25f
    };

    /**
     * The model of this block.
     *
     * @see #loadModel(float[], int[], float[], BlockTexture)
     */
    public static final BlockModel MODEL = loadModel(VERTICES, TCS, NORMALS, INDICES, TEXTURE);

    /**
     * Create a block with the position {@code x}, {@code y}, {@code z} with the default {@link #MODEL model}
     *
     * @param x The x position of the block
     * @param y The y position of the block
     * @param z The z position of the block
     * @author 4347
     */
    public Dirt(float x, float y, float z) {
        super(MODEL, new Vector3f(x, y, z));
    }

    /**
     * Create a block with the position with the default {@link #MODEL model}
     *
     * @param position The position of the block
     * @author 4347
     */
    public Dirt(@NotNull Vector3f position) {
        super(MODEL, position);
    }

    /**
     * Create a block with the position and rotation with the default {@link #MODEL model}
     *
     * @param position The position of the block
     * @param rotation The rotation of the block
     * @author 4347
     */
    public Dirt(@NotNull Vector3f position, @NotNull Vector3f rotation) {
        super(MODEL, position, rotation);
    }

    /**
     * Create a block with the position with the texture
     * <li>If the texture is null it will use the default {@link #TEXTURE texture}
     *
     * @param texture  The texture of the block
     * @param position The position of the block
     * @author 4347
     */
    public Dirt(@Nullable BlockTexture texture, @NotNull Vector3f position) {
        super((texture == null) ? TEXTURE : texture, position);
    }

    /**
     * Create a block with the position and rotation with the texture
     * <li>If the texture is null it will use the default {@link #TEXTURE texture}
     *
     * @param texture  The texture of the block
     * @param position The position of the block
     * @param rotation The rotation of the block
     * @author 4347
     */
    public Dirt(@Nullable BlockTexture texture, @NotNull Vector3f position, @NotNull Vector3f rotation) {
        super((texture == null) ? TEXTURE : texture, position, rotation);
    }

    /**
     * Create a block with the position with the model
     * <li>If the model is null it will use the default {@link #MODEL model}
     *
     * @param model    The model of the block
     * @param position The position of the block
     * @author 4347
     */
    public Dirt(@Nullable BlockModel model, @NotNull Vector3f position) {
        super((model == null) ? MODEL : model, position);
    }

    /**
     * Create a block with the position and rotation with the model
     * <li>If the model is null it will use the default {@link #MODEL model}
     *
     * @param model    The model of the block
     * @param position The position of the block
     * @param rotation The rotation of the block
     * @author 4347
     */
    public Dirt(@Nullable BlockModel model, @NotNull Vector3f position, @NotNull Vector3f rotation) {
        super((model == null) ? MODEL : model, position, rotation);
    }

    /**
     * Returns the {@link #NAME name} of the block
     *
     * @return The name of the block
     * @author 4347
     */
    @NotNull
    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Returns the width of the block
     * <li>Normally it will return 1f
     * <li>On block {@link Dirt dirt} it will return 1f
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
     * <li>On block {@link Dirt dirt} it will return 1f
     *
     * @return The height of the block
     * @author 4347
     */
    @Override
    public float getHeight() {
        return super.getHeight();
    }

    /**
     * Returns the scale of the block
     * <li>Normally it will return <i>Vector3f(1, 1, 1)</i>
     * <li>On block {@link Dirt dirt} it will return <i>Vector3f(1, 1, 1)</i>
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
     * Returns the {@link #TYPE type} of this block
     * <li>On block {@link Dirt dirt} it will return <i>{@link #TYPE}</i>
     *
     * @return The type of this block
     * @author 4347
     */
    @NotNull
    @Override
    public Type getType() {
        return TYPE;
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
     * @see java.util.HashMap
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}.
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
        return super.hashCode();
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
        return super.toString();
    }

}
