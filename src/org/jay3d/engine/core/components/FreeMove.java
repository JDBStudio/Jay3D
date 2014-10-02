package org.jay3d.engine.core.components;

import org.jay3d.engine.core.Input;
import org.jay3d.engine.core.math.Vector3f;

/**
 * 'Temporary' class that directly handles the keyboard input movement for any object via the user.
 *     The default keys are as follows:
 *     <p>
 *         Forward: KEY_W
 *     </p>
 *
 *     <p>
 *         Backwards: KEY_S
 *     </p>
 *
 *     <p>
 *         Left: KEY_A
 *     </p>
 *
 *     <p>
 *         Right: KEY_D
 *     </p>
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class FreeMove extends GameComponent {
    private float speed;
    private int forwardKey, backKey, leftKey, rightKey;

    /**
     * Constructs and initialises the keyboard input movement for the object with custom sensitivity. Note: this uses
     * default input keys for movement, refer to class documentation.
     *
     * @param speed
     *      The desired movement speed for that object
     */
    public FreeMove(float speed) {
        this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
    }

    /**
     * constructs and initialises the keyboard input movement for the object with custom sensitivity and Key Bindings.
     *
     * @param speed
     *      The desired movement speed for that object
     * @param forwardKey
     *      The desired key binding for the forward key
     * @param backKey
     *      The desired key binding for the backwards key
     * @param leftKey
     *      The desired key binding for the left key
     * @param rightKey
     *      The desired key binding for the right key
     */
    public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey) {
        this.speed = speed;
        this.forwardKey = forwardKey;
        this.backKey = backKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }

    /**
     * Input method that calculates and translates the keyboard input directly on screen. This method is overriding
     * it's parent's public void input method.
     */
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

    /**
     * Translates the objects movement towards a direction with by a specific amount.
     * <p>
     *     This is achieved by adding the current position by the direction multiplied by desired amount.
     * </p>
     *
     * @param dir
     *      Direction you want to move in, this is handled using Vector3f like so:
     *      <p>
     *          Vector3f(float x, float y, float z)
     *      </p>
     *
     *      <p>
     *          Therefore: Vector3f(0, 0, 1) will move the object towards the Z-axis(depth)
     *      </p>
     * @param amt
     *      The amount of distance desired to cover in the specified distance
     */
    public void move(Vector3f dir, float amt) {
        getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
    }
}
