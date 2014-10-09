package org.jay3d.engine.rendering;

import org.jay3d.engine.core.math.Vector2f;
import org.jay3d.engine.core.math.Vector3f;

/**
 * @author Juxhin Dyrmishi Brigjaj
 */
public class Vertex {
    public static final int SIZE = 8;

    private Vector3f pos;
    private Vector2f textCoord;
    private Vector3f normal;

    public Vertex(Vector3f pos) {
        this(pos, new Vector2f(0,0));
    }

    public Vertex(Vector3f pos, Vector2f textCoord) {
        this(pos, textCoord, new Vector3f(0, 0, 0));
    }

    public Vertex(Vector3f pos, Vector2f textCoord, Vector3f normal) {
        this.pos = pos;
        this.textCoord = textCoord;
        this.normal = normal;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector2f getTextCoord() {
        return textCoord;
    }

    public void setTextCoord(Vector2f textCoord) {
        this.textCoord = textCoord;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }
}
