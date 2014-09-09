package org.jay3d.engine.render;

import static org.lwjgl.opengl.GL11.*;
/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Texture {
    private int id;

    public Texture(int id){
        this.id = id;
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getId(){
        return id;
    }
}
