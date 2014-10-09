package org.jay3d.engine.core;

import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.rendering.Window;
import org.jay3d.util.Time;

/**
 * The CoreEngine class is the essence of Jay3D engine. Whilst it's not fully finished, this class is to be used to:
 * <p>
 *     - Setup the configurations for the windows and game.
 * </p>
 * <p>
 *     - Initialise your game.
 * </p>
 * <p>
 *     - Cleanup the environment when needed.
 * </p>
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class CoreEngine {

    private boolean isRunning;
    private Game game;
    private RenderingEngine engine;
    private int width, height;
    private double frameTime;

    /**
     * @param width
     *      The desired Window width.
     * @param height
     *      The desired Window height.
     * @param framerate
     *      The desired FPS cap.
     * @param game
     *      The game to link the engine with.
     */
    public CoreEngine(int width, int height, double framerate, Game game){
        this.isRunning = false;
        this.game = game;
        this.width = width;
        this.height = height;
        this.frameTime = 1.0/framerate;
        game.setEngine(this);
    }

    /**
     * @param title
     *      Desired title for your Window
     */
    public void createWindow(String title){
        Window.createWindow(width, height, title);
        this.engine = new RenderingEngine();
    }

    /**
     * Starts the engine.
     */
    public void start(){
        if(isRunning)
            return;

        run();
    }

    /**
     * Stops the engine.
     */
    public void stop(){
        if(!isRunning)
            return;

        isRunning = false;
    }

    /**
     * Performs core functions, rendering, updating, input, FPS and so on.
     */
    private void run() {
        isRunning = true;

        int frames = 0;
        long frameCounter = 0;

        game.init();

        double lastTime = Time.getTime();
        double unprocessedTime = 0;

        while (isRunning) {
            boolean render = false;

            double startTime = Time.getTime();
            double passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime;
            frameCounter += passedTime;

            while (unprocessedTime > frameTime) {
                render = true;

                unprocessedTime -= frameTime;

                if (Window.isCloseRequested())
                    stop();

                game.input((float) frameTime);
                Input.update();

                game.update((float) frameTime);
            }

            if (frameCounter >= 1.0) {
                System.out.println("FPS: " + frames);
                frames = 0;
                frameCounter = 0;
            }

            if (render) {
                game.render(engine);
                Window.render();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        cleanup();
    }

    /**
     * @return
     *      The Core Engine's Rendering Engine.
     */
    public RenderingEngine getRenderingEngine() {
        return engine;
    }

    /**
     * Cleans the environment.
     */
    private void cleanup(){
        Window.dispose();
    }
}
