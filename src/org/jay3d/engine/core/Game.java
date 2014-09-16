package org.jay3d.engine.core;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public abstract class Game {
    private GameObject root;

    public void init(){

    }

    public void input(){
        getRootObject().input();
    }

    public void update(){
        getRootObject().update();
    }

    public GameObject getRootObject() {
        if(root == null)
            root = new GameObject();

        return root;
    }
}
