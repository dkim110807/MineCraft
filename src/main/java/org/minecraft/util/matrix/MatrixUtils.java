package org.minecraft.util.matrix;

import org.minecraft.entity.Camera;
import org.minecraft.util.vector.Matrix4f;
import org.minecraft.util.vector.Vector2f;
import org.minecraft.util.vector.Vector3f;

public final class MatrixUtils {

    private static final float FOV = 70f;
    private static final float NEAR = 0.1f;
    private static final float FAR = 1000f;

    private MatrixUtils() {
    }

    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);

        return matrix;
    }

    public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, Vector3f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rotation.x), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rotation.x), new Vector3f(0, 0, 1), matrix, matrix);
        Matrix4f.scale(scale, matrix, matrix);

        return matrix;
    }

    public static Matrix4f createProjectionMatrix() {

        Matrix4f matrix = new Matrix4f();

        float aspect = 1280f / 720f;
        float yScale = 1f / (float) Math.tan(Math.toRadians(FOV / 2f));
        float xScale = yScale / aspect;
        float zp = FAR + NEAR;
        float zm = FAR - NEAR;

        matrix.m00 = xScale;
        matrix.m11 = yScale;
        matrix.m22 = -zp / zm;
        matrix.m23 = -1;
        matrix.m32 = -(2 * FAR * NEAR) / zm;
        matrix.m33 = 0;

        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {

        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        Matrix4f.rotate((float) Math.toRadians(camera.getRotation().x), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getRotation().y), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getRotation().z), new Vector3f(0, 0, 1), matrix, matrix);
        Matrix4f.translate(new Vector3f(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z), matrix, matrix);

        return matrix;
    }

}
