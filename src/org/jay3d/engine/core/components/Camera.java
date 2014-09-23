package org.jay3d.engine.core.components;

import org.jay3d.engine.core.Input;
import org.jay3d.engine.core.math.Matrix4f;
import org.jay3d.engine.core.math.Quaternion;
import org.jay3d.engine.core.math.Vector2f;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.rendering.Window;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Camera extends GameComponent {
    public static final Vector3f Y_AXIS = new Vector3f(0,1,0);
    private Matrix4f projection;

    public Camera(float fov, float aspect, float zNear, float zFar) {
        this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
    }

    public Matrix4f getViewProjection() {
        Matrix4f cameraRotation = getTransform().getRot().toRotationMatrix();
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-getTransform().getPos().getX(), -getTransform().getPos().getY(), -getTransform().getPos().getZ());
        return projection.mul(cameraRotation.mul(cameraTranslation));
    }

    @Override
    public void addToRenderingEngine(RenderingEngine renderingEngine) {
        renderingEngine.addCamera(this);
    }

    boolean mouseLocked = false;
    Vector2f centerPosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);

    @Override
    public void input(float delta) {
        float sensitivity = -0.125f;
        float movAmt = 10 * delta;

        if(Input.getKey(Input.KEY_ESCAPE)) {
            Input.setCursor(true);
            mouseLocked = false;
        }

        if(Input.getMouseDown(0)) {
            Input.setMousePosition(centerPosition);
            Input.setCursor(false);
            mouseLocked = true;
        }

        if(Input.getKey(Input.KEY_W))
            move(getTransform().getRot().getForward(), movAmt);
        if(Input.getKey(Input.KEY_S))
            move(getTransform().getRot().getForward(), -movAmt);
        if(Input.getKey(Input.KEY_A))
            move(getTransform().getRot().getLeft(), movAmt);
        if(Input.getKey(Input.KEY_D))
            move(getTransform().getRot().getRight(), movAmt);

        if(mouseLocked) {
            Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);
            boolean rotY = deltaPos.getX() != 0;
            boolean rotX = deltaPos.getY() != 0;
            if(rotY)
                getTransform().setRot(getTransform().getRot().mul(new Quaternion(Y_AXIS, (float) Math.toRadians(deltaPos.getX() * sensitivity))).normalise());
            if(rotX)
                getTransform().setRot(getTransform().getRot().mul(new Quaternion(getTransform().getRot().getRight(), ((float) Math.toRadians(-deltaPos.getY() * sensitivity)))).normalise());
            if(rotY || rotX)
                Input.setMousePosition(new Vector2f(Window.getWidth()/2, Window.getHeight()/2));
        }
    }

    public void move(Vector3f dir, float amt) {
        getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
    }
}