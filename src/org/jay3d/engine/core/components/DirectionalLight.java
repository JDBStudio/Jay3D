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

    public DirectionalLight(Vector3f colour, float intensity){
        super(colour, intensity);
        setShader(ForwardDirectional.getInstance());
    }


    public Vector3f getDirection() {
        return getTransform().getTransformedRot().getForward();
    }
}
