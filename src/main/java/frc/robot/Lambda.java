package frc.helpers;

public interface Lambda<T, V> {
    void run(T target, V value);
}