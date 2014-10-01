package org.jay3d.engine.rendering.shaders;

import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.rendering.material.Material;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class ForwardAmbient extends Shader{
    private static final ForwardAmbient INSTANCE = new ForwardAmbient();

    public static ForwardAmbient getInstance(){
        return INSTANCE;
    }

    public ForwardAmbient(){
        super("Forward-ambient");
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine){
        super.updateUniforms(transform, material, renderingEngine);
    }
}
