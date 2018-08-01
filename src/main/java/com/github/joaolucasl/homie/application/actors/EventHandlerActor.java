package com.github.joaolucasl.homie.application.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.joaolucasl.homie.application.actors.DirectMessageHandlerActor.HandleDirectMessageEvent;

public class EventHandlerActor extends AbstractActor {
    private final LoggingAdapter LOGGER = Logging.getLogger(getContext().getSystem(), this);

    final ActorRef directMessageHandler = getContext().actorOf(Props.create(DirectMessageHandlerActor.class), "directMessageHandler");

    // Messages
    public static class HandleEvent {
        public final JsonNode payload;

        public HandleEvent(JsonNode payload) {
            this.payload = payload;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(HandleEvent.class, event -> {
                    LOGGER.info("Received JSON message: {}", event.payload);
                    dispatchToSpecializedActor(event.payload);
                })
                .matchAny(o -> LOGGER.warning("Received Unknown message of type {}", o.getClass().getName()))
                .build();
    }

    public void dispatchToSpecializedActor(JsonNode payload) {
        JsonNode event = payload.path("event");
        JsonNode eventType = event.path("type");

        if (eventType.isMissingNode()) {
            // TODO: Dispatch message to monitoring actor
            LOGGER.error("Can't handle event - type is not defined in the payload");
            return;
        }

        switch (eventType.asText()) {
            case "message":
                directMessageHandler.tell(new HandleDirectMessageEvent(event), getSelf());
        }
    }
}
