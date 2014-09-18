package org.jay3d.engine.core;

import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.*;
import org.jay3d.engine.rendering.Window;
import org.jay3d.engine.rendering.light.ForwardAmbient;
import org.jay3d.engine.rendering.shaders.Shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class RenderingEngine {
    private Camera mainCamera;
    private Vector3f ambientLight;

    public RenderingEngine() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);

        mainCamera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000f);

        ambientLight = new Vector3f(0.2f, 0.2f, 0.2f);
    }

    public void input(float delta){
        mainCamera.input(delta);
    }

    public void render(GameObject object){
        clearScreen();

        Shader forwardAmbient = ForwardAmbient.getInstance();
        forwardAmbient.setRenderingEngine(this);

        object.render(forwardAmbient);

        

//        Shader shader = BasicShader.getInstance();
//        shader.setRenderingEngine(this);
//
//        object.render(BasicShader.getInstance());
    }

    private static void clearScreen(){

        //TODO: Stencil Buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private static void setTextures(boolean enabled){
        if(enabled)
            glEnable(GL_TEXTURE_2D);
        else
            glDisable(GL_TEXTURE_2D);
    }

    public Vector3f getAmbientLight() {
        return ambientLight;
    }

    private static void setClearColor(Vector3f color){
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

    private static void unbindTextures(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }

    public Camera getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }
}
