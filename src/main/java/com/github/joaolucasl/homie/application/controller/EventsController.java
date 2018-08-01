package com.github.joaolucasl.homie.application.controller;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.util.Timeout;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.joaolucasl.homie.ApplicationServer;
import com.github.joaolucasl.homie.application.actors.EventHandlerActor;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class EventsController extends AllDirectives {

    final ActorSystem system = ApplicationServer.getSystem();
    final ActorRef eventHandler = system.actorOf(Props.create(EventHandlerActor.class));

    public Route routes() {


        return path("events", () ->
            post(() ->
                entity(
                        Jackson.unmarshaller(JsonNode.class),
                        (payload) -> {
                            eventHandler.tell(new EventHandlerActor.HandleEvent(payload), ActorRef.noSender());
                            return complete(StatusCodes.ACCEPTED);
                        })
                )
        );
    }
}
