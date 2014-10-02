package org.jay3d.engine.core.components;

import org.jay3d.engine.core.CoreEngine;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.Shader;

/**
 * @author Juxhin Dyrmishi Brigjaj
 */
public class BaseLight extends GameComponent {
    private Vector3f colour;
    private float intensity;
    private Shader shader;

    /**
     * As there isn't a convenience class of yet, Vector3f is used in order
     * to determine the colour of the light. Whilst Vector3f has parameters
     * Vector3f(float x, float y, float z), the values are represented for the.
     * <p>
     *     BaseLight's colour like so:
     *      Colour(RBG)
     *      float x = R
     *      float y = G
     *      float z = B
     * </p>
     * <p>
     *     Example:
     *              BaseLight(new Vector3f(1, 1, 1), 1.0f)
     *      Returns a BaseLight that is white with an intensity of 1.0f
     * </p>
     * <p>
     *              BaseLight(new Vector3f(1, 0, 0), 0.5f)
     *      Returns a BaseLight that is red with an intensity of 0.5f
     * </p>
     * @param colour
     *          The desired colour for the Base Light(RPG).
     * @param intensity
     *          The intensity of the light.
     */
    public BaseLight(Vector3f colour, float intensity){
        this.colour = colour;
        this.intensity = intensity;
    }

    /**
     * Convenience method that is overidden from it's parent class. Allows us
     * to easily add a BaseLight to our engine list of lights.
     *
     * @param engine
     *      The Core Engine in order to add the light(Not Rendering Engine!).
     */
    @Override
    public void addToEngine(CoreEngine engine) {
        engine.getRenderingEngine().addLight(this);
    }

    /**
     * @return
     *      The intensity of the Base Light.
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * Recommended to keep between greater than 0.0f and smaller than 1.0f
     * to avoid any weird behavior. However putting it higher should not
     * actually cause any harm. Set it above 1.0f only if you really need to.
     *
     * @param intensity
     *      The intensity of the Base Light.
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    /**
     * @return
     *      The colour of the Base Light(RGB).
     */
    public Vector3f getColour() {
        return colour;
    }

    /**
     * As there isn't a convenience class of yet, Vector3f is used in order
     * to determine the colour of the light. Whilst Vector3f has parameters
     * Vector3f(float x, float y, float z), the values are represented for the
     * <p>
     *     BaseLight's colour like so:
     *      Colour(RBG)
     *      float x = R
     *      float y = G
     *      float z = B
     * </p>
     * <p>
     *     Example:
     *              BaseLight(new Vector3f(1, 1, 1), 1.0f)
     *      Returns a BaseLight that is white with an intensity of 1.0f
     * </p>
     * <p>
     *              BaseLight(new Vector3f(1, 0, 0), 0.5f)
     *      Returns a BaseLight that is red with an intensity of 0.5f
     * </p>
     * @param colour
     *          The desired colour for the Base Light(RPG).
     */
    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    /**
     * @param shader
     *      Set's the Base Light's shader.
     */
    public void setShader(Shader shader){
        this.shader = shader;
    }

    /**
     * @return
     *      The Base Light's shader.
     */
    public Shader getShader() {
        return shader;
    }
}
