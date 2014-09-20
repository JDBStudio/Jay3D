package org.jay3d.engine.core;

import org.jay3d.engine.core.components.DirectionalLight;
import org.jay3d.engine.core.components.PointLight;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.*;
import org.jay3d.engine.rendering.Window;
import org.jay3d.engine.rendering.light.*;
import org.jay3d.engine.rendering.shaders.*;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class RenderingEngine {
    private Camera mainCamera;
    private Vector3f ambientLight;
    private DirectionalLight activeDirectionalLight;
    private PointLight activePointLight;
    private SpotLight spotLight;

    //Structures(Permanent)
    private ArrayList<DirectionalLight> directionalLights;
    private ArrayList<PointLight> pointLights;

    public RenderingEngine() {
        directionalLights = new ArrayList<>();
        pointLights = new ArrayList<>();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);

        mainCamera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000f);

        ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
        activeDirectionalLight = new DirectionalLight(new BaseLight(new Vector3f(0, 0, 1), 0.4f), new Vector3f(1, 1, 1));
        activePointLight = new PointLight(new BaseLight(new Vector3f(0, 1, 0), 0.5f), new Attenuation(0, 0, 1), new Vector3f(5, 0, 5), 100);
        spotLight = new SpotLight(new PointLight(new BaseLight(new Vector3f(0, 4, 1), 0.8f),
                new Attenuation(0, 0, 1f),
                new Vector3f(1, 0, 1), 100),
                new Vector3f(1, 0, 0), 0.7f);
    }

    public void input(float delta){
        mainCamera.input(delta);
    }

    public void render(GameObject object){
        clearScreen();

        clearLights();
        object.addToRenderingEngine(this);

        Shader forwardAmbient = ForwardAmbient.getInstance();
        Shader forwardDirectional = ForwardDirectional.getInstance();
        Shader forwardPoint = ForwardPoint.getInstance();
        Shader forwardSpot = ForwardSpotlight.getInstance();

        forwardAmbient.setRenderingEngine(this);
        forwardDirectional.setRenderingEngine(this);
        forwardPoint.setRenderingEngine(this);
        forwardSpot.setRenderingEngine(this);

        object.render(forwardAmbient);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        for(DirectionalLight d : directionalLights){
            activeDirectionalLight = d;
            object.render(forwardDirectional);
        }

        for(PointLight p : pointLights){
            activePointLight = p;
            object.render(forwardPoint);
        }

        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    public Vector3f getAmbientLight() {
        return ambientLight;
    }

    public DirectionalLight getActiveDirectionalLight(){
        return activeDirectionalLight;
    }

    public SpotLight getSpotLight() {
        return spotLight;
    }

    public PointLight getActivePointLight() {
        return activePointLight;
    }

    public void addDirectionalLight(DirectionalLight directionalLight){
        directionalLights.add(directionalLight);
    }
    public void addPointLight(PointLight pointLight){
        pointLights.add(pointLight);
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

    private static void setClearColor(Vector3f color){
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

    private static void unbindTextures(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private void clearLights(){
        directionalLights.clear();
        pointLights.clear();
    }
}
