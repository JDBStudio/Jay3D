package org.jay3d.engine.core.components;

import org.jay3d.engine.core.RenderingEngine;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.light.BaseLight;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 *
 * This is mainly a wrapper class for my phongFragment
 * shader class.
 */
public class DirectionalLight extends GameComponent{
    private BaseLight base;
    private Vector3f direction;

    public DirectionalLight(BaseLight base, Vector3f direction){
        this.base = base;
        this.direction = direction.normalise();
    }

    @Override
    public void addToRenderingEngine(RenderingEngine engine){
        engine.addDirectionalLight(this);
    }

    public BaseLight getBase() {
        return base;
    }

    public void setBase(BaseLight base) {
        this.base = base;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
}
