package org.jay3d.game;

import org.jay3d.engine.Input;
import org.jay3d.engine.math.Vector3f;
import org.jay3d.engine.render.*;
import org.jay3d.util.Time;
import org.lwjgl.input.Keyboard;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Game {
    private Mesh mesh;
    private Shader shader;
    private Transform transform;
    public Game(){
        shader = new Shader();
        mesh = new Mesh();

        Vertex[] vertices = new Vertex[]{ new Vertex(new Vector3f(-1, -1, 0)),
                                      new Vertex(new Vector3f(0, 1, 0)),
                                      new Vertex(new Vector3f(1, -1, 0)),
                                      new Vertex(new Vector3f(0, -1, 1))};

        int[] indices = new int[]{0, 1, 3,
                                  3, 1, 2,
                                  2, 1, 0,
                                  0, 2, 3};

        mesh.addVertices(vertices, indices);

        transform = new Transform();

        shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
        shader.compileShader();

        shader.addUniform("transform");
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
        temp += 0.00009;

        float sinTemp = (float)Math.sin(temp);

        transform.setTranslation(sinTemp, 0, 0);
        transform.setRotation(0, sinTemp * 180, 0);
        //transform.setScale(sinTemp, sinTemp, sinTemp);
    }

    public void render(){
        shader.bind();
        shader.setUniform("transform", transform.getTransformation());
        mesh.draw();
    }
}
