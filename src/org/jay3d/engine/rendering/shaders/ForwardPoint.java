package org.jay3d.engine.rendering.shaders;

import org.jay3d.engine.core.math.Matrix4f;
import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.core.components.BaseLight;
import org.jay3d.engine.core.components.PointLight;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.rendering.material.Material;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class ForwardPoint extends Shader{
    private static final ForwardPoint INSTANCE = new ForwardPoint();

    public static ForwardPoint getInstance(){
        return INSTANCE;
    }

    public ForwardPoint(){
        super("Forward-point");
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine){
        super.updateUniforms(transform, material, renderingEngine);
    }
}
