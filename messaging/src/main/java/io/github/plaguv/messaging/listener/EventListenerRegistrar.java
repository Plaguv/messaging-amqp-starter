package io.github.plaguv.messaging.listener;

import jakarta.annotation.Nonnull;

import java.lang.reflect.Method;

public interface EventListenerRegistrar {
    void registerListener(@Nonnull Object bean, @Nonnull Method method);
}