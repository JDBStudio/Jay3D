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
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
        material.getTexture("diffuse").bind();

        setUniform("model", worldMatrix);
        setUniform("mvp", projectedMatrix);

        setUniformf("specularIntensity", material.getFloat("specularIntensity"));
        setUniformf("specularPower", material.getFloat("specularPower"));

        setUniform("eyePos", renderingEngine.getMainCamera().getTransform().getTranformedPos());
        setUniformPointLight("pointLight", (PointLight)renderingEngine.getActiveLight());
    }

    public void setUniformBaseLight(String uniformName, BaseLight baseLight){
        setUniform(uniformName + ".colour", baseLight.getColour());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    public void setUniformPointLight(String uniformName, PointLight pointLight) {
        setUniformBaseLight(uniformName + ".base", pointLight);
        setUniformf(uniformName + ".atten.constant", pointLight.getConstant());
        setUniformf(uniformName + ".atten.linear", pointLight.getLinear());
        setUniformf(uniformName + ".atten.exponent", pointLight.getExponent());
        setUniform(uniformName + ".position", pointLight.getTransform().getPos());
        setUniformf(uniformName + ".range", pointLight.getRange());
    }
}
