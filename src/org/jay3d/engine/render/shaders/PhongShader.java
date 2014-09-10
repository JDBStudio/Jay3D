package org.jay3d.engine.render.shaders;

import org.jay3d.engine.math.Matrix4f;
import org.jay3d.engine.math.Vector3f;
import org.jay3d.engine.render.RenderUtil;
import org.jay3d.engine.render.ResourceLoader;
import org.jay3d.engine.render.light.BaseLight;
import org.jay3d.engine.render.light.DirectionalLight;
import org.jay3d.engine.render.material.Material;

import static org.lwjgl.opengl.GL20.glUniform1i;

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
    private static Vector3f ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
    private static DirectionalLight directionalLight = new DirectionalLight(new BaseLight
            (new Vector3f(0, 0, 0), 0),new Vector3f(0,0,0));

    private PhongShader()
    {
        super();
        addVertexShader(ResourceLoader.loadShader("phongVertex.vs"));
        addFragmentShader(ResourceLoader.loadShader("phongFragment.fs"));
        compileShader();
        addUniform("transform");
        addUniform("transformProjected");
        addUniform("baseColour");
        addUniform("ambientLight");

        addUniform("directionalLight.base.colour");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");
    }
    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material)
    {
        if(material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();
        setUniform("transformProjected", projectedMatrix);
        setUniform("transform", worldMatrix);
        setUniform("baseColour", material.getColour());
        setUniform("ambientLight", ambientLight);
        setUniform("directionalLight", directionalLight);
    }
    public static Vector3f getAmbientLight()
    {
        return ambientLight;
    }
    public static void setAmbientLight(Vector3f ambientLight)
    {
        PhongShader.ambientLight = ambientLight;
    }

    public static void setDirectionalLight(DirectionalLight directionalLight){
        PhongShader.directionalLight = directionalLight;
    }

    public void setUniform(String uniformName, BaseLight baseLight){
        setUniform(uniformName + ".colour", baseLight.getColour());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    public void setUniform(String uniformName, DirectionalLight directionalLight){
        setUniform(uniformName + ".base", directionalLight.getBase());
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }

}
