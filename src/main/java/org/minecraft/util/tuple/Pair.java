package org.minecraft.util.tuple;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Pair<F,S> implements Serializable {

    /**
     * The serial version
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The first parameter
     */
    public F first;

    /**
     * The second parameter
     */
    public S second;

    /**
     * Creates pair
     *
     * @param first The first value
     * @param second The second value
     * @author 4347
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first value of this pair
     *
     * @return The first value of this pair
     * @author 4347
     */
    public F getFirst() {
        return first;
    }

    /**
     * Sets the first value of this pair
     *
     * @param first The new first value of this pair
     * @author 4347
     */
    public void setFirst(F first) {
        this.first = first;
    }

    /**
     * Returns the second value of this pair
     *
     * @return The second value of this pair
     * @author 4347
     */
    public S getSecond() {
        return second;
    }

    /**
     * Sets the second value of this pair
     *
     * @param second The new second value of this pair
     * @author 4347
     */
    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

}
