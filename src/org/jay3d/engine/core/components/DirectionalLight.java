package org.jay3d.engine.core.components;

import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.Shader;

/**
 * @author Juxhin Dyrmishi Brigjaj
 */
public class DirectionalLight extends BaseLight{
    private BaseLight base;

    public DirectionalLight(Vector3f colour, float intensity){
        super(colour, intensity);
        setShader(new Shader("Forward-directional"));
    }


    public Vector3f getDirection() {
        return getTransform().getTransformedRot().getForward();
    }
}
