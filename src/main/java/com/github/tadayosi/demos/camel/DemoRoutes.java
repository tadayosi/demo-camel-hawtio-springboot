package com.github.tadayosi.demos.camel;

import com.twilio.type.PhoneNumber;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.twilio.TwilioComponent;
import org.apache.camel.component.twitter.AbstractTwitterComponent;
import org.apache.camel.component.twitter.timeline.TwitterTimelineComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DemoRoutes extends RouteBuilder {

    // Twitter

    @Value("${twitter.consumer.key}")
    private String twitterConsumerKey;

    @Value("${twitter.consumer.secret}")
    private String twitterConsumerSecret;

    @Value("${twitter.access.token}")
    private String twitterAccessToken;

    @Value("${twitter.access.token.secret}")
    private String twitterAccessTokenSecret;

    @Value("${twitter.target.user}")
    private String twitterTargetUser;

    // Twilio

    @Value("${twilio.username}")
    private String twilioUsername;

    @Value("${twilio.password}")
    private String twilioPassword;

    @Value("${twilio.number.from}")
    private String twilioNumberFrom;

    @Value("${twilio.number.to}")
    private String twilioNumberTo;

    @Override
    public void configure() throws Exception {
        setupTwitter(getContext().getComponent("twitter-timeline", TwitterTimelineComponent.class));
        setupTwilio(getContext().getComponent("twilio", TwilioComponent.class));

        // @formatter:off
        from("undertow:http://localhost:8080/hello").id("hello-java")
            .log("name = ${in.header.name}")
            .setBody(simple("Hello, ${in.header.name} from Java at ${date:now:yyyy/MM/dd HH:mm:ss}! #test"))
            .to("twitter-timeline://user")
            .setBody(constant("Success!"));
        // @formatter:on

        // @formatter:off
        fromF("twitter-timeline://user?user=%s", twitterTargetUser).id("twitter-to-twilio")
            .filter(simple("${body.text} starts with 'SEND: '"))
                .log("tweet = ${body.text}")
                .setBody(simple("${body.text.substring(6)}")) // Drop "SEND: "
                .log("message = ${body}")
                .setHeader("CamelTwilioTo", constant(new PhoneNumber(twilioNumberTo)))
                .setHeader("CamelTwilioFrom", constant(new PhoneNumber(twilioNumberFrom)))
                .setHeader("CamelTwilioBody", simple("${body} from Camel!"))
                .to("twilio://message/create");
        // @formatter:on
    }

    private void setupTwitter(AbstractTwitterComponent twitter) {
        twitter.setConsumerKey(twitterConsumerKey);
        twitter.setConsumerSecret(twitterConsumerSecret);
        twitter.setAccessToken(twitterAccessToken);
        twitter.setAccessTokenSecret(twitterAccessTokenSecret);
    }

    private void setupTwilio(TwilioComponent twilio) {
        twilio.getConfiguration().setUsername(twilioUsername);
        twilio.getConfiguration().setPassword(twilioPassword);
    }

}
