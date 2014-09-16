package org.jay3d.game;

import org.jay3d.engine.core.CoreEngine;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Main {
    public static void main(String[] args) {
        CoreEngine engine = new CoreEngine(800, 600, 60, new TestGame());
        engine.createWindow("Jay3D");
        engine.start();
    }
}
