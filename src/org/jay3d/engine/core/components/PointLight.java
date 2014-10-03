package org.jay3d.engine.core.components;

import org.jay3d.engine.core.math.Attenuation;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.Shader;

/**
 * PointLight is a child class of <code>BaseLight</code>, which uses attenuation on top of colour and intensity. As there isn't a
 * convenience class for colour as of yet, <code>Vector3f</code> is being used instead for the meantime.
 *
 * <code>Vector3f(float x, float y, float z)</code>
 * <p>
 *     BaseLight's colour like so:
 *      Colour(RBG)
 *      float x = R
 *      float y = G
 *      float z = B
 * </p>
 * <p>
 *     Example:
 *              <code>PointLight(new Vector3f(1, 1, 1), 1.0f, new Attenuation(0, 0, 1)</code>
 *      Returns a PointLight that is white with an intensity of 1.0f and Attenuation with constant and linear values
 *      0 but exponential value of 1.
 * </p>
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class PointLight extends BaseLight {
    private static final int COLOUR_DEPTH = 256;
    private Attenuation attenuation;
    private float range;

    /**
     * Constructs and initialises a PointLight specified colour, intensity and attenuation. Within the constructor,
     * the object's range is calculated and has it's shader is automatically set to Forward-point.vs and Forward-point.fs.
     * Therefore requires the shaders in order to work.
     *
     * @param colour
     *      The Point Light's colour (RGB)
     * @param intensity
     *      The Point Light's intensity
     * @param attenuation
     *      The Attenuation(float constant, float linear, float exponent)
     */
    public PointLight(Vector3f colour, float intensity, Attenuation attenuation) {
        super(colour, intensity);
        this.attenuation = attenuation;

        float a = attenuation.getExponent();
        float b = attenuation.getLinear();
        float c = attenuation.getConstant() - COLOUR_DEPTH * getIntensity() * getColour().max();

        this.range = (float)(-b + Math.sqrt(b * b - 4 * a * c))/(2 * a);//Quadratic equation
        setShader(new Shader("Forward-point"));
    }

    /**
     * @return
     *      The PointLight's range.
     */
    public float getRange() {
        return range;
    }

    /**
     * @return
     *      The attenuation's constant value.
     */
    public float getConstant() {
        return attenuation.getX();
    }

    /**
     * @return
     *      The attenuation's linear value.
     */
    public float getLinear() {
        return attenuation.getY();
    }

    /**
     * @return
     *      The attenuation's exponential value.
     */
    public float getExponent() {
        return attenuation.getZ();
    }

    /**
     * @return
     *      The PointLight's attenuation
     */
    public Attenuation getAttenuation(){
        return attenuation;
    }

}
