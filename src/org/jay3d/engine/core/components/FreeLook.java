package org.jay3d.engine.core.components;

import org.jay3d.engine.core.Input;
import org.jay3d.engine.core.math.Vector2f;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.Window;

/**
 * Created by Juxhin on 02/10/14.
 * Do not distribute without permission
 */
public class FreeLook extends GameComponent{
    public static final Vector3f Y_AXIS = new Vector3f(0,1,0);

    boolean mouseLocked = false;
    private float sensitivity;
    private int unlockMouseKey;
    Vector2f centerPosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);

    public FreeLook(float sensitivity) {
        this(sensitivity, Input.KEY_ESCAPE);
    }

    public FreeLook(float sensitivity, int unlockMouseKey) {
        this.sensitivity = sensitivity;
        this.unlockMouseKey = unlockMouseKey;
    }

    @Override
    public void input(float delta) {
        float sensitivity = this.sensitivity;

        if(Input.getKey(unlockMouseKey)) {
            Input.setCursor(true);
            mouseLocked = false;
        }

        if(Input.getMouseDown(0)) {
            Input.setMousePosition(centerPosition);
            Input.setCursor(false);
            mouseLocked = true;
        }

        if(mouseLocked) {
            Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);
            boolean rotY = deltaPos.getX() != 0;
            boolean rotX = deltaPos.getY() != 0;
            if(rotY)
                getTransform().rotate(Y_AXIS, (float) Math.toRadians(deltaPos.getX() * sensitivity));
            if(rotX)
                getTransform().rotate(getTransform().getRot().getRight(), ((float) Math.toRadians(-deltaPos.getY() * sensitivity)));
            if(rotY || rotX)
                Input.setMousePosition(new Vector2f(Window.getWidth()/2, Window.getHeight()/2));
        }
    }
}
