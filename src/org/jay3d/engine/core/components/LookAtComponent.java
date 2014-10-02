package org.jay3d.engine.core.components;

import org.jay3d.engine.core.math.Quaternion;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.rendering.Shader;

/**
 * Mainly a convenience class that makes use of Quaternion#normalLinearInterpolation via the update method.
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class LookAtComponent extends GameComponent {
    private RenderingEngine m_renderingEngine;


    @Override
    public void update(float delta) {
        if (m_renderingEngine != null) {
            Quaternion newRot = getTransform().getLookAtDirection(m_renderingEngine.getMainCamera().getTransform().getTranformedPos(),
                    new Vector3f(0, 1, 0));
            getTransform().setRot(getTransform().getRot().normalLinearInterpolation(newRot, delta * 5.0f, true));
            //getTransform().setRot(GetTransform().getRot().spheticalInterpolation(newRot, delta * 5.0f, true));
        }
    }

    @Override
    public void render(Shader shader, RenderingEngine renderingEngine) {
        this.m_renderingEngine = renderingEngine;

    }
}