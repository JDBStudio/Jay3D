package org.jay3d.engine.render.shaders;

import org.jay3d.engine.math.Matrix4f;
import org.jay3d.engine.math.Vector3f;
import org.jay3d.engine.render.RenderUtil;
import org.jay3d.engine.render.ResourceLoader;
import org.jay3d.engine.render.Transform;
import org.jay3d.engine.render.light.BaseLight;
import org.jay3d.engine.render.light.DirectionalLight;
import org.jay3d.engine.render.light.PointLight;
import org.jay3d.engine.render.material.Material;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class PhongShader extends Shader{
    private static final PhongShader instance = new PhongShader();
    private static final int MAX_POINT_LIGHTS = 4;

    public static PhongShader getInstance()
    {
        return instance;
    }
    private static Vector3f ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
    private static DirectionalLight directionalLight = new DirectionalLight(new BaseLight
            (new Vector3f(0, 0, 0), 0),new Vector3f(0,0,0));

    private static PointLight[] pointLights = new PointLight[]{};

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

        addUniform("specularIntensity");
        addUniform("specularPower");
        addUniform("eyePos");

        addUniform("directionalLight.base.colour");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");

        for(int i = 0; i < MAX_POINT_LIGHTS; i++){
            addUniform("pointLights[" + i + "].base.colour");
            addUniform("pointLights[" + i + "].base.intensity");
            addUniform("pointLights[" + i + "].atten.constant");
            addUniform("pointLights[" + i + "].atten.linear");
            addUniform("pointLights[" + i + "].atten.exponent");
            addUniform("pointLights[" + i + "].position");
            addUniform("pointLights[" + i + "].range");
        }
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
        for(int i = 0; i < pointLights.length; i++){
            setUniform("pointLights[" + i + "]", pointLights[i]);
        }


        setUniformf("specularIntensity", material.getSpecularIntensity());
        setUniformf("specularPower", material.getSpecularPower());

        setUniform("eyePos", Transform.getCamera().getPos());
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

    public static void setPointLights(PointLight[] pointLights){
        if(pointLights.length > MAX_POINT_LIGHTS){
            System.err.println("TOO MANY POINT LIGHTS(MAX: " + MAX_POINT_LIGHTS + ")");
            new Exception().printStackTrace();
            System.exit(1);
        }

        PhongShader.pointLights = pointLights;
    }

    public void setUniform(String uniformName, BaseLight baseLight){
        setUniform(uniformName + ".colour", baseLight.getColour());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    public void setUniform(String uniformName, DirectionalLight directionalLight){
        setUniform(uniformName + ".base", directionalLight.getBase());
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }

    public void setUniform(String uniformName, PointLight pointLight) {
        setUniform(uniformName + ".base", pointLight.getBaseLight());
        setUniformf(uniformName + ".atten.constant", pointLight.getAtten().getConstant());
        setUniformf(uniformName + ".atten.linear", pointLight.getAtten().getLinear());
        setUniformf(uniformName + ".atten.exponent", pointLight.getAtten().getExponent());
        setUniform(uniformName + ".position", pointLight.getPosition());
        setUniformf(uniformName + ".range", pointLight.getRange());
    }
}
