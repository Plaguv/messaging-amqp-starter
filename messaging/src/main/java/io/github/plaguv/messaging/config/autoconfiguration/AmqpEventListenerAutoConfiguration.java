package io.github.plaguv.messaging.config.autoconfiguration;

import io.github.plaguv.messaging.listener.AmqpEventListenerDiscoverer;
import io.github.plaguv.messaging.listener.AmqpEventListenerRegistrar;
import io.github.plaguv.messaging.listener.EventListenerDiscoverer;
import io.github.plaguv.messaging.listener.EventListenerRegistrar;
import io.github.plaguv.messaging.utlity.EventRouter;
import io.github.plaguv.messaging.utlity.TopologyDeclarer;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = AmqpAutoConfiguration.class)
public class AmqpEventListenerAutoConfiguration {

    public AmqpEventListenerAutoConfiguration() {}

    @Bean
    @ConditionalOnMissingBean
    public EventListenerDiscoverer eventListenerDiscoverer(ListableBeanFactory listableBeanFactory) {
        return new AmqpEventListenerDiscoverer(listableBeanFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public EventListenerRegistrar eventListenerRegistrar(EventRouter eventRouter, EventListenerDiscoverer eventListenerDiscoverer, TopologyDeclarer topologyDeclarer) {
        return new AmqpEventListenerRegistrar(eventRouter, eventListenerDiscoverer, topologyDeclarer);
    }
}