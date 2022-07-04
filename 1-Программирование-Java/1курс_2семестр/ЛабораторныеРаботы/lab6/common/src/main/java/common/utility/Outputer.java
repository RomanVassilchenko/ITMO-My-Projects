package common.utility;

/**
 * Class for outputting something
 */
public class Outputer {
    /**
     * Prints toOut.toString() to Console
     *
     * @param toOut Object to print
     */
    public static void print(Object toOut) {
        System.out.print(toOut);
    }

    /**
     * Prints \n to Console
     */
    public static void println() {
        System.out.println();
    }

    /**
     * Prints toOut.toString() + \n to Console
     *
     * @param toOut Object to print
     */
    public static void printLn(Object toOut) {
        System.out.println(toOut);
    }

    /**
     * Prints error: toOut.toString() to Console
     *
     * @param toOut Error to print
     */
    public static void printError(Object toOut) {
        System.out.println("error: " + toOut);
    }

    /**
     * Prints formatted 2-element table to Console
     *
     * @param element1 Left element of the row.
     * @param element2 Right element of the row.
     */
    public static void printTable(Object element1, Object element2) {
        System.out.printf("%-37s%-1s%n", element1, element2);
    }
}