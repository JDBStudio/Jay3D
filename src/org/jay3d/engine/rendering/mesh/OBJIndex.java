package org.jay3d.engine.rendering.mesh;

/**
 * Created by Juxhin on 27/09/14.
 * Do not distribute without permission
 */
public class OBJIndex {
    public int vertexIndex;
    public int texCoordIndex;
    public int normalIndex;


    @Override
    public boolean equals(Object obj) {
        OBJIndex index = (OBJIndex)obj;

        return vertexIndex == index.vertexIndex
                && texCoordIndex == index.texCoordIndex
                && normalIndex == index.normalIndex;
    }

    @Override
    public int hashCode(){
        final int BASE = 17;
        final int MULTIPLIER = 31;

        int result = BASE;

        result = MULTIPLIER * result + vertexIndex;
        result = MULTIPLIER * result + texCoordIndex;
        result = MULTIPLIER * result + normalIndex;

        return result;
    }
}
