package org.jay3d.engine.rendering.shaders;

import org.jay3d.engine.core.math.Matrix4f;
import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.rendering.material.Material;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class BasicShader extends Shader{
    private static final BasicShader INSTANCE = new BasicShader();

    public static BasicShader getInstance(){
        return INSTANCE;
    }
    public BasicShader(){
        super();

        addVertexShaderFromFile("basicVertex.vs");
        addFragmentShaderFromFile("basicFragment.fs");
        compileShader();

        addUniform("transform");
        addUniform("colour");
    }

    @Override
    public void updateUniforms(Transform transform, Material material){
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
        material.getTexture().bind();

        setUniform("transform", projectedMatrix);
        setUniform("colour", material.getColour());
    }
}
