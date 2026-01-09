package io.github.plaguv.messaging.listener;

public interface EventListener {
    void handleMessage(Object message);
}