package Data;

import Reader.ConsoleReader;

import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

public class Coordinates implements Serializable {
    private final int x;
    private final float y;

    public Coordinates(int x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates fillCoordinates(Scanner scanner) throws NullPointerException {
        System.out.println("Entering the Coordinates object:");
        int x = (int) ConsoleReader.conditionalRead(scanner, "Enter x: ", false, Integer::parseInt, Objects::nonNull);
        float y = (float) ConsoleReader.conditionalRead(scanner, "Enter y: ", false, Float::parseFloat, Objects::nonNull);
        return new Coordinates(x, y);
    }

    public static Coordinates fillCoordinatesFromFile(Scanner scanner) throws NullPointerException {
        System.out.println("Entering the Coordinates object:");
        int x = (int) ConsoleReader.conditionalRead(scanner, "", false, Integer::parseInt, Objects::nonNull);
        float y = (float) ConsoleReader.conditionalRead(scanner, "", false, Float::parseFloat, Objects::nonNull);
        return new Coordinates(x, y);
    }

    /**
     * @return Координату X
     */
    public int getX() {
        return x;
    }

    /**
     * @return Координату Y
     */
    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "X=" + x + "," + "Y=" + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(that.y, y) == 0 && x == that.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
