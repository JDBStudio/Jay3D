package org.jay3d.game;

import org.jay3d.engine.core.GameComponent;
import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.rendering.Mesh;
import org.jay3d.engine.rendering.material.Material;
import org.jay3d.engine.rendering.shaders.Shader;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class MeshRenderer implements GameComponent{
    private Mesh mesh;
    private Material material;

    public MeshRenderer(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    @Override
    public void input(Transform transform, float delta){

    }

    @Override
    public void update(Transform transform, float delta){

    }

    @Override
    public void render(Transform transform, Shader shader){
        shader.bind();
        shader.updateUniforms(transform, material);
        mesh.draw();
    }
}
