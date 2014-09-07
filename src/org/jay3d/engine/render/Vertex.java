package org.jay3d.engine.render;

import org.jay3d.engine.math.Vector3f;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Vertex {
    public static final int SIZE = 3;

    private Vector3f pos;

    public Vertex(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }
}
