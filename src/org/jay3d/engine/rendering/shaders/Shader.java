package org.jay3d.engine.rendering.shaders;

import org.jay3d.engine.core.components.BaseLight;
import org.jay3d.engine.core.components.DirectionalLight;
import org.jay3d.engine.core.components.PointLight;
import org.jay3d.engine.core.components.SpotLight;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.core.math.Matrix4f;
import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.material.Material;
import org.jay3d.util.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Shader {
    private int program;
    private HashMap<String, Integer> uniforms;
    private ArrayList<String> uniformNames;
    private ArrayList<String> uniformTypes;

    public Shader(String fileName){
        program = glCreateProgram();
        uniforms = new HashMap<>();

        uniformTypes = new ArrayList<>();
        uniformNames = new ArrayList<>();

        if(program == 0){
            System.err.print("SHADER ERROR: Could not find valid memory location in constructor");
            System.exit(1);
        }

        String vertexShaderText = loadShader(fileName + ".vs");
        String fragmentShaderText = loadShader(fileName + ".fs");

        addAllAttirbutes(vertexShaderText);

        addVertexShader(vertexShaderText);
        addFragmentShader(fragmentShaderText);

        compileShader();

        addAllUniforms(vertexShaderText);
        addAllUniforms(fragmentShaderText);
    }

    public void setUniformi(String uniformName, int value){
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniformf(String uniformName, float value){
        glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Vector3f value){
        glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
    }

    public void setUniform(String uniformName, Matrix4f value){
        glUniformMatrix4(uniforms.get(uniformName), true, Util.createFlippedBuffer(value));
    }

    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine){
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f MVPMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);

        for(int i = 0; i < uniformNames.size(); i++){
            String uniformName = uniformNames.get(i);
            String uniformType = uniformTypes.get(i);
            String unprefix = uniformName.substring(2);

            if(uniformType.equals("sampler2D")) {
                int samplerSlot = renderingEngine.getSamplerSlot(uniformName);
                material.getTexture(uniformName).bind(samplerSlot);
                setUniformi(uniformName, samplerSlot);
            }else if(uniformName.startsWith("T_")){
                switch (uniformName) {
                    case "T_MVP":
                        setUniform(uniformName, MVPMatrix);
                        break;
                    case "T_model":
                        setUniform(uniformName, worldMatrix);
                        break;
                    default:
                        throw new IllegalArgumentException(uniformName + " is not a valid Transform");
                }
            }else if(uniformName.startsWith("R_")){
                switch (uniformType) {
                    case "vec3":
                        setUniform(uniformName, renderingEngine.getVector3f(unprefix));
                        break;
                    case "float":
                        setUniformf(uniformName, renderingEngine.getFloat(unprefix));
                        break;
                    case "DirectionalLight":
                        setUniformDirectionalLight(uniformName, (DirectionalLight) renderingEngine.getActiveLight());
                        break;
                    case "SpotLight":
                        setUniformSpotLight(uniformName, (SpotLight) renderingEngine.getActiveLight());
                        break;
                    case "PointLight":
                        setUniformPointLight(uniformName, (PointLight) renderingEngine.getActiveLight());
                        break;
                    default:
                        renderingEngine.updateUniformStruct(transform, material, this, uniformName, uniformType);
                }
            }else if(uniformName.startsWith("C_")){
                    if(uniformName.equals("C_eyePos"))
                        setUniform(uniformName, renderingEngine.getMainCamera().getTransform().getTranformedPos());
                    else
                        throw new IllegalArgumentException(uniformName + " is not a valid component of Camera");
            }else{
                switch (uniformType) {
                    case "vec3":
                        setUniform(uniformName, material.getVector3f(uniformName));
                        break;
                    case "float":
                        setUniformf(uniformName, material.getFloat(uniformName));
                        break;
                    default:
                        throw new IllegalArgumentException(uniformType + " is not a supported type in Rendering Engine");
                }
            }
        }
    }

    public void bind(){
        glUseProgram(program);
    }

    private static String loadShader(String fileName){
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader reader;
        final String INCLUDE_DIRECTIVE = "#include";

        try{
            reader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
            String line;

            while((line = reader.readLine()) != null){
                if(line.startsWith(INCLUDE_DIRECTIVE)){

                    /*
                     * Example:
                     *      #include "file.h"
                     *      line.substring will return file.h
                     */
                    shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length() - 1)));
                }else
                    shaderSource.append(line).append("\n");
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }

        return shaderSource.toString();
    }

    private void addAllUniforms(String shaderText){
        HashMap<String, ArrayList<GLSLStruct>> structs = findUniformStructs(shaderText);

        final String UNIFORM_KEY = "uniform";
        int uniformStartLocation = shaderText.indexOf(UNIFORM_KEY);

        while(uniformStartLocation != -1){
            if(!(uniformStartLocation != 0
                    && (Character.isWhitespace(shaderText.charAt(uniformStartLocation - 1)) || shaderText.charAt(uniformStartLocation - 1) == ';'))
                    && Character.isWhitespace(shaderText.charAt(uniformStartLocation + UNIFORM_KEY.length())))
                continue;

            int begin = uniformStartLocation + UNIFORM_KEY.length() + 1;
            int end = shaderText.indexOf(";", begin);

            String uniformLine = shaderText.substring(begin, end);

            int whiteSpacePos = uniformLine.indexOf(' ');
            String uniformName = uniformLine.substring(uniformLine.indexOf(' ') + 1, uniformLine.length());
            String uniformType = uniformLine.substring(0, whiteSpacePos);

            uniformNames.add(uniformName);
            uniformTypes.add(uniformType);
            addUniform(uniformName, uniformType, structs);

            uniformStartLocation = shaderText.indexOf(UNIFORM_KEY, uniformStartLocation + UNIFORM_KEY.length());
        }
    }

    private void addAllAttirbutes(String shaderText){
        final String ATTRIBUTE_KEY = "attribute";
        int attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEY);
        int attributeNumber = 0;

        while(attributeStartLocation != -1){
            if(!(attributeStartLocation != 0
                    && (Character.isWhitespace(shaderText.charAt(attributeStartLocation - 1)) || shaderText.charAt(attributeStartLocation - 1) == ';'))
                    && Character.isWhitespace(shaderText.charAt(attributeStartLocation + ATTRIBUTE_KEY.length())))
                continue;
            int begin = attributeStartLocation + ATTRIBUTE_KEY.length() + 1;
            int end = shaderText.indexOf(";", begin);

            String attributeLine = shaderText.substring(begin, end).trim();
            String attributeName = attributeLine.substring(attributeLine.indexOf(' ') + 1, attributeLine.length()).trim();

            setAttribLocation(attributeName, attributeNumber);
            attributeNumber++;

            attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEY, attributeStartLocation + ATTRIBUTE_KEY.length());
        }
    }

    private HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(String shaderText) {
        HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<>();
        final String STRUCT_KEYWORD = "struct";
        int structStartLocation = shaderText.indexOf(STRUCT_KEYWORD);

        while(structStartLocation != -1) {

            int nameBegin = structStartLocation + STRUCT_KEYWORD.length() + 1;
            int braceBegin = shaderText.indexOf("{", nameBegin);
            int braceEnd = shaderText.indexOf("}", braceBegin);

            String structName = shaderText.substring(nameBegin, braceBegin).trim();
            ArrayList<GLSLStruct> glslStructs = new ArrayList<>();

            int componentSemicolonPos = shaderText.indexOf(";", braceBegin);
            while(componentSemicolonPos != -1 && componentSemicolonPos < braceEnd) {
                int componentNameStart = componentSemicolonPos;

                while(!Character.isWhitespace(shaderText.charAt(componentNameStart - 1)))
                    componentNameStart--;

                int componentTypeEnd = componentNameStart - 1;

                int componentTypeStart = componentTypeEnd;

                while(!Character.isWhitespace(shaderText.charAt(componentTypeStart - 1)))
                    componentTypeStart--;

                String componentName = shaderText.substring(componentNameStart, componentSemicolonPos);
                String componentType = shaderText.substring(componentTypeStart, componentTypeEnd);

                GLSLStruct glslStruct = new GLSLStruct();
                glslStruct.name = componentName;
                glslStruct.type = componentType;
                glslStructs.add(glslStruct);

                componentSemicolonPos = shaderText.indexOf(";", componentSemicolonPos + 1);
            }
            result.put(structName, glslStructs);
            
            structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
        }
        return result;
    }

    private void addUniform(String uniformName, String uniformType, HashMap<String, ArrayList<GLSLStruct>> structs){

        boolean addThis = true;
        ArrayList<GLSLStruct> structComponents = structs.get(uniformType);

        if(structComponents != null){
            addThis = false;

            for(GLSLStruct struct : structComponents){
                addUniform(uniformName + "." + struct.name, struct.type, structs);
            }
        }

        if(!addThis)
            return;

        int uniformLocation = glGetUniformLocation(program, uniformName);
        if(uniformLocation == 0xFFFFFFFF){
            System.err.println("ERROR: Could not find uniform: " + uniformName);
            new Exception().printStackTrace();
            System.exit(4);
        }

        uniforms.put(uniformName, uniformLocation);
    }

    private void addProgram(String text, int type){
        int shader = glCreateShader(type);

        if(shader == 0){
            System.err.print("SHADER ERROR: Could not find valid memory location when adding shader");
            System.exit(2);
        }

        glShaderSource(shader, text);
        glCompileShader(shader);

        if(glGetShader(shader, GL_COMPILE_STATUS) == 0){
            System.err.print(glGetShaderInfoLog(shader, 1024));
            System.exit(2);
        }

        glAttachShader(program, shader);
    }

    private void addVertexShader(String text){
        addProgram(text, GL_VERTEX_SHADER);
    }

    private void addGeometryShader(String text){
        addProgram(text, GL_GEOMETRY_SHADER);
    }

    public void addFragmentShader(String text){
        addProgram(text, GL_FRAGMENT_SHADER);
    }

    private void setAttribLocation(String attribute, int location){
        glBindAttribLocation(program, location, attribute);
    }

    public void setUniformBaseLight(String uniformName, BaseLight baseLight){
        setUniform(uniformName + ".colour", baseLight.getColour());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    public void setUniformDirectionalLight(String uniformName, DirectionalLight directionalLight){
        setUniformBaseLight(uniformName + ".base", directionalLight);
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }

    private void compileShader(){
        glLinkProgram(program);
        if(glGetProgram(program, GL_LINK_STATUS) == 0){
            System.err.print(glGetShaderInfoLog(program, 1024));
            System.exit(3);
        }
        glValidateProgram(program);

        if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0){
            System.err.print(glGetProgramInfoLog(program, 1024));
            System.exit(3);
        }
    }

    private class GLSLStruct{
        public String name;
        public String type;
    }


    public void setUniformPointLight(String uniformName, PointLight pointLight) {
        setUniformBaseLight(uniformName + ".base", pointLight);
        setUniformf(uniformName + ".atten.constant", pointLight.getConstant());
        setUniformf(uniformName + ".atten.linear", pointLight.getLinear());
        setUniformf(uniformName + ".atten.exponent", pointLight.getExponent());
        setUniform(uniformName + ".position", pointLight.getTransform().getPos());
        setUniformf(uniformName + ".range", pointLight.getRange());
    }

    public void setUniformSpotLight(String uniformName, SpotLight spotLight) {
        setUniformPointLight(uniformName + ".pointLight", spotLight);
        setUniform(uniformName + ".direction", spotLight.getDirection());
        setUniformf(uniformName + ".cutoff", spotLight.getCutoff());
    }

}
