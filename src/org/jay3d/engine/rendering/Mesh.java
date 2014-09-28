package org.jay3d.engine.rendering;

import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.mesh.IndexedModel;
import org.jay3d.engine.rendering.mesh.OBJModel;
import org.jay3d.engine.rendering.resources.MeshResource;
import org.jay3d.util.Util;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Mesh {
    private static HashMap<String, MeshResource> loadedModels = new HashMap<>();
    private MeshResource resource;
    private String fileName;

    public Mesh(String fileName) {
        this.fileName = fileName;
        MeshResource oldResource = loadedModels.get(fileName);

        if(oldResource != null){
            resource = oldResource;
            resource.addReference();
        }else{
            loadMesh(fileName);
            loadedModels.put(fileName, resource);
        }
    }

    public Mesh(Vertex[] vertices, int[] indices) {
        this(vertices, indices, false);
        fileName = "";
    }

    public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals){
        fileName = "";
        resource = new MeshResource(indices.length);
        addVertices(vertices, indices, calcNormals);
    }

    @SuppressWarnings("FinalizeDoesntCallSuperFinalize")
    @Override
    protected void finalize(){
        if(resource.removeReference()
                && !fileName.isEmpty()){
            loadedModels.remove(fileName);
        }
    }

    private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals){
        if(calcNormals){
            calcNormals(vertices, indices);
        }

        resource = new MeshResource(indices.length);

        glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
        glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
    }

    public void draw(){
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());

        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
        glDrawElements(GL_TRIANGLES, resource.getSize(), GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
    }

    private void calcNormals(Vertex[] vertices, int[] indices) {
        for(int i = 0; i < indices.length; i += 3){
            int i0 = indices[i];
            int i1 = indices[i+1];
            int i2 = indices[i+2];

            Vector3f v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
            Vector3f v2 = vertices[i2].getPos().sub(vertices[i0].getPos());

            Vector3f normal = v1.cross(v2).normalise();

            vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
            vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
            vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
        }

        for (Vertex vertex : vertices) {
            vertex.setNormal(vertex.getNormal().normalise());
        }
    }

    private Mesh loadMesh(String fileName){
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        if(!ext.equalsIgnoreCase("obj")){
            System.err.println("ERROR: File format not supported - " + ext);
            new Exception().printStackTrace();
            System.exit(1);
        }

        OBJModel test = new OBJModel("./res/models/" + fileName);
        IndexedModel model = test.toIndexedModel();
        model.calcNormals();

        ArrayList<Vertex> vertices = new ArrayList<>();

        for(int i = 0; i < model.getPositions().size(); i++){
            vertices.add(new Vertex(model.getPositions().get(i),
                                    model.getTextCoordinates().get(i),
                                    model.getNormals().get(i)));
        }

            Vertex[] vertexData = new Vertex[vertices.size()];
            vertices.toArray(vertexData);

            Integer[] indexData = new Integer[model.getIndices().size()];
            model.getIndices().toArray(indexData);

            addVertices(vertexData, Util.toIntArray(indexData), false);

        return null;
    }
}
