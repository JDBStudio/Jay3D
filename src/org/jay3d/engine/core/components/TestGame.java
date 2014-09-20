package org.jay3d.engine.core.components;

import org.jay3d.engine.core.Game;
import org.jay3d.engine.core.GameObject;
import org.jay3d.engine.core.math.Vector2f;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.*;
import org.jay3d.engine.rendering.material.Material;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class TestGame extends Game {

    public void init() {
        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;

        Vertex[] vertices = new Vertex[]{new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

        int indices[] = {0, 1, 2,
                2, 1, 3};

        Mesh mesh = new Mesh(vertices, indices, true);
        Material material = new Material(new Texture("test.png"), new Vector3f(1, 1, 1), 1, 8);

        MeshRenderer meshRenderer = new MeshRenderer(mesh, material);

        GameObject planeObject = new GameObject();
        planeObject.addComponent(meshRenderer);
        planeObject.getTransform().setTranslation(0, -1, 5);

        GameObject directionalLightObject = new GameObject();
        DirectionalLight directionalLight = new DirectionalLight(new Vector3f(0, 0, 1), 0.4f, new Vector3f(1, 1, 1));
        directionalLightObject.addComponent(directionalLight);

        GameObject pointLightObject = new GameObject();
        PointLight pointLight = new PointLight(new Vector3f(0, 1, 0), 0.5f, 0, 0, 1, new Vector3f(5, 0, 5), 100);
        pointLightObject.addComponent(pointLight);

        SpotLight spotLight = new SpotLight(new Vector3f(0,1,1), 0.4f,
                0,0,0.1f,
                new Vector3f(5,0,5), 100,
                new Vector3f(1,0,0), 0.7f);


        GameObject spotLightObject = new GameObject();
        spotLightObject.addComponent(spotLight);

        getRootObject().addChild(planeObject);
        getRootObject().addChild(directionalLightObject);
        getRootObject().addChild(pointLightObject);
        getRootObject().addChild(spotLightObject);
    }
}
