package org.jay3d.engine.rendering.mesh;

import org.jay3d.engine.core.math.Vector2f;
import org.jay3d.engine.core.math.Vector3f;

import java.util.ArrayList;

/**
 * Created by root on 27/09/14.
 */
public class IndexedModel {
    private ArrayList<Vector3f> positions;
    private ArrayList<Vector2f> textCoordinates;
    private ArrayList<Vector3f> normals;
    private ArrayList<Integer> indices;

    public IndexedModel() {
        positions = new ArrayList<>();
        textCoordinates = new ArrayList<>();
        normals = new ArrayList<>();
        indices = new ArrayList<>();
    }

    public ArrayList<Vector3f> getPositions() {
        return positions;
    }

    public ArrayList<Vector2f> getTextCoordinates() {
        return textCoordinates;
    }

    public ArrayList<Vector3f> getNormals() {
        return normals;
    }

    public ArrayList<Integer> getIndices() {
        return indices;
    }
}
