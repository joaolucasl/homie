package com.github.joaolucasl.homie.application.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;

public class EventHandlerActor extends AbstractActor {

    // Messages
    public static class HandleEvent {
        public final JsonNode payload;

        public HandleEvent(JsonNode payload) {
            this.payload = payload;
        }
    }

    private final LoggingAdapter LOGGER = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(HandleEvent.class, event -> {
                    LOGGER.info("Received JSON message: {}", event.payload);
                })
                .matchAny(o -> LOGGER.warning("Received Unknown message of type {}", o.getClass().getName()))
                .build();
    }
}
