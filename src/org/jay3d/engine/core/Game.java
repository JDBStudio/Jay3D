package org.jay3d.engine.core;

import org.jay3d.engine.rendering.RenderingEngine;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public abstract class Game {
    private GameObject root;

    public void init(){

    }

    public void input(float delta){
        getRootObject().inputAll(delta);
    }

    public void update(float delta){
        getRootObject().updateAll(delta);
    }

    public void render(RenderingEngine renderingEngine){
        renderingEngine.render(getRootObject());
    }

    private GameObject getRootObject() {
        if(root == null)
            root = new GameObject();

        return root;
    }

    public void setEngine(CoreEngine engine){
        getRootObject().setEngine(engine);
    }

    public void addObject(GameObject object){
        getRootObject().addChild(object);
    }
}
