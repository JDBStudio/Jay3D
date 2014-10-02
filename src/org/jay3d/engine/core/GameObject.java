package org.jay3d.engine.core;

import org.jay3d.engine.core.components.GameComponent;
import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.rendering.Shader;

import java.util.ArrayList;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class GameObject {
    private ArrayList<GameObject> children;
    private ArrayList<GameComponent> components;
    private Transform transform;
    private CoreEngine engine;

    public GameObject(){
        this.children = new ArrayList<>();
        this.components = new ArrayList<>();
        this.transform = new Transform();
        engine = null;
    }

    public void addChild(GameObject child){
        children.add(child);
        child.setEngine(engine);
        child.getTransform().setParent(transform);
    }

    public GameObject addComponent(GameComponent component){
        components.add(component);
        component.setParent(this);

        return this;
    }

    public void inputAll(float delta){
        input(delta);
        for(GameObject child : children)
            child.inputAll(delta);
    }

    public ArrayList<GameObject> getAllAttached() {
        ArrayList<GameObject> result = new ArrayList<>();

        for(GameObject child : children)
            result.addAll(child.getAllAttached());

        result.add(this);
        return result;
    }

    public void updateAll(float delta){
        update(delta);
        for(GameObject child : children)
            child.updateAll(delta);
    }

    public void renderAll(Shader shader, RenderingEngine renderingEngine){
        render(shader, renderingEngine);
        for(GameObject child : children)
            child.renderAll(shader, renderingEngine);
    }

    public void input(float delta) {
        transform.update();

        for(GameComponent component : components)
            component.input(delta);
    }

    public void update(float delta) {
        for(GameComponent component : components)
            component.update(delta);

    }

    public void render(Shader shader, RenderingEngine renderingEngine) {
        for(GameComponent component : components)
            component.render(shader, renderingEngine);

    }

    public void setEngine(CoreEngine engine){
        if(this.engine != engine) {
            this.engine = engine;
            for(GameComponent component : components)
                component.addToEngine(engine);

            for(GameObject child : children)
                child.setEngine(engine);
        }
    }

    public Transform getTransform(){
        return transform;
    }
}
