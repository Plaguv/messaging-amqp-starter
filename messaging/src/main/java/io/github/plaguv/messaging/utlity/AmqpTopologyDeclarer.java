package io.github.plaguv.messaging.utlity;

import io.github.plaguv.contract.envelope.routing.EventRoutingDescriptor;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AmqpTopologyDeclarer implements TopologyDeclarer {

    private static final Logger log = LoggerFactory.getLogger(AmqpTopologyDeclarer.class);

    private final RabbitAdmin rabbitAdmin;
    private final EventRouter eventRouter;

    private final ConcurrentMap<String, Exchange> declaredExchanges = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Queue> declaredQueues = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Binding> declaredBindings = new ConcurrentHashMap<>();

    public AmqpTopologyDeclarer(RabbitAdmin rabbitAdmin, EventRouter eventRouter) {
        this.rabbitAdmin = rabbitAdmin;
        this.eventRouter = eventRouter;
    }

    @Override
    public Exchange declareExchangeIfAbsent(@Nonnull EventRoutingDescriptor eventRoutingDescriptor) {
        String exchangeName = eventRouter.resolveExchange(eventRoutingDescriptor);
        return declaredExchanges.computeIfAbsent(exchangeName, name -> {
            Exchange exchange = switch (eventRoutingDescriptor.dispatchType()) {
                case DIRECT -> new DirectExchange(name, true, false);
                case TOPIC -> new TopicExchange(name, true, false);
                case FANOUT -> new FanoutExchange(name, true, false);
            };

            rabbitAdmin.declareExchange(exchange);
            log.atInfo().log("Declared centralExchange '{}'", name);

            return exchange;
        });
    }

    @Override
    public Queue declareQueueIfAbsent(@Nonnull EventRoutingDescriptor eventRoutingDescriptor) {
        String queueName = eventRouter.resolveQueue(eventRoutingDescriptor);

        return declaredQueues.computeIfAbsent(queueName, name -> {
            Queue queue = new Queue(name, true, false, false);

            rabbitAdmin.declareQueue(queue);
            log.atInfo().log("Declared queue '{}'", name);

            return queue;
        });
    }

    @Override
    public Binding declareBindingIfAbsent(@Nonnull EventRoutingDescriptor eventRoutingDescriptor) {
        String bindingKey = eventRouter.resolveBinding(eventRoutingDescriptor);
        String exchangeName = eventRouter.resolveExchange(eventRoutingDescriptor);
        String queueName = eventRouter.resolveQueue(eventRoutingDescriptor);

        // Ensure required topology exists
        declareExchangeIfAbsent(eventRoutingDescriptor);
        declareQueueIfAbsent(eventRoutingDescriptor);

        return declaredBindings.computeIfAbsent(bindingKey, key -> {
            Queue queue = declaredQueues.get(queueName);
            Exchange exchange = declaredExchanges.get(exchangeName);
            Binding binding = switch (exchange.getType()) {
                case ExchangeTypes.DIRECT -> BindingBuilder.bind(queue)
                        .to((DirectExchange) exchange)
                        .with(key);
                case ExchangeTypes.TOPIC -> BindingBuilder.bind(queue)
                        .to((TopicExchange) exchange)
                        .with(key);
                case ExchangeTypes.FANOUT -> BindingBuilder
                        .bind(queue)
                        .to((FanoutExchange) exchange);
                default -> throw new IllegalStateException("Unknown centralExchange type: " + exchange.getType());
            };

            rabbitAdmin.declareBinding(binding);
            log.atInfo().log("Declared binding '{}' for centralExchange '{}' -> queue '{}'", key, exchangeName, queueName);

            return binding;
        });
    }
}