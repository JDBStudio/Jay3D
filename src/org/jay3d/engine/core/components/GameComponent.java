package org.jay3d.engine.core.components;

import org.jay3d.engine.core.CoreEngine;
import org.jay3d.engine.core.GameObject;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.rendering.Shader;

/**
 * GameComponent is the super class for any component to be rendered within the scene graph. There is no particular
 * reason to use this class unless overriding it's method for your own custom use. All methods within the class are
 * self-explanatory and are documented throughout the other classes that inherit GameComponent.
 *
 * @author Juxhin Dyrmishi Brigjaj
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