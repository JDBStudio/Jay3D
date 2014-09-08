package org.jay3d.util;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Time {
    private static final long SECOND = 1000000000L;
    public static double getTime()
    {
        return (double)System.nanoTime()/(double)SECOND;
    }
}
