package org.jay3d.engine.render;

import org.jay3d.engine.math.Matrix4f;
import org.jay3d.engine.math.Vector3f;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Transform {
    private Vector3f translation;

    public Transform() {
        translation = new Vector3f(0, 0, 0);
    }

    public Matrix4f getTransformation(){
        Matrix4f translationMatrix = new Matrix4f().initTranslation(
                translation.getX(), translation.getY(), translation.getZ());

        return translationMatrix;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public void setTranslation(float x, float y, float z) {
        this.translation = new Vector3f(x, y, z);
    }
}
