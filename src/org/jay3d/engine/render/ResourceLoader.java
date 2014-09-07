package org.jay3d.engine.render;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class ResourceLoader {
    public static String loadShader(String fileName){
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader reader;

        try{
            reader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
            String line;
            while((line = reader.readLine()) != null){
                shaderSource.append(line).append("\n");
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }

        return shaderSource.toString();
    }
}
