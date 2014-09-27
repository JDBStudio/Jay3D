package org.jay3d.engine.rendering.mesh;

import org.jay3d.engine.core.math.Vector2f;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.util.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Juxhin on 27/09/14.
 * Do not distribute without permission
 */
public class OBJModel {
    private ArrayList<Vector3f> positions;
    private ArrayList<Vector2f> textCoordinates;
    private ArrayList<Vector3f> normals;
    private ArrayList<OBJIndex> indices;
    private boolean hasTexCoords;
    private boolean hasNormals;

    public OBJModel(String fileName) {
        positions = new ArrayList<>();
        textCoordinates = new ArrayList<>();
        normals = new ArrayList<>();
        indices = new ArrayList<>();
        hasTexCoords = false;
        hasNormals = false;

        BufferedReader meshReader;

        try{
            meshReader = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = meshReader.readLine()) != null){
                String[] tokens = line.split(" ");
                tokens = Util.removeEmptyStrings(tokens);
                if(tokens.length == 0 || tokens[0].equalsIgnoreCase("#"))
                    continue;
                else if(tokens[0].equalsIgnoreCase("v")){
                    positions.add(new Vector3f(Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2]),
                            Float.valueOf(tokens[3])));
                }else if(tokens[0].equals("vt")){
                    textCoordinates.add(new Vector2f(Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2])));
                }else if(tokens[0].equals("vn")){
                    normals.add(new Vector3f(Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2]),
                            Float.valueOf(tokens[3])));
                }
                else if(tokens[0].equalsIgnoreCase("f")){
                    for(int i = 0; i < tokens.length - 3; i++) {
                        indices.add(parseOBJIndex(tokens[1]));
                        indices.add(parseOBJIndex(tokens[2 + i]));
                        indices.add(parseOBJIndex(tokens[3 + i]));
                    }
                }
            }
            meshReader.close();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private OBJIndex parseOBJIndex(String token){
        String[] values = token.split("/");

        OBJIndex result = new OBJIndex();
        result.vertexIndex = Integer.parseInt(values[0].split("/")[0]) - 1;

        if(values.length > 1){
            hasTexCoords = true;
            result.texCoordIndex = Integer.parseInt(values[1].split("/")[0]) - 1;

            if(values.length > 2){
                hasNormals = true;
                result.normalIndex = Integer.parseInt(values[2].split("/")[0]) - 1;
            }
        }

        return result;
    }

    public ArrayList<Vector3f> getPositions() {
        return positions;
    }

    public ArrayList<Vector2f> getTextCoordinates() {
        return textCoordinates;
    }

    public ArrayList<Vector3f> getNormals() {
        return normals;
    }

    public ArrayList<OBJIndex> getIndices() {
        return indices;
    }
}
