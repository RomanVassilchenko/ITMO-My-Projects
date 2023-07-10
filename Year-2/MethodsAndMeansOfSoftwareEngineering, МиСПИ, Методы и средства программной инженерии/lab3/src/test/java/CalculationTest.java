import data.Result;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CalculationTest {
    private final Result result = new Result();

    @Test
    public void hitCircle() {
        result.setX(-0.2);
        result.setY(-0.2);
        result.setR(1);
        assertTrue(result.testHit());
    }

    @Test
    public void hitCircleFarBorder() {
        result.setX(-1);
        result.setY(0);
        result.setR(1);
        assertTrue(result.testHit());
    }

    @Test
    public void hitCircleLowBorder() {
        result.setX(0);
        result.setY(-1);
        result.setR(1);
        assertTrue(result.testHit());
    }

    @Test
    public void hitCircleArchBorder() {
        result.setX(-Math.sqrt(2)/2);
        result.setY(-Math.sqrt(2)/2);
        result.setR(1);
        assertTrue(result.testHit());
    }

    @Test
    public void missCircle() {
        result.setX(-1);
        result.setY(-1);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void missCircleFarBorder() {
        result.setX(-1.001);
        result.setY(0);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void missCircleLowBorder() {
        result.setX(0);
        result.setY(-1.001);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void missCircleArchBorder() {
        result.setX(-Math.sqrt(2)/2 - 0.001);
        result.setY(-Math.sqrt(2)/2 - 0.001);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void hitTriangle() {
        result.setX(0.2);
        result.setY(-0.2);
        result.setR(1);
        assertTrue(result.testHit());
    }

    @Test
    public void hitTriangleLowBorder() {
        result.setX(0.001);
        result.setY(-0.499);
        result.setR(1);
        assertTrue(result.testHit());
    }

    @Test
    public void hitTriangleTopBorder() {
        result.setX(0.5);
        result.setY(0);
        result.setR(1);
        assertTrue(result.testHit());
    }

    @Test
    public void hitTriangleHBorder() {
        result.setX(0.25);
        result.setY(-0.25);
        result.setR(1);
        assertTrue(result.testHit());
    }

    @Test
    public void missTriangle() {
        result.setX(0.5);
        result.setY(0.5);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void missTriangleLowBorder() {
        result.setX(0.001);
        result.setY(-0.5);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void missTriangleTopBorder() {
        result.setX(0.501);
        result.setY(0);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void missTriangleHBorder() {
        result.setX(Math.sqrt(3)*1/4 + 0.001);
        result.setY(Math.sqrt(3)*1/4 + 0.001);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void hitRectangle() {
        result.setX(-0.2);
        result.setY(0.2);
        result.setR(1);
        assertTrue(result.testHit());
    }

    @Test
    public void hitRectangleLowBorder() {
        result.setX(-0.5);
        result.setY(0);
        result.setR(1);
        assertTrue(result.testHit());
    }

    @Test
    public void hitRectangleTopBorder() {
        result.setX(0);
        result.setY(1);
        result.setR(1);
        assertTrue(result.testHit());
    }

    @Test
    public void hitRectangleFarTopBorder() {
        result.setX(-0.5);
        result.setY(1);
        result.setR(1);
        assertTrue(result.testHit());
    }

    @Test
    public void missRectangle() {
        result.setX(-1);
        result.setY(1);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void missRectangleLowBorder() {
        result.setX(- 0.5 - 0.001);
        result.setY(0.001);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void missRectangleTopBorder() {
        result.setX(0);
        result.setY(1 + 0.001);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void missRectangleFarTopBorder() {
        result.setX(-0.5);
        result.setY(-1 - 0.001);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void hitEmptySector() {
        result.setX(0.5);
        result.setY(0.5);
        result.setR(1);
        assertFalse(result.testHit());
    }

    @Test
    public void rSwitch() {
        result.setX(-1);
        result.setY(2);
        result.setR(1);
        assertFalse(result.testHit());
        result.setR(2.5);
        assertTrue(result.testHit());
        result.setR(5);
        assertTrue(result.testHit());
    }
}
