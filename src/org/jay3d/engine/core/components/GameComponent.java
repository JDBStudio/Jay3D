package org.jay3d.engine.core.components;

import org.jay3d.engine.core.RenderingEngine;
import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.rendering.shaders.Shader;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public abstract class GameComponent {
    public void input(Transform transform, float delta) {}

    public void update(Transform transform, float delta) {}

    public void render(Transform transform, Shader shader) {}

    public void addToRenderingEngine(RenderingEngine renderingEngine) {}
}