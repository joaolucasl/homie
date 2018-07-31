package com.github.joaolucasl.homie.application;

import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import com.github.joaolucasl.homie.application.controller.EventsController;

public class Router extends AllDirectives {

    private EventsController eventsController;

    public Router() {
        this.eventsController = new EventsController();
    }

    public Route routes(){
        return route(
            eventsController.routes()
        );
    }
}
