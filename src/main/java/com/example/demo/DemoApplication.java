package com.example.demo;

import com.viber.bot.ViberBot;
import com.viber.bot.messages.Message;
import com.viber.bot.messages.MessageType;
import com.viber.bot.messages.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import com.google.common.util.concurrent.Futures;
import com.viber.bot.Request;
import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.profile.BotProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class DemoApplication {

    public static void test() throws IOException{

        ViberBot viberBot = new ViberBot("CreditsBot", "4d6643958767dc47-2d470f6727ff1975-d8b9cabe2682ea08");

    // start server on 8080 port with path /callback
       viberBot.listen("/");
    // let Viber API know about webhook url
//        viberBot.setWebhook("https://test-vbbot.herokuapp.com/");
    // subscribes on message events
        viberBot.addMessageListener((event, response) -> {
        Message message = event.getMessage();
        if (message.getType() == MessageType.TEXT) {
            TextMessage textMessage = (TextMessage) message;
            // send message back to user
            viberBot.sendMessage(message.getSender().getId(),
                    new TextMessage().setText("echo " + textMessage.getText()));
        }
    });
}
//
//    @Autowired
//    private ViberBot bot;
//
//    @Autowired
//    private ViberSignatureValidator signatureValidator;
//
//    @Value("${application.viber-bot.webhook-url}")
//    private String webhookUrl = "";
//
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        try {
            test();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent appReadyEvent) {
//        try {
//            bot.setWebhook(webhookUrl).get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        bot.onMessageReceived((event, message, response) -> response.send(message.getKeyboard().put("")));
//
//        bot.onMessageReceived((event, message, response) -> response.send(message)); // echos everything back
//        bot.onConversationStarted(event -> Futures.immediateFuture(Optional.of( // send 'Hi UserName' when conversation is started
//                new TextMessage("Hi " + event.getUser().getName()))));
//    }
//
//    @PostMapping(value = "/", produces = "application/json")
//    public String incoming(@RequestBody String json,
//                           @RequestHeader("X-Viber-Content-Signature") String serverSideSignature)
//            throws ExecutionException, InterruptedException, IOException {
//        Preconditions.checkState(signatureValidator.isSignatureValid(serverSideSignature, json), "invalid signature");
//        @Nullable InputStream response = bot.incoming(Request.fromJsonString(json)).get();
//        return response != null ? CharStreams.toString(new InputStreamReader(response, Charsets.UTF_16)) : null;
//    }
//
//    @Configuration
//    public class BotConfiguration {
//
//        @Value("${application.viber-bot.auth-token}")
//        private String authToken;
//
//        @Value("${application.viber-bot.name}")
//        private String name;
//
//        @Nullable
//        @Value("${application.viber-bot.avatar:@null}")
//        private String avatar;
//
//        @Bean
//        ViberBot viberBot() {
//            return new ViberBot(new BotProfile(name, avatar), authToken);
//        }
//
//        @Bean
//        ViberSignatureValidator signatureValidator() {
//            return new ViberSignatureValidator(authToken);
//        }
//    }
}

