package org.jay3d.engine.core.components;

import org.jay3d.engine.rendering.Mesh;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.rendering.material.Material;
import org.jay3d.engine.rendering.shaders.Shader;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class MeshRenderer extends GameComponent{
    private Mesh mesh;
    private Material material;

    public MeshRenderer(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    public void render(Shader shader, RenderingEngine renderingEngine){
        shader.bind();
        shader.updateUniforms(getTransform(), material, renderingEngine);
        mesh.draw();
    }
}
