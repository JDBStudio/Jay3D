package org.jay3d.engine.rendering.resources;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

/**
 * Created by Juxhin on 29/09/14.
 * Do not distribute without permission
 */
public class TextureResource {
    private int id;
    private int referenceCount;

    public TextureResource(){
        this.id = glGenTextures();
        this.referenceCount = 1;
    }

    @SuppressWarnings("FinalizeDoesntCallSuperFinalize")
    @Override
    protected void finalize(){
        glDeleteBuffers(id);
    }

    public void addReference(){
        referenceCount++;
    }

    public boolean removeReference(){
        referenceCount--;
        return referenceCount == 0;
    }

    public int getId() {
        return id;
    }
}
