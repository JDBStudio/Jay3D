package org.jay3d.engine.core.components;

import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.shaders.ForwardPoint;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */

public class PointLight extends BaseLight {
    private static final int COLOUR_DEPTH = 256;

    private Vector3f attenuation;
    private float range;

    public PointLight(Vector3f colour, float intensity, Vector3f attenuation) {
        super(colour, intensity);
        this.attenuation = attenuation;

        float a = attenuation.getZ();
        float b = attenuation.getY();
        float c = attenuation.getX() - COLOUR_DEPTH * getIntensity() * getColour().max();

        this.range = (float)(-b + Math.sqrt(b * b - 4 * a * c))/(2 * a);//Quadratic equation
        setShader(ForwardPoint.getInstance());
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getConstant() {
        return attenuation.getX();
    }

    public void setConstant(float constant) {
        this.attenuation.setX(constant);
    }

    public float getLinear() {
        return attenuation.getY();
    }

    public void setLinear(float linear) {
        this.attenuation.setY(linear);
    }

    public float getExponent() {
        return attenuation.getZ();
    }

    public void setExponent(float exponent) {
        this.attenuation.setZ(exponent);
    }
}
