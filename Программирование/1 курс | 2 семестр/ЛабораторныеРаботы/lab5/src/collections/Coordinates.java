package collections;

/**
 * Coordinates of Organization (x and y)
 */
public class Coordinates {
    private int x;
    private Float y; //Поле не может быть null

    public Coordinates(int x, Float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * get Organization's x coordinate
     * @return get coordinate x
     */
    public int getX() {
        return x;
    }

    /**
     * get Organization's y coordinate
     * @return get coordinate y
     */
    public Float getY() {
        return y;
    }
}
