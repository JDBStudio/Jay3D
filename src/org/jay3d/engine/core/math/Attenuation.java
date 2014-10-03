package org.jay3d.engine.core.math;

/**
 * The attenuation value is the gradual loss of intensity
 * through the 3D space. Whilst travelling from it's original source, it usually diminishes in intensity, which is why
 * it is often called, "light attenuation".
 *
 * <p>
 *  The real-attenuation tends to be 100% quadratic, however this easily easily interpreted in virtual environments such
 *  as the Rendering Engine due to the number of calculations required. By blending the three values that constitutes
 *  the lights attenuation you are able to set the proportions of each type as you feel like.
 * </p>
 * <p>
 *     Therefore an attenuation ratio of 3:6:1 will yield the same result as 6:12:2 or 30:60:10.
 * </p>
 *
 * The class itself is simply a convenience class that changes Vector3f into attenuation, to replace:
 * <p>
 *     <code>Vector3f(float x, float y, float z)</code> which are the axis with
 * </p>
 *
 * <p>
 *     Attenuation(float constant, float linear, float exponent)
 *     <p>
 *         Constant the the amount of strength that the light scatters
 *     </p>
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class Attenuation extends Vector3f{

    /**
     * Constructs and initialises the attenuation for the given light object.
     *
     * @param constant
     *      The amount of strength that that the light scatters
     * @param linear
     *      The rate that the light dimishes as it travels from it's source
     * @param exponent
     *      Not to be confused with <code>Linear</code>, this means that the further
     *      the light travels from it source, the 'more' it will dimishes. This creates
     *      a sharp drop in light to compensate for fast decline
     */
    public Attenuation(float constant, float linear, float exponent) {
        super(constant, linear, exponent);
    }

    /**
     * The amount of strength that that the light scatters
     * @return
     *      The Attenuation's constant value
     */
    public float getConstant(){
        return getX();
    }

    /**
     * The rate that the light dimishes as it travels from it's source
     * @return
     *      The Attenuation's linear value
     */
    public float getLinear(){
        return getY();
    }

    /**
     * Not to be confused with <code>Linear</code>, this means that the further
     * the light travels from it source, the 'more' it will dimishes. This creates
     * a sharp drop in light to compensate for fast decline
     * @return
     *      The Attenuation's exponential value
     */
    public float getExponent(){
        return getZ();
    }
}
