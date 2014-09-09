package org.jay3d.engine.render;

import org.jay3d.engine.math.Vector2f;
import org.jay3d.engine.math.Vector3f;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Vertex {
    public static final int SIZE = 5;

    private Vector3f pos;
    private Vector2f textCord;

    public Vertex(Vector3f pos) {
        this(pos, new Vector2f(0,0));
    }

    public Vertex(Vector3f pos, Vector2f textCord) {
        this.pos = pos;
        this.textCord = textCord;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector2f getTextCord() {
        return textCord;
    }

    public void setTextCord(Vector2f textCord) {
        this.textCord = textCord;
    }
}
