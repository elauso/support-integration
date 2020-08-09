package br.com.integration.supportintegrationcore.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class ExampleRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("jetty:http://0.0.0.0:8080/example").id("example-route")
                .convertBodyTo(String.class)
                .log("Received body: ${body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
}