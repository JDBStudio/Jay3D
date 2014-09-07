package org.jay3d.engine.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class RenderUtil {
    public static void clearScreen(){

        //TODO: Stencil Buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void initGraphics(){
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        //TODO: Depth clamp

        glEnable(GL_FRAMEBUFFER_SRGB);
    }
}