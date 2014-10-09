package org.jay3d.engine.rendering;

import org.jay3d.engine.rendering.resources.MappedValues;

import java.util.HashMap;

/**
 * @author Juxhin Dyrmishi Brigjaj
 */
public class Material extends MappedValues{

    private HashMap<String, Texture> textureHashMap;

    public Material() {
        super();
        textureHashMap = new HashMap<>();
    }

    public void addTexture(String name, Texture texture){
        textureHashMap.put(name, texture);
    }

    public Texture getTexture(String name){
        Texture result = textureHashMap.get(name);

        if(result != null)
            return result;

        return new Texture("test.png");
    }
}
