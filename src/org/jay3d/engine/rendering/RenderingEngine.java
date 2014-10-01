package org.jay3d.engine.rendering;

import org.jay3d.engine.core.GameObject;
import org.jay3d.engine.core.components.BaseLight;
import org.jay3d.engine.core.components.Camera;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.resources.MappedValues;
import org.jay3d.engine.rendering.shaders.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class RenderingEngine extends MappedValues {
    private Camera mainCamera;
    private Vector3f ambientLight;

    //Fixed structure
    private ArrayList<BaseLight> lights;
    private ArrayList<Camera> cameras;
    private BaseLight activeLight;

    private HashMap<String, Integer> samplerMap;

    public RenderingEngine() {
        super();
        lights = new ArrayList<>();
        cameras = new ArrayList<>();
        samplerMap = new HashMap<>();

        samplerMap.put("diffuse", 0);

        addVector3f("ambient", new Vector3f(0.1f, 0.1f, 0.1f));

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);
    }

    public void render(GameObject object){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        lights.clear();
        object.addToRenderingEngine(this);

        Shader forwardAmbient = ForwardAmbient.getInstance();
        object.render(forwardAmbient, this);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        for(BaseLight b : lights){
            activeLight = b;

            object.render(b.getShader(), this);
        }

        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    public int getSamplerSlot(String samplerName){
        return samplerMap.get(samplerName);
    }

    public void addLight(BaseLight light){
        lights.add(light);
    }

    public void addCamera(Camera camera){
        mainCamera = camera;
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

    public BaseLight getActiveLight(){
        return activeLight;
    }
}
