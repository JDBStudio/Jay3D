package org.jay3d.engine.rendering.resources;

import static org.lwjgl.opengl.GL15.*;

/**
 * Created by Juxhin on 28/09/14.
 * Do not distribute without permission
 */
public class MeshResource {
    private int vbo;
    private int ibo;
    private int size;
    private int referenceCount;

    public MeshResource(int size){
        vbo = glGenBuffers();
        ibo = glGenBuffers();
        this.size = size;
        this.referenceCount = 1;
    }

    @SuppressWarnings("FinalizeDoesntCallSuperFinalize")
    @Override
    protected void finalize(){
        glDeleteBuffers(vbo);
        glDeleteBuffers(ibo);
    }

    public void addReference(){
        referenceCount++;
    }

    public boolean removeReference(){
        referenceCount--;
        return referenceCount == 0;
    }

    public int getVbo() {
        return vbo;
    }

    public int getIbo() {
        return ibo;
    }

    public int getSize() {
        return size;
    }
}
