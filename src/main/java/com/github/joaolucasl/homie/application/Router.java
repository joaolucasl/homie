package com.github.joaolucasl.homie.application;

import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import com.github.joaolucasl.homie.application.controller.SlackChallengeController;

public class Router extends AllDirectives {

    private SlackChallengeController slackChallengeController;

    public Router() {
        this.slackChallengeController = new SlackChallengeController();
    }

    public Route routes(){
        return route(
            slackChallengeController.routes()
        );
    }
}
