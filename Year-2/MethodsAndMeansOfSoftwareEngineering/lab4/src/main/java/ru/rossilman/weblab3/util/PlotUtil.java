package ru.rossilman.weblab3.util;

import lombok.experimental.UtilityClass;

/**
 * Created by Rossilman on 04/05/2023
 */
@UtilityClass
public class PlotUtil {

    public boolean isOnPlot(double x, double y, double r) {
        return (x >= 0 && x <= r && y >= 0 && y <= r / 2 && x + 2 * y <= r) || //triangle
                (x <= 0 && x >= -r && y <= 0 && y >= -r) || //rectangle
                (x >= 0 && y <= 0 && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2)); //circle
    }
}
