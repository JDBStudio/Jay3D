package org.jay3d.engine.rendering.shaders;

import org.jay3d.engine.core.math.Matrix4f;
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
        super();

        String vertexShaderText = loadShader("Forward-ambient.vs");
        String fragmentShaderText = loadShader("Forward-ambient.fs");

        addVertexShader(vertexShaderText);
        addFragmentShader(fragmentShaderText);

        addAllAttirbutes(vertexShaderText);
        addAllAttirbutes(fragmentShaderText);

        compileShader();

        addAllUniforms(vertexShaderText);
        addAllUniforms(fragmentShaderText);
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine){
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
        material.getTexture("diffuseTexture").bind();

        setUniform("MVP", projectedMatrix);
        setUniform("ambientIntensity", renderingEngine.getAmbientLight());
    }

}
