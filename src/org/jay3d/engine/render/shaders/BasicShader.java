package org.jay3d.engine.render.shaders;

import org.jay3d.engine.math.Matrix4f;
import org.jay3d.engine.render.RenderUtil;
import org.jay3d.engine.render.ResourceLoader;
import org.jay3d.engine.render.material.Material;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class BasicShader extends Shader{
    public BasicShader(){
        super();

        addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
        compileShader();

        addUniform("transform");
        addUniform("colour");
    }

    @Override
    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material){
        if(material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();

        setUniform("transform", projectedMatrix);
        setUniform("colour", material.getColour());
    }
}
