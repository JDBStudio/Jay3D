package org.jay3d.engine.core;

import org.jay3d.engine.core.components.GameComponent;
import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.rendering.shaders.Shader;

import java.util.ArrayList;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class GameObject {
    private ArrayList<GameObject> children;
    private ArrayList<GameComponent> components;
    private Transform transform;

    public GameObject(){
        this.children = new ArrayList<>();
        this.components = new ArrayList<>();
        this.transform = new Transform();
    }

    public void addChild(GameObject object){
        children.add(object);
    }

    public GameObject addComponent(GameComponent component){
        components.add(component);
        component.setParent(this);

        return this;
    }

    public void input(float delta){
        for(GameComponent component : components)
            component.input(delta);

        for(GameObject child : children)
            child.input(delta);
    }

    public void update(float delta){
        for(GameComponent component : components)
            component.update(delta);

        for(GameObject child : children)
            child.update(delta);
    }

    public void render(Shader shader){
        for(GameComponent component : components)
            component.render(shader);

        for(GameObject child : children)
            child.render(shader);
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine){
        for(GameComponent component : components)
            component.addToRenderingEngine(renderingEngine);

        for(GameObject child : children)
            child.addToRenderingEngine(renderingEngine);
    }

    public Transform getTransform(){
        return transform;
    }
}
