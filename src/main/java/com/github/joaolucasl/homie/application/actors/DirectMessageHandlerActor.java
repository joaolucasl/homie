package com.github.joaolucasl.homie.application.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;

public class DirectMessageHandlerActor extends AbstractActor {
    private final LoggingAdapter LOGGER = Logging.getLogger(getContext().getSystem(), this);

    public static class HandleDirectMessageEvent {
        public final JsonNode payload;

        public HandleDirectMessageEvent(JsonNode event) {
            this.payload = event;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(HandleDirectMessageEvent.class, event -> {
                    LOGGER.info("Handling Direct Message: {}", event.payload);
                })
                .build();
    }
}
