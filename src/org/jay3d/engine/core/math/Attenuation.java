package org.jay3d.engine.core.math;

/**
 * Created by Juxhin on 02/10/14.
 * Do not distribute without permission
 */
public class Attenuation extends Vector3f{

    public Attenuation(float contstant, float linear, float exponent) {
        super(contstant, linear, exponent);
    }

    public float getConstant(){
        return getX();
    }

    public float getLinear(){
        return getY();
    }

    public float getExponent(){
        return getZ();
    }
}
