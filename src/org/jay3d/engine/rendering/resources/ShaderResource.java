package org.jay3d.engine.rendering.resources;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glCreateProgram;

/**
 * @author Juxhin Dyrmishi Brigjaj
 */
public class ShaderResource {
    private int program;
    private int referenceCount;
    private HashMap<String, Integer> uniforms;
    private ArrayList<String> uniformNames;
    private ArrayList<String> uniformTypes;

    public ShaderResource() {
        this.program = glCreateProgram();
        this.referenceCount = 1;
        uniformNames = new ArrayList<>();
        uniformTypes = new ArrayList<>();
        uniforms = new HashMap<>();

        if(program == 0){
            System.err.print("SHADER ERROR: Could not find valid memory location in constructor");
            System.exit(1);
        }
    }

    @SuppressWarnings("FinalizeDoesntCallSuperFinalize")
    @Override
    protected void finalize(){
        glDeleteBuffers(program);
    }

    public void addReference(){
        referenceCount++;
    }

    public boolean removeReference(){
        referenceCount--;
        return referenceCount == 0;
    }

    public int getProgram() {
        return program;
    }

    public HashMap<String, Integer> getUniforms() {
        return uniforms;
    }

    public ArrayList<String> getUniformNames() {
        return uniformNames;
    }

    public ArrayList<String> getUniformTypes() {
        return uniformTypes;
    }
}
