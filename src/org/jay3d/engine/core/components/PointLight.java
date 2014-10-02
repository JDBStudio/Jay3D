package org.jay3d.engine.core.components;

import org.jay3d.engine.core.math.Attenuation;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.Shader;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */

public class PointLight extends BaseLight {
    private static final int COLOUR_DEPTH = 256;
    private Attenuation attenuation;
    private float range;

    public PointLight(Vector3f colour, float intensity, Attenuation attenuation) {
        super(colour, intensity);
        this.attenuation = attenuation;

        float a = attenuation.getExponent();
        float b = attenuation.getLinear();
        float c = attenuation.getConstant() - COLOUR_DEPTH * getIntensity() * getColour().max();

        this.range = (float)(-b + Math.sqrt(b * b - 4 * a * c))/(2 * a);//Quadratic equation
        setShader(new Shader("Forward-point"));
    }

    public float getRange() {
        return range;
    }

    public float getConstant() {
        return attenuation.getX();
    }

    public float getLinear() {
        return attenuation.getY();
    }

    public float getExponent() {
        return attenuation.getZ();
    }

    public Attenuation getAttenuation(){
        return attenuation;
    }

}
