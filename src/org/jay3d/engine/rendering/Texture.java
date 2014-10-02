package org.jay3d.engine.rendering;


import org.jay3d.engine.rendering.resources.TextureResource;
import org.jay3d.util.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Texture {
    private static HashMap<String, TextureResource> loadedTextures = new HashMap<>();
    private TextureResource textureResource;
    private String fileName;

    public Texture(String fileName){
        this.fileName = fileName;
        TextureResource oldResource = loadedTextures.get(fileName);

        if(oldResource != null){
            textureResource = oldResource;
            textureResource.addReference();
        }else{
            textureResource = loadTexture(fileName);
            loadedTextures.put(fileName, textureResource);
        }
    }

    public void bind(){
        bind(0);
    }

    public void bind(int samplerSlot){
        assert(samplerSlot >= 0 && samplerSlot <= 31);
        glActiveTexture(GL_TEXTURE0 + samplerSlot);
        glBindTexture(GL_TEXTURE_2D, textureResource.getId());
    }

    public int getId(){
        return textureResource.getId();
    }

    @SuppressWarnings("FinalizeDoesntCallSuperFinalize")
    @Override
    protected void finalize(){
        if(textureResource.removeReference()
                && !fileName.isEmpty()){
            loadedTextures.remove(fileName);
        }
    }


    private static TextureResource loadTexture(String fileName){
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        try{
            BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName));
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

            ByteBuffer buffer = Util.createByteBuffer(image.getHeight() * image.getWidth() * 4);

            boolean hasAlpha = image.getColorModel().hasAlpha();

            for(int y = 0; y < image.getHeight(); y++){
                for(int x = 0; x < image.getWidth(); x++){
                    int pixel = pixels[y * image.getWidth() + x];

                    buffer.put((byte)((pixel >> 16) & 0xFF));
                    buffer.put((byte)((pixel >> 8) & 0xFF));
                    buffer.put((byte)((pixel) & 0xFF));

                    if(hasAlpha)
                        buffer.put((byte)((pixel >> 24) & 0xFF));
                    else
                        buffer.put((byte)(0xFF));

                }
            }

            buffer.flip();

            TextureResource resource = new TextureResource();
            glBindTexture(GL_TEXTURE_2D, resource.getId());

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8,image.getWidth(),
                    image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

            return resource;
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
