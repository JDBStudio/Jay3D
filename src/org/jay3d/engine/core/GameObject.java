package org.jay3d.engine.core;

import org.jay3d.engine.core.math.Transform;
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

    public void addComponent(GameComponent component){
        components.add(component);
    }

    public void input(){
        for(GameComponent component : components)
            component.input(transform);

        for(GameObject child : children)
            child.input();
    }

    public void update(){
        for(GameComponent component : components)
            component.update(transform);

        for(GameObject child : children)
            child.update();
    }

    public void render(Shader shader){
        for(GameComponent component : components)
            component.render(transform, shader);

        for(GameObject child : children)
            child.render(shader);
    }

    public Transform getTransform(){
        return transform;
    }
}
