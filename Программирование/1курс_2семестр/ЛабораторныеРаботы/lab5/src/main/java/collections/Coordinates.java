package collections;



/**
 * This class represents a point in a two dimensional space
 */
public class Coordinates {
    private final int x;
    private final Float y;

    public Coordinates(int x, Float y) {
        this.x = x;
        this.y = y;
    }


    
    /**
     * Get the x coordinate of the point
     * 
     * @return The value of the instance variable x.
     */
    public int getX() {
        return x;
    }


    
    /**
     * Returns the y-coordinate of the point
     * 
     * @return The y value of the point.
     */
    public Float getY() {
        return y;
    }
}
