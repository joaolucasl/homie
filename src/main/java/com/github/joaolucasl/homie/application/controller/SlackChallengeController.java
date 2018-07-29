package com.github.joaolucasl.homie.application.controller;

import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import com.github.joaolucasl.homie.domain.ChallengePayload;

import java.util.Objects;

public class SlackChallengeController extends AllDirectives {

    public Route routes() {
        return path("events", () ->
                post(() ->
                    entity(Jackson.unmarshaller(ChallengePayload.class),
                            challenge -> (Objects.isNull(challenge.challenge) ? reject() : complete(StatusCodes.OK, challenge.challenge))
                    )
                )
        );
    }
}
