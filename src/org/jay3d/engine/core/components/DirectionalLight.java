package org.jay3d.engine.core.components;

import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.shaders.ForwardDirectional;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 *
 * This is mainly a wrapper class for my phongFragment
 * shader class.
 */
public class DirectionalLight extends BaseLight{
    private BaseLight base;
    private Vector3f direction;

    public DirectionalLight(Vector3f colour, float intensity, Vector3f direction){
        super(colour, intensity);
        this.direction = direction.normalise();
        setShader(ForwardDirectional.getInstance());
    }


    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
}
