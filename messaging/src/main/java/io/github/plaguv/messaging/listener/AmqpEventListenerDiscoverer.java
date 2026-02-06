package io.github.plaguv.messaging.listener;

import io.github.plaguv.contract.envelope.payload.Event;
import io.github.plaguv.contract.envelope.payload.EventInstance;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AmqpEventListenerDiscoverer implements EventListenerDiscoverer, SmartInitializingSingleton {

    private static final Logger log = LoggerFactory.getLogger(AmqpEventListenerDiscoverer.class);

    private final ListableBeanFactory beanFactory;
    private final Map<Method, Class<? extends EventInstance>> listeners = new ConcurrentHashMap<>();

    public AmqpEventListenerDiscoverer(ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Arrays.stream(beanFactory.getBeanNamesForType(Object.class))
                .map(beanFactory::getBean)
                .forEach(this::putIfListenerBean);
    }

    private void putIfListenerBean(Object bean) {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(AmqpListener.class)) {
                continue;
            }
            if (method.getParameterCount() < 1) {
                log.warn("Method '{}' in bean '{}' was annotated with @AmqpListener but has no parameters.", method, bean);
                continue;
            }
            if (method.getParameterCount() > 1) {
                log.warn(
                        "Method '{}' in bean '{}' was annotated with @AmqpListener but has too many parameters.",
                        method, bean);
                continue;
            }

            Parameter parameter = method.getParameters()[0];
            Class<?> parameterType = method.getParameterTypes()[0];

            if (!EventInstance.class.isAssignableFrom(parameterType)) {
                log.warn("Method '{}' in bean '{}' was annotated with '@AmqpListener' but its parameter '{}' does not extend EventInstance.",
                        method, bean, parameter);
                continue;
            }

            Class<?> declaringClass = method.getDeclaringClass();

            if (!declaringClass.isAnnotationPresent(Event.class)) {
                log.warn("Method '{}' in bean '{}' was annotated with '@AmqpListener' but its parameter '{}' does not define an '@EventDomain' in its class '{}'.",
                        method, bean, parameter, declaringClass);
                 continue;
            }

            listeners.putIfAbsent(method, parameterType.asSubclass(EventInstance.class));
        }
    }

    @Override
    @Nonnull
    public Map<Method, Class<? extends EventInstance>> getListeners() {
        return listeners;
    }
}