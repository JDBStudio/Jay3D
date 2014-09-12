package org.jay3d.engine.render.light;

import org.jay3d.engine.math.Vector3f;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 *
 * This is mainly a wrapper class for my phongFragment
 * shader class.
 */
public class PointLight {
    private BaseLight baseLight;
    private Vector3f position;
    private Attenuation atten;

    public PointLight(BaseLight baseLight, Vector3f position, Attenuation atten) {
        this.baseLight = baseLight;
        this.position = position;
        this.atten = atten;
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

    public Attenuation getAtten() {
        return atten;
    }

    public void setAtten(Attenuation atten) {
        this.atten = atten;
    }
}
