package org.jay3d.engine.rendering.shaders;

import org.jay3d.engine.core.math.Matrix4f;
import org.jay3d.engine.core.math.Transform;
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

        addVertexShaderFromFile("Forward-ambient.vs");
        addFragmentShaderFromFile("Forward-ambient.fs");

        setAttribLocation("positon", 0);
        setAttribLocation("texCoord", 1);

        compileShader();

        addUniform("MVP");
        addUniform("ambientIntensity");
    }

    @Override
    public void updateUniforms(Transform transform, Material material){
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
        material.getTexture().bind();

        setUniform("MVP", projectedMatrix);
        setUniform("ambientIntensity", getRenderingEngine().getAmbientLight());
    }

}
