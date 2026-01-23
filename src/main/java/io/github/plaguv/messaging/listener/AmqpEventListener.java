package io.github.plaguv.messaging.listener;

import io.github.plaguv.contracts.common.EventInstance;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AmqpEventListener<T extends EventInstance> implements EventListener<T> {

    public AmqpEventListener() {}

    @Override
    @SuppressWarnings("unchecked")
    public Class<T> getEventType() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType type) {
            return (Class<T>) type.getActualTypeArguments()[0];
        }
        throw new IllegalStateException("Could not infer generic event type for class '%s'".formatted(getClass().getSimpleName()));
    }
}