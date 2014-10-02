package org.jay3d.engine.core.components;

import org.jay3d.engine.core.Input;
import org.jay3d.engine.core.math.Vector3f;

/**
 * Created by Juxhin on 02/10/14.
 * Do not distribute without permission
 */
public class FreeMove extends GameComponent {
    private float speed;
    private int forwardKey, backKey, leftKey, rightKey;

    public FreeMove(float speed) {
        this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
    }

    public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey) {
        this.speed = speed;
        this.forwardKey = forwardKey;
        this.backKey = backKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }

    @Override
    public void input(float delta) {
        float movAmt = speed * delta;

        if(Input.getKey(forwardKey))
            move(getTransform().getRot().getForward(), movAmt);
        if(Input.getKey(backKey))
            move(getTransform().getRot().getForward(), -movAmt);
        if(Input.getKey(leftKey))
            move(getTransform().getRot().getLeft(), movAmt);
        if(Input.getKey(rightKey))
            move(getTransform().getRot().getRight(), movAmt);
    }

    public void move(Vector3f dir, float amt) {
        getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
    }
}
