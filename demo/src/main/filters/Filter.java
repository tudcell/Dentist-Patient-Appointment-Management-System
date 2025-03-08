package main.filters;

public interface Filter<T> {
    boolean accept(T item);
}