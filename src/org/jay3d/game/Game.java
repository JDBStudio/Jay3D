package org.jay3d.game;

import org.jay3d.engine.Input;
import org.jay3d.engine.math.Vector3f;
import org.jay3d.engine.render.Mesh;
import org.jay3d.engine.render.ResourceLoader;
import org.jay3d.engine.render.Shader;
import org.jay3d.engine.render.Vertex;
import org.jay3d.util.Time;
import org.lwjgl.input.Keyboard;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Game {
    private Mesh mesh;
    private Shader shader;
    public Game(){
        shader = new Shader();
        mesh = new Mesh();

        Vertex[] data = new Vertex[]{ new Vertex(new Vector3f(-1, -1, 0)),
                                      new Vertex(new Vector3f(0, 1, 0)),
                                      new Vertex(new Vector3f(1, -1, 0)),
                                      };

        mesh.addVertices(data);

        shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
        shader.compileShader();

        shader.addUniform("uniformFloat");
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

    float temp = 0.0f;

    public void update(){
        temp += 0.00001;

        shader.setUniformf("uniformFloat", temp);
    }

    public void render(){
        shader.bind();
        mesh.draw();
    }
}
