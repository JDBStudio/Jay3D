package org.jay3d.engine.rendering.resources;

import org.jay3d.engine.core.math.Vector3f;

import java.util.HashMap;

/**
 * Created by Juxhin on 01/10/14.
 * Do not distribute without permission
 */
public abstract class MappedValues {
    private HashMap<String, Vector3f> vector3fHashMap;
    private HashMap<String, Float> floatHashMap;

    public MappedValues() {
        this.vector3fHashMap = new HashMap<>();
        this.floatHashMap = new HashMap<>();
    }

    public Vector3f getVector3f(String name){
        Vector3f result = vector3fHashMap.get(name);

        if(result != null)
            return result;

        return new Vector3f(0, 0, 0);
    }
    public Float getFloat(String name){
        Float result = floatHashMap.get(name);

        if(result != null)
            return result;

        return 0f;
    }

    public void addFloat(String name, Float floatValue){
        floatHashMap.put(name, floatValue);
    }

    public void addVector3f(String name, Vector3f vector3f){
        vector3fHashMap.put(name, vector3f);
    }
}
