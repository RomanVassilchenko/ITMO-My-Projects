package Reader;

@FunctionalInterface
public interface Condition<T> {
    boolean check(String x);
}
