package org.jay3d.engine.core.components;

import org.jay3d.engine.core.CoreEngine;
import org.jay3d.engine.core.GameObject;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.rendering.Shader;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public abstract class GameComponent {
    private GameObject parent;

    public void input(float delta) {}

    public void update(float delta) {}

    public void render(Shader shader, RenderingEngine renderingEngine) {}

    public void addToEngine(CoreEngine engine) {

    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public Transform getTransform() {
        return parent.getTransform();
    }
}