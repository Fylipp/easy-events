package com.pploder.events;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class TestSimpleEvent {

    @Test
    public void testTriggerWithNull() {
        Event<Void> event = new SimpleEvent<>();

        AtomicBoolean executed = new AtomicBoolean();
        event.addListener(arg -> executed.set(true));

        event.trigger(null);

        Assert.assertTrue(executed.get());
    }

    @Test
    public void testTriggerNoArg() {
        Event<Void> event = new SimpleEvent<>();

        AtomicBoolean executed = new AtomicBoolean();
        event.addListener(arg -> executed.set(true));

        event.trigger();

        Assert.assertTrue(executed.get());
    }

    @Test
    public void testTriggerWithArg() {
        Event<String> event = new SimpleEvent<>();
        AtomicReference<String> reference = new AtomicReference<>();

        event.addListener(reference::set);
        event.trigger("Hello, World!");

        Assert.assertEquals("Hello, World!", reference.get());
    }

    @Test
    public void testListenerRemoval() {
        Event<Void> event = new SimpleEvent<>();
        AtomicBoolean executed = new AtomicBoolean();
        Consumer<Void> listener = arg -> executed.set(true);

        event.addListener(listener);
        event.trigger();

        Assert.assertTrue(executed.get());

        executed.set(false);

        event.removeListener(listener);
        event.trigger();

        Assert.assertFalse(executed.get());
    }

    @Test(expected = NullPointerException.class)
    public void testAddListenerNull() {
        new SimpleEvent<>().addListener(null);
    }

    @Test
    public void testDuplicateListener() {
        Event<Void> event = new SimpleEvent<>();
        AtomicInteger counter = new AtomicInteger();
        Consumer<Void> listener = arg -> counter.incrementAndGet();

        event.addListener(listener);
        event.addListener(listener);

        event.trigger();

        Assert.assertEquals(2, counter.get());
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveListenerNull() {
        new SimpleEvent<>().removeListener(null);
    }

}
