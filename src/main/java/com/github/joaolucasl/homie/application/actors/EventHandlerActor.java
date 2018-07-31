package com.github.joaolucasl.homie.application.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;

public class EventHandlerActor extends AbstractActor {

    private final LoggingAdapter LOGGER = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(JsonNode.class, payload -> {
                    LOGGER.info("Received JSON message: {}", payload);
                })
                .matchAny(o -> LOGGER.warning("Received Unknown message of type {}", o.getClass().getName()))
                .build();
    }
}
