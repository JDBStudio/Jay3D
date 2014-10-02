package org.jay3d.engine.rendering.shaders;

import org.jay3d.engine.core.math.Matrix4f;
import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.core.components.BaseLight;
import org.jay3d.engine.core.components.PointLight;
import org.jay3d.engine.core.components.SpotLight;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.rendering.material.Material;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class ForwardSpotlight extends Shader{
    private static final ForwardSpotlight INSTANCE = new ForwardSpotlight();

    public static ForwardSpotlight getInstance(){
        return INSTANCE;
    }

    public ForwardSpotlight(){
        super("Forward-spot");
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine){
        super.updateUniforms(transform, material, renderingEngine);
    }
}
