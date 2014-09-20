package org.jay3d.engine.core.components;

import org.jay3d.engine.core.components.PointLight;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.shaders.ForwardSpotlight;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class SpotLight extends PointLight{
    private PointLight pointLight;
    private Vector3f direction;
    private float cutoff;

    public SpotLight(Vector3f colour,
                     float intensity,
                     float constant,
                     float linear,
                     float exponent,
                     Vector3f position,
                     float range,
                     Vector3f direction,
                     float cutoff) {
        super(colour, intensity, constant, exponent, linear, position, range);
        this.direction = direction;
        this.cutoff = cutoff;

        setShader(ForwardSpotlight.getInstance());
    }

    public PointLight getPointLight() {
        return pointLight;
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction.normalise();
    }

    public float getCutoff() {
        return cutoff;
    }

    public void setCutoff(float cutoff) {
        this.cutoff = cutoff;
    }
}
