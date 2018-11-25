package fengye.util;

import java.lang.Math;

public class Helper {
    public static float getPowerLinear(float diff)
    {
    	return diff * 255.0f;
    }

    public static float getPowerExponential(float diff, float pow)
    {
    	return (float)Math.pow(diff, pow) * 255.0f;
    }
}
