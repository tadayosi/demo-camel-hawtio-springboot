package com.github.tadayosi.demos.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.twitter.timeline.TwitterTimelineComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HelloRoute extends RouteBuilder {

    @Value("${twitter.consumer.key}")
    private String consumerKey;

    @Value("${twitter.consumer.secret}")
    private String consumerSecret;

    @Value("${twitter.access.token}")
    private String accessToken;

    @Value("${twitter.access.token.secret}")
    private String accessTokenSecret;

    @Override
    public void configure() throws Exception {
        setupTwitter();

        // @formatter:off
        from("undertow:http://localhost:8080/hello").id("hello-java")
            .log("name = ${in.header.name}")
            .setBody(simple("Hello, ${in.header.name} from Java at ${date:now:yyyy/MM/dd HH:mm:ss}! #test"))
            .to("twitter-timeline://user")
            .setBody(constant("Success!"));
        // @formatter:on
    }

    private void setupTwitter() {
        TwitterTimelineComponent twitter = getContext().getComponent("twitter-timeline", TwitterTimelineComponent.class);
        twitter.setConsumerKey(consumerKey);
        twitter.setConsumerSecret(consumerSecret);
        twitter.setAccessToken(accessToken);
        twitter.setAccessTokenSecret(accessTokenSecret);
    }

}
