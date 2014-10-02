package org.jay3d.engine.core.components;

import org.jay3d.engine.core.CoreEngine;
import org.jay3d.engine.core.math.Matrix4f;
import org.jay3d.engine.core.math.Vector3f;

/**
 * @author Juxhin Dyrmishi Brigjaj
 */
public class Camera extends GameComponent {
    private Matrix4f projection;

    /**
     *
     * @param fov
     *      The Camera's field of view
     * @param aspect
     *      The Camera's apect, it is recommend that you simply set it to Window width divided by Window height.
     * @param zNear
     *      The closest depth distance, generally something like 0.01f.
     * @param zFar
     *      The furthest depth distance, Example: 1000.0f.
     */
    public Camera(float fov, float aspect, float zNear, float zFar) {
        this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
    }

    /**
     * This allows us to get the camera's view projection.
     *
     * <p>
     *     This is done by multiplying two Matrices(Camera's rotation and Camera's translation) with the camera's
     *     current Matrix projection.
     * </p>
     * @return
     *      Updated projection Matrix for the camera object
     */
    public Matrix4f getViewProjection() {
        Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
        Vector3f cameraPos = getTransform().getTranformedPos().mul(-1);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
        return projection.mul(cameraRotation.mul(cameraTranslation));
    }

    /**
     * A convenience method that overrides it's parent's method.
     *
     * <p>
     *     Allows us to easily add cameras inside our scene graph by adding it to our Core Engine(Not Rendering
     *     Engine!).
     * </p>
     * @param engine
     *      The Core Engine to which we want to add the camera to.
     */
    @Override
    public void addToEngine(CoreEngine engine) {
        engine.getRenderingEngine().addCamera(this);
    }
}