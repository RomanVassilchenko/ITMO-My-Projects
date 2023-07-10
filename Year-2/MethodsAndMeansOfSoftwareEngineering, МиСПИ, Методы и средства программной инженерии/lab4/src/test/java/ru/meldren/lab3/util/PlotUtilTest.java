package ru.rossilman.lab3.util;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static ru.rossilman.weblab3.util.PlotUtil.isOnPlot;

/**
 * Created by Rossilman on 19/03/2023
 */
class PlotUtilTest {

    @Test
    void checkIfValueIsOnPlotCircle() {
        assertTrue(isOnPlot(0.25, -0.25, 1.0));
    }

    @Test
    void checkIfValueIsNotOnPlotCircle() {
        assertFalse(isOnPlot(1.0, -1.0, 1.0));
    }

    @Test
    void checkIfValueIsOnPlotTriangle() {
        assertTrue(isOnPlot(0.75, 0.75, 3.0));
    }

    @Test
    void checkIfValueIsNotOnPlotTriangle() {
        assertFalse(isOnPlot(1.5, 1.5, 3.0));
    }

    @Test
    void checkIfValueIsOnPlotRectangle() {
        assertTrue(isOnPlot(-0.5, -0.5, 2.0));
    }

    @Test
    void checkIfValueIsNotOnPlotRectangle() {
        assertFalse(isOnPlot(-2.001, -2.0, 2.0));
    }

    @RepeatedTest(100)
    void checkIfValueIsOutsideOfThePlot() {
        Random random = ThreadLocalRandom.current();
        double r = random.nextInt(), x = random.nextDouble() + r, y = random.nextDouble() + r;
        assertFalse(isOnPlot(x, y, r));
    }
}
