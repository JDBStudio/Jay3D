package org.jay3d.engine.core;

import org.jay3d.engine.rendering.Window;
import org.jay3d.util.Time;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class CoreEngine {

    private boolean isRunning;
    private Game game;
    private RenderingEngine engine;
    private int width, height;
    private double frameTime;

    public CoreEngine(int width, int height, double framerate, Game game){
        this.isRunning = false;
        this.game = game;
        this.width = width;
        this.height = height;
        this.frameTime = 1.0/framerate;
    }

    public void createWindow(String title){
        Window.createWindow(width, height, title);
        this.engine = new RenderingEngine();
    }

    public void start(){
        if(isRunning)
            return;

        run();
    }

    public void stop(){
        if(!isRunning)
            return;

        isRunning = false;
    }

    private void run(){
        isRunning = true;

        int frames = 0;
        long frameCounter = 0;

        game.init();

        long lastTime = Time.getTime();
        double unprocessedTime = 0;

        while(isRunning){
            boolean render = false;

            long startTime = Time.getTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double)Time.SECOND;
            frameCounter += passedTime;

            while(unprocessedTime > frameTime){
                render = true;

                unprocessedTime -= frameTime;

                if(Window.isCloseRequested())
                    stop();

                Time.setDelta(frameTime);
                game.input();
                engine.input();

                Input.update();

                game.update();
            }

            if(frameCounter >= Time.SECOND){
                System.out.println("FPS: " + frames);
                frames = 0;
                frameCounter = 0;
            }

            if(render){
                engine.render(game.getRootObject());
                Window.render();
                frames++;
            }else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        cleanup();
    }

    private void cleanup(){
        Window.dispose();
    }
}
