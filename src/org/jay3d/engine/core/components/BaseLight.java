package org.jay3d.engine.core.components;

import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.shaders.Shader;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 *
 * This is mainly a wrapper class for my phongFragment
 * shader class.
 */
public class BaseLight extends GameComponent {
    private Vector3f colour;
    private float intensity;
    private Shader shader;

    public BaseLight(Vector3f colour, float intensity){
        this.colour = colour;
        this.intensity = intensity;
    }

    @Override
    public void addToRenderingEngine(RenderingEngine engine){
        engine.addLight(this);
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    public void setShader(Shader shader){
        this.shader = shader;
    }

    public Shader getShader() {
        return shader;
    }
}
