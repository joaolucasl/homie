package com.github.joaolucasl.homie;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.event.LoggingAdapter;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import com.github.joaolucasl.homie.application.Router;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class ApplicationServer extends AllDirectives {

    private static ActorSystem system;
    private static Http http;
    private static ActorMaterializer materializer;
    private static LoggingAdapter logger;

    public static void main(String[] args) {
        int port = Optional.ofNullable(System.getProperty("http.port")).map(Integer::valueOf).orElse(8080);

        startActorSystem();

        Router applicationRouter = new Router();

        Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = applicationRouter.routes().flow(system, materializer);

        final CompletionStage<ServerBinding> binding =
                http.bindAndHandle(routeFlow, ConnectHttp.toHost("localhost", port), materializer);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> system.terminate()));
    }

    private static void startActorSystem() {
        system = ActorSystem.create("homieApplicationServer");
        http = Http.get(system);
        materializer = ActorMaterializer.create(system);
        logger = system.log();
    }
}



