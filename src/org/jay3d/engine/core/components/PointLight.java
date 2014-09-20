package org.jay3d.engine.core.components;

import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.shaders.ForwardPoint;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 *
 * This is mainly a wrapper class for my phongFragment
 * shader class.
 */

public class PointLight extends BaseLight {
    private BaseLight baseLight;
    private Vector3f position;
    private float constant;
    private float linear;
    private float exponent;
    private float range;

    public PointLight(Vector3f colour, float intensity, float constant, float linear, float exponent,Vector3f position, float range) {
        super(colour, intensity);
        this.position = position;
        this.range = range;
        this.constant = constant;
        this.linear = linear;
        this.exponent = exponent;

        setShader(ForwardPoint.getInstance());
    }

    public BaseLight getBaseLight() {
        return baseLight;
    }

    public void setBaseLight(BaseLight baseLight) {
        this.baseLight = baseLight;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getConstant() {
        return constant;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }

    public float getLinear() {
        return linear;
    }

    public void setLinear(float linear) {
        this.linear = linear;
    }

    public float getExponent() {
        return exponent;
    }

    public void setExponent(float exponent) {
        this.exponent = exponent;
    }
}
