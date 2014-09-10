package org.jay3d.engine.render.material;

import org.jay3d.engine.math.Vector3f;
import org.jay3d.engine.render.Texture;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Material {
    private Texture texture;
    private Vector3f colour;

    public Material(Texture texture, Vector3f colour) {
        this.texture = texture;
        this.colour = colour;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
}
