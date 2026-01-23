package io.github.plaguv.messaging.listener;

import io.github.plaguv.contracts.common.EventEnvelope;
import io.github.plaguv.contracts.common.EventInstance;
import io.github.plaguv.messaging.publisher.EventRouter;
import io.github.plaguv.messaging.publisher.TopologyDeclarer;
import jakarta.annotation.Nonnull;
import org.springframework.amqp.rabbit.listener.MethodRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;

@Component
public class AmqpEventListenerRegistrar implements ApplicationContextAware, InitializingBean, EventListenerRegistrar {

    private final RabbitListenerEndpointRegistry registry;
    private final RabbitListenerContainerFactory<?> factory;
    private final TopologyDeclarer topologyDeclarer;
    private final EventRouter eventRouter;

    private ApplicationContext applicationContext;

    public AmqpEventListenerRegistrar(
            RabbitListenerEndpointRegistry registry,
            RabbitListenerContainerFactory<?> factory,
            TopologyDeclarer topologyDeclarer,
            EventRouter eventRouter
    ) {
        this.registry = registry;
        this.factory = factory;
        this.topologyDeclarer = topologyDeclarer;
        this.eventRouter = eventRouter;
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventListener> listeners = applicationContext.getBeansOfType(EventListener.class);

        for (EventListener<?> listener : listeners.values()) {
            registerListener(listener);
        }
    }

    private <T extends EventInstance> void registerListener(EventListener<T> listener) {

        Class<T> eventType = listener.getEventType();
        EventEnvelope mockEventEnvelope = new EventEnvelope(null, null, null);

        String queueName = eventRouter.resolveQueue(mockEventEnvelope);

        topologyDeclarer.declareExchangeIfAbsent(mockEventEnvelope);
        topologyDeclarer.declareQueueIfAbsent(mockEventEnvelope);
        topologyDeclarer.declareBindingIfAbsent(mockEventEnvelope);

        Method method = ReflectionUtils.findMethod(listener.getClass(), "onEvent", eventType);

        MethodRabbitListenerEndpoint endpoint = new MethodRabbitListenerEndpoint();
        endpoint.setId(listener.getClass().getName() + "#" + eventType.getSimpleName());
        endpoint.setBean(listener);
        endpoint.setMethod(method);
        endpoint.setQueueNames(queueName);

        registry.registerListenerContainer(endpoint, factory, true);
    }
}