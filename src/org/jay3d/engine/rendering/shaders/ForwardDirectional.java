package org.jay3d.engine.rendering.shaders;

import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.rendering.material.Material;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class ForwardDirectional extends Shader{
    private static final ForwardDirectional INSTANCE = new ForwardDirectional();

    public static ForwardDirectional getInstance(){
        return INSTANCE;
    }

    public ForwardDirectional(){
        super("Forward-directional");
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine){
        super.updateUniforms(transform, material, renderingEngine);
    }
}
