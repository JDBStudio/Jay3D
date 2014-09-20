package org.jay3d.engine.core.components;

import org.jay3d.engine.core.RenderingEngine;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.light.Attenuation;
import org.jay3d.engine.rendering.light.BaseLight;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 *
 * This is mainly a wrapper class for my phongFragment
 * shader class.
 */

public class PointLight extends GameComponent {
    private BaseLight baseLight;
    private Attenuation atten;
    private Vector3f position;
    private float range;

    public PointLight(BaseLight baseLight, Attenuation atten, Vector3f position, float range) {
        this.baseLight = baseLight;
        this.atten = atten;
        this.position = position;
        this.range = range;
    }

    @Override
    public void addToRenderingEngine(RenderingEngine engine){
        engine.addPointLight(this);
    }

    public BaseLight getBaseLight() {
        return baseLight;
    }

    public void setBaseLight(BaseLight baseLight) {
        this.baseLight = baseLight;
    }

    public Attenuation getAtten() {
        return atten;
    }

    public void setAtten(Attenuation atten) {
        this.atten = atten;
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
}
