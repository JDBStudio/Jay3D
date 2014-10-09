package org.jay3d.engine.core;

import org.jay3d.engine.core.components.GameComponent;
import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.rendering.Shader;

import java.util.ArrayList;

/**
 * GameObject class uses multiple classes including <code>Transform, Shader, GameComponent</code>. It is then added to
 * a game class that is linked to both a Rendering Engine and Core Engine to render the objects and make sure everything
 * is placed in a proper parent-child hierarchy.
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class GameObject {
    private ArrayList<GameObject> children;
    private ArrayList<GameComponent> components;
    private Transform transform;
    private CoreEngine engine;

    /**
     * Constructs and initialises a GameObject with a list of children, components and a transform.
     */
    public GameObject(){
        this.children = new ArrayList<>();
        this.components = new ArrayList<>();
        this.transform = new Transform();
        engine = null;
    }

    /**
     * @param child
     *      Child <code>GameObject</code> to add to this current GameObject.
     */
    public void addChild(GameObject child){
        children.add(child);
        child.setEngine(engine);
        child.getTransform().setParent(transform);
    }

    /**
     * @param component
     *      The type of component to add to this particular GameObject.
     *      <p>
     *          For example, you could add a <code>LookAtComponent</code> to the GameObject to follow a particular points
     *          using <code>nlerp</code> or <code>slerp</code>.
     *      </p>
     * @return
     *      Current <code>GameObject</code> with new Child object added to it's children list.
     */
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

    /**
     * Used in order to get the ArrayList of child objects attached to this GameObject. This will only return a list
     * of it's children and not parents.
     *
     * @return
     *      ArrayList of child objects.
     */
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

    /**
     * If you wish to use a specific <code>CoreEngine</code> for this specific <code>GameObject</code> you may do so.
     * The method automatically adds all child components and objects to the same Engine that it's parent is now set to.
     */
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
