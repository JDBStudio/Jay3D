package org.jay3d.game;

import org.jay3d.engine.Input;
import org.jay3d.engine.math.Vector3f;
import org.jay3d.engine.render.Mesh;
import org.jay3d.engine.render.Vertex;
import org.lwjgl.input.Keyboard;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Game {
    private Mesh mesh;
    public Game(){
        mesh = new Mesh();

        Vertex[] data = new Vertex[]{ new Vertex(new Vector3f(-1, -1, 0)),
                                      new Vertex(new Vector3f(0, 1, 0)),
                                      new Vertex(new Vector3f(1, -1, 0)),
                                      };

        mesh.addVertices(data);
    }

    public void input(){
        if(Input.getKeyDown(Keyboard.KEY_UP))
            System.out.println("KEY_UP: DOWN");
        if(Input.getKeyUp(Keyboard.KEY_UP))
            System.out.println("KEY_UP: UP");

        if(Input.getMouseDown(1))
            System.out.println("RIGHT_CLICK: DOWN" + " POS: " + Input.getMousePosition().toString());
        if(Input.getMouseUp(1))
            System.out.println("RIGHT_CLICK: UP"  + " POS: " + Input.getMousePosition().toString());
    }

    public void update(){

    }

    public void render(){
        mesh.draw();
    }
}
