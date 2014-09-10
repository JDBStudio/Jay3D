package org.jay3d.engine.render.shaders;

import org.jay3d.engine.math.Matrix4f;
import org.jay3d.engine.math.Vector3f;
import org.jay3d.engine.render.RenderUtil;
import org.jay3d.engine.render.ResourceLoader;
import org.jay3d.engine.render.material.Material;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class PhongShader extends Shader{
    private static final PhongShader instance = new PhongShader();
    public static PhongShader getInstance()
    {
        return instance;
    }
    private static Vector3f ambientLight;
    private PhongShader()
    {
        super();
        addVertexShader(ResourceLoader.loadShader("phongVertex.vs"));
        addFragmentShader(ResourceLoader.loadShader("phongFragment.fs"));
        compileShader();
        addUniform("transform");
        addUniform("baseColor");
        addUniform("ambientLight");
    }
    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material)
    {
        if(material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();
        setUniform("transform", projectedMatrix);
        setUniform("baseColor", material.getColour());
        setUniform("ambientLight", ambientLight);
    }
    public static Vector3f getAmbientLight()
    {
        return ambientLight;
    }
    public static void setAmbientLight(Vector3f ambientLight)
    {
        PhongShader.ambientLight = ambientLight;
    }
}
