package org.jay3d.engine.core.components;

import org.jay3d.engine.core.Input;
import org.jay3d.engine.core.math.Vector2f;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.Window;

/**
 * 'Temporary' class that handles the object's mouse movement that is controlled by the user.
 *
 * <p>
 *     The default key to unlock mouse from screen(after already clicking on screen) is the KEY_ESCAPE(0x01) under
 *     Input class.
 * </p>
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class FreeLook extends GameComponent{
    public static final Vector3f Y_AXIS = new Vector3f(0,1,0);

    boolean mouseLocked = false;
    private float sensitivity;
    private int unlockMouseKey;
    Vector2f centerPosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);

    /**
     * Constructs and initialises the mouse movement with custom sensitivity.
     *
     * <p>
     *     The default key to unlock mouse from screen(after already clicking on screen) is the KEY_ESCAPE(0x01) under
     *     Input class.
     * </p>
     *
     * @param sensitivity
     *      The mouse sensitivity
     *
     */
    public FreeLook(float sensitivity) {
        this(sensitivity, Input.KEY_ESCAPE);
    }

    /**
     * Constructs and initialises the mouse movement with custom sensitivity and custom escape key.
     *
     * <p>
     *     The default key to unlock mouse from screen(after already clicking on screen) is the KEY_ESCAPE(0x01) under
     *     Input class. Therefore it is recommended you use any int value other than 0x01.
     * </p>
     *
     * @param sensitivity
     *      The mouse sensitivity
     * @param unlockMouseKey
     *      Custom Input key in order to unlock mouse from the screen
     */
    public FreeLook(float sensitivity, int unlockMouseKey) {
        this.sensitivity = sensitivity;
        this.unlockMouseKey = unlockMouseKey;
    }

    /**
     * Input method that calculates and translates the mouse movement directly on screen. This method is overriding
     * it's parent's public void input method.
     */
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
