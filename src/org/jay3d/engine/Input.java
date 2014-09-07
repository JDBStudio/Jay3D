package org.jay3d.engine;

import org.jay3d.engine.math.Vector2f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Input {
    public static final int NUM_KEYCODE = 256;
    public static final int NUM_MOUSEBUTTONS = 5;

    private static ArrayList<Integer> currentKeys = new ArrayList<>();
    private static ArrayList<Integer> downKeys = new ArrayList<>();
    private static ArrayList<Integer> upKeys = new ArrayList<>();

    private static ArrayList<Integer> mouseCurrent = new ArrayList<>();
    private static ArrayList<Integer> mouseDown = new ArrayList<>();
    private static ArrayList<Integer> mouseUp = new ArrayList<>();

    public static void update() {
        mouseUp.clear();
        for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
            if (!getMouse(i) && mouseCurrent.contains(i))
                mouseUp.add(i);
        }

        mouseDown.clear();
        for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
            if (getMouse(i) && !mouseCurrent.contains(i))
                mouseDown.add(i);
        }

        upKeys.clear();
        for (int i = 0; i < NUM_KEYCODE; i++) {
            if (!getKey(i) && currentKeys.contains(i))
                upKeys.add(i);
        }

        downKeys.clear();
        for (int i = 0; i < NUM_KEYCODE; i++) {
            if (getKey(i) && !currentKeys.contains(i))
                downKeys.add(i);
        }

        currentKeys.clear();
        for (int i = 0; i < NUM_KEYCODE; i++) {
            if (getKey(i))
                currentKeys.add(i);
        }

        mouseCurrent.clear();
        for(int i = 0; i < NUM_MOUSEBUTTONS; i++){
            if(getMouse(i))
                mouseCurrent.add(i);
        }
    }

    public static boolean getKey(int keyCode){
        return Keyboard.isKeyDown(keyCode);
    }

    public static boolean getKeyDown(int keyCode){
        return downKeys.contains(keyCode);
    }

    public static boolean getKeyUp(int keyCode){
        return upKeys.contains(keyCode);
    }

    public static boolean getMouse(int mouseButton){
        return Mouse.isButtonDown(mouseButton);
    }

    public static boolean getMouseDown(int mouseButton){
        return mouseDown.contains(mouseButton);
    }

    public static boolean getMouseUp(int mouseButton){
        return mouseUp.contains(mouseButton);
    }

    public static Vector2f getMousePosition(){
        return new Vector2f(Mouse.getX(), Mouse.getY());
    }
}
