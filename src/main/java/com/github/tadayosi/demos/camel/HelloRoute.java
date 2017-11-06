package com.github.tadayosi.demos.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HelloRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // @formatter:off
        from("undertow:http://localhost:8080/hello")
            .log("name = ${in.header.name}")
            .setBody(simple("Hello, ${in.header.name}!"));
        // @formatter:on
    }

}
