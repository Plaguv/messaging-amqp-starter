package io.github.plaguv.messaging.config.autoconfiguration;

import io.github.plaguv.messaging.config.properties.AmqpProperties;
import io.github.plaguv.messaging.listener.discoverer.AmqpEventListenerDiscoverer;
import io.github.plaguv.messaging.listener.discoverer.EventListenerDiscoverer;
import io.github.plaguv.messaging.listener.registrar.AmqpEventListenerRegistrar;
import io.github.plaguv.messaging.listener.registrar.EventListenerRegistrar;
import io.github.plaguv.messaging.listener.topology.AmqpEventListenerTopology;
import io.github.plaguv.messaging.listener.topology.EventListenerTopology;
import io.github.plaguv.messaging.utlity.converter.EventEnvelopeAmqpConverter;
import io.github.plaguv.messaging.utlity.EventRouter;
import io.github.plaguv.messaging.utlity.converter.EventPayloadArgumentResolver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@AutoConfiguration(after = AmqpAutoConfiguration.class)
public class AmqpEventListenerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EventListenerDiscoverer eventListenerDiscoverer(ListableBeanFactory listableBeanFactory) {
        return new AmqpEventListenerDiscoverer(listableBeanFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public EventListenerRegistrar eventListenerRegistrar(MessageHandlerMethodFactory factory, EventListenerDiscoverer discoverer, EventRouter router) {
        return new AmqpEventListenerRegistrar(factory, discoverer, router);
    }

    @Bean
    @ConditionalOnMissingBean
    public EventListenerTopology topologyDeclarer(AmqpProperties amqpProperties, EventRouter eventRouter) {
        return new AmqpEventListenerTopology(amqpProperties, eventRouter);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBooleanProperty(prefix = "amqp.skip", name = "register-listeners", havingValue = false, matchIfMissing = true)
    public Declarables declarables(EventListenerDiscoverer discoverer, EventListenerTopology eventListenerTopology) {
        return eventListenerTopology.getDeclarablesFromListeners(discoverer.getListeners());
    }

    @Bean
    @ConditionalOnMissingBean(name = "rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setMessageConverter(new EventEnvelopeAmqpConverter(objectMapper));
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setChannelTransacted(false);
        factory.setAdviceChain(RetryInterceptorBuilder.stateless()
                .maxRetries(5)
                .backOffOptions(1000, 5.0, 100_000)
                .build());

        return factory;
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageHandlerMethodFactory messageHandlerMethodFactory(ObjectMapper objectMapper) {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();

        factory.setCustomArgumentResolvers(List.of(
                new EventPayloadArgumentResolver(objectMapper)
        ));
        factory.afterPropertiesSet();

        return factory;
    }
}