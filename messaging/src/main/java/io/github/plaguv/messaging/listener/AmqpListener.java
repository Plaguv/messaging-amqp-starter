package io.github.plaguv.messaging.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RabbitListener
public @interface AmqpListener {}