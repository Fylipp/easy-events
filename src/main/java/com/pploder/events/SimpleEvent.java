package com.pploder.events;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * A simple implementation of an event.
 * Duplicate occurrences of listeners are supported.
 *
 * @param <T> The argument type to be passed to the listeners. (copied from {@link Event})
 * @author Philipp Ploder
 * @version 1.0.0
 * @since 1.0.0
 */
public class SimpleEvent<T> implements Event<T> {

    private final List<Consumer<T>> listeners = new CopyOnWriteArrayList<>();

    @Override
    public void addListener(Consumer<T> listener) throws NullPointerException {
        if (listener == null) {
            throw new NullPointerException("The listener to add may not be null");
        }

        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(Consumer<T> listener) throws NullPointerException {
        if (listener == null) {
            throw new NullPointerException("The listener to remove may not be null");
        }

        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    @Override
    public void trigger(T t) {
        listeners.forEach(listener -> listener.accept(t));
    }

}
