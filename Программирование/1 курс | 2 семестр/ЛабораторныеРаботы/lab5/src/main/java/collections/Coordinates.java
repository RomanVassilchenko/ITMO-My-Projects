package collections;


/**
 * Coordinates is a class that represents a point in a two-dimensional space
 */
public class Coordinates {
    private int x;
    private Float y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Coordinates(int x, Float y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Get the x coordinate of the point
     * 
     * @return The x value of the object.
     */
    public int getX() {
        return x;
    }


    /**
     * Returns the y-coordinate of the point
     * 
     * @return The y-coordinate of the point.
     */
    public Float getY() {
        return y;
    }
}
