package org.jay3d.engine.core.components;

import org.jay3d.engine.core.math.Attenuation;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.Shader;

/**
 * SpotLight is a child class of <code>PointLight</code>, which uses cutoff on top of colour, attenuation and intensity. As there
 * isn't a convenience class for colour as of yet, <code>Vector3f</code> is being used instead for the meantime.
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
 *              <code>new SpotLight(new Vector3f(0,1,1), 0.4f, new Attenuation(0,0,0.1f), 0.7f)</code>
 *      Returns a SpotLight With RGB(0, 1, 1) intensity of 0.4f and Attenuation with constant and linear values
 *      0 but exponential value of 0.1f and cutoff value of 0.7f.
 * </p>
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class SpotLight extends PointLight{
    private PointLight pointLight;
    private float cutoff;

    /**
     * Constructs and initialises the SpotLight specified colour, intensity, cutoff and attenuation. It's shader is
     * automatically set to Forward-spot.vs and Forward-spot.fs.Therefore requires the shaders in order to work.
     *
     * @param colour
     *      The SpotLight's colour(RGB) Example: new Vector(1, 1, 1) for white colour
     * @param intensity
     *      The SpotLight's intensity
     * @param attenuation
     *      The SpotLight's attenuation Example: new Attenuation(0, 0, 1)
     *      <p>
     *          Attenuation is composed of a constant, a linear and an exponent, like so: <code>Attenuation(float constant,
     *          float linear, floar exponent)</code>
     *      </p>
     * @param cutoff
     *      The SpotLight's cutoff point
     */
    public SpotLight(Vector3f colour,
                     float intensity,
                     Attenuation attenuation,
                     float cutoff) {
        super(colour, intensity, attenuation);
        this.cutoff = cutoff;

        setShader(new Shader("Forward-spot"));
    }

    public PointLight getPointLight() {
        return pointLight;
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    public Vector3f getDirection() {
        return getTransform().getTransformedRot().getForward();
    }

    public float getCutoff() {
        return cutoff;
    }

    public void setCutoff(float cutoff) {
        this.cutoff = cutoff;
    }
}
