package com.github.tadayosi.demos.camel;

import io.hawt.web.AuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:camel-context.xml")
public class CamelApplication {

    public static void main(String[] args) {
        System.setProperty(AuthenticationFilter.HAWTIO_AUTHENTICATION_ENABLED, "false");
        SpringApplication.run(CamelApplication.class, args);
    }

}
