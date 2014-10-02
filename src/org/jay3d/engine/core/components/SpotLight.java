package org.jay3d.engine.core.components;

import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.Shader;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class SpotLight extends PointLight{
    private PointLight pointLight;
    private float cutoff;

    public SpotLight(Vector3f colour,
                     float intensity,
                     Vector3f attenuation,
                     float cutoff) {
        super(colour, intensity, attenuation);
        this.cutoff = cutoff;

        setShader(new Shader("Forward-spot"));
    }

    public PointLight getPointLight() {
        return pointLight;
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    public Vector3f getDirection() {
        return getTransform().getTransformedRot().getForward();
    }

    public float getCutoff() {
        return cutoff;
    }

    public void setCutoff(float cutoff) {
        this.cutoff = cutoff;
    }
}
