package managers;

/**
 * Operates command input.
 */
public class Console {
    /**
     * Prints toOut.toString() to Console
     * @param toOut Object to print
     */
    public static void print(Object toOut){
        System.out.print(toOut);
    }
    /**
     * Prints toOut.toString() + \n to Console
     * @param toOut Object to print
     */
    public static void printLn(Object toOut) {
        System.out.println(toOut);
    }
    /**
     * Prints error: toOut.toString() to Console
     * @param toOut Error to print
     */
    public static void printError(Object toOut) {
        System.out.println("Error: " + toOut);
    }
}
