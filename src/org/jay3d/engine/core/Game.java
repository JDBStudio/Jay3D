package org.jay3d.engine.core;

import org.jay3d.engine.rendering.RenderingEngine;

/**
 * The Game class has to be linked to a Core Engine in order to function. This is where part of the scenegraph
 * initialisation and implementation takes place.
 *
 * @author Juxhin Dyrmishi Brigjaj
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

    /**
     * When objects are placed within the scene graph via the Game class, they must be linked to a Rendering engine.
     * The rendering engine then renders the root object.
     *
     * @param renderingEngine
     *      The Rendering Engine to render the objects.
     */
    public void render(RenderingEngine renderingEngine){
        renderingEngine.render(getRootObject());
    }

    /**
     * @return
     *  The <code>Game</code> classes' root <code>GameObject</code>
     */
    private GameObject getRootObject() {
        if(root == null)
            root = new GameObject();

        return root;
    }

    /**
     * When objects are placed within the scene graph via the Game class, they must also be linked to a Core Engine.
     *
     * @param engine
     *      The Core Engine to link the Game with.
     */
    public void setEngine(CoreEngine engine){
        getRootObject().setEngine(engine);
    }

    /**
     * @param object
     *      <code>GameObject</code> to each to the object hierarchy.
     */
    public void addObject(GameObject object){
        getRootObject().addChild(object);
    }
}
