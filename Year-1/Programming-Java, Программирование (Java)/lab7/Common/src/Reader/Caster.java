package Reader;

@FunctionalInterface
public interface Caster<T> {
    T cast(String x);
}
