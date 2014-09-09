package org.jay3d.game;

import org.jay3d.engine.Camera;
import org.jay3d.engine.Input;
import org.jay3d.engine.Window;
import org.jay3d.engine.math.Vector2f;
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
    private Camera camera;
    private Texture texture;
    public Game(){
        mesh = new Mesh();//ResourceLoader.loadMesh("box.obj");
        texture = ResourceLoader.loadTexture("test.png");
        shader = new Shader();
        camera = new Camera();
        Vertex[] vertices = new Vertex[] {new Vertex(new Vector3f(-1,-1,0), new Vector2f(0,0)),
                new Vertex(new Vector3f(0,1,0), new Vector2f(0.5f,0)),
                new Vertex(new Vector3f(1,-1,0), new Vector2f(1.0f,0)),
                new Vertex(new Vector3f(0,-1,1), new Vector2f(0.5f,1.0f))};
        int[] indices = new int[] {3,1,0,
                3,1,0,
                2,1,3,
                0,1,2,
                0,2,3};
        mesh.addVertices(vertices, indices);
        Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
        Transform.setCamera(camera);
        transform = new Transform();
        shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
        shader.compileShader();
        shader.addUniform("transform");
    }

    public void input(){
        camera.input();
        /*
        if(Input.getKeyDown(Keyboard.KEY_UP))
            System.out.println("KEY_UP: DOWN");
        if(Input.getKeyUp(Keyboard.KEY_UP))
            System.out.println("KEY_UP: UP");

        if(Input.getMouseDown(1))
            System.out.println("RIGHT_CLICK: DOWN" + " POS: " + Input.getMousePosition().toString());
        if(Input.getMouseUp(1))
            System.out.println("RIGHT_CLICK: UP"  + " POS: " + Input.getMousePosition().toString());
            */
    }

    float temp = 0.0f;

    public void update(){
        temp += Time.getDelta();

        float sinTemp = (float)Math.sin(temp);

        transform.setTranslation(sinTemp, 0, 5);
        transform.setRotation(0, sinTemp * 180, 0);
        //transform.setScale(0.7f * sinTemp, 0.7f * sinTemp, 0.7f * sinTemp);
    }

    public void render(){
        shader.bind();
        shader.setUniform("transform", transform.getProjectedTransformation());
        texture.bind();
        mesh.draw();
    }
}
