package org.jay3d.engine.core;

import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.rendering.shaders.Shader;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public interface GameComponent {
    public void input(Transform transform);
    public void update(Transform transform);
    public void render(Transform transform, Shader shader);
}
