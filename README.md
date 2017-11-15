# Demo application: Camel + hawtio + Spring Boot

Set up the following [Twitter](https://apps.twitter.com) & [Twilio](https://www.twilio.com) credentials as environment variables:

    export TWITTER_CONSUMER_KEY=<Twitter consumer key>
    export TWITTER_CONSUMER_SECRET=<Twitter consumer secret>
    export TWITTER_ACCESS_TOKEN=<Twitter access token>
    export TWITTER_ACCESS_TOKEN_SECRET=<Twitter token secret>
    export TWITTER_TARGET_USER=<Your Twitter username>
    
    export TWILIO_USERNAME=<Twilio account sid>
    export TWILIO_PASSWORD=<Twilio auth token>
    export TWILIO_NUMBER_FROM=<Twilio phone number>
    export TWILIO_NUMBER_TO=<Phone number to send SMS>

Then run:

    $ mvn spring-boot:run

You can access:

- `hello-java` route entry point: <http://localhost:8080/hello?name=Camel>
- `hello-xml` route entry point: <http://localhost:8081/hello?name=Camel>
- hawtio console: <http://localhost:10001/hawtio/>
