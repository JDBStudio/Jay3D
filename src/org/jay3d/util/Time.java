package org.jay3d.util;

/**
 * @author Juxhin Dyrmishi Brigjaj
 */
public class Time {
    private static final long SECOND = 1000000000L;
    private static double delta;

    public static double getTime(){
        return (double)System.nanoTime()/(double)SECOND;
    }
}
