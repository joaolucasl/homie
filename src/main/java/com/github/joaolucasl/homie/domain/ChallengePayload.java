package com.github.joaolucasl.homie.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChallengePayload {
    public final String token;
    public final String challenge;
    public final String type;

    @JsonCreator
    public ChallengePayload(@JsonProperty("token") String token, @JsonProperty("challenge") String challenge, @JsonProperty("type") String type) {
        this.token = token;
        this.challenge = challenge;
        this.type = type;
    }
}
