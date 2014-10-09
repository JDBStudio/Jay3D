package org.jay3d.util;

import org.jay3d.engine.core.math.Matrix4f;
import org.jay3d.engine.rendering.Vertex;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * @author Juxhin Dyrmishi Brigjaj
 */
public class Util {

    public static FloatBuffer createFloatBuffer(int size) {
        return BufferUtils.createFloatBuffer(size);
    }

    public static IntBuffer createIntBuffer(int size) {
        return BufferUtils.createIntBuffer(size);
    }
    public static ByteBuffer createByteBuffer(int size){
        return BufferUtils.createByteBuffer(size);
    }

    public static IntBuffer createFlippedBuffer(int... values) {
        IntBuffer buffer = createIntBuffer(values.length);

        buffer.put(values);
        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);
        for (Vertex vertice : vertices) {
            buffer.put(vertice.getPos().getX());
            buffer.put(vertice.getPos().getY());
            buffer.put(vertice.getPos().getZ());
            buffer.put(vertice.getTextCoord().getX());
            buffer.put(vertice.getTextCoord().getY());
            buffer.put(vertice.getNormal().getX());
            buffer.put(vertice.getNormal().getY());
            buffer.put(vertice.getNormal().getZ());
        }
        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Matrix4f value){
        FloatBuffer buffer = createFloatBuffer(4 * 4);

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                buffer.put(value.get(i, j));
            }
        }

        buffer.flip();
        return buffer;
    }

    public static String[] removeEmptyStrings(String[] tokens){
        ArrayList<String> result = new ArrayList<>();
        for (String token : tokens) {
            if (!token.equalsIgnoreCase("")) {
                result.add(token);
            }
        }
        String[] res = new String[result.size()];
        result.toArray(res);
        return res;
    }

    public static int[] toIntArray(Integer[] integerArray){
        int[] result = new int[integerArray.length];
        for(int i = 0; i < integerArray.length; i++)
            result[i] = integerArray[i];

            return result;
    }
}
