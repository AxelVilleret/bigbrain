package fr.ensim.interop.introrest.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.introrest.bot.BotImpl;
import fr.ensim.interop.introrest.bot.Chats.Chats;
import fr.ensim.interop.introrest.model.UserMessage;
import fr.ensim.interop.introrest.model.openai.Completion;
import fr.ensim.interop.introrest.model.openai.Message;
import fr.ensim.interop.introrest.model.openai.Request;

@Controller
public class OpenAIController {

    private static final String MODEL = "gpt-3.5-turbo-0125";
    private static final String USER_ROLE = "user";
    private static final String ERRORMESSAGE = "Désolé mais ce n'est pas une action valide... Tapper " + BotImpl.NOTICE
            + " pour voir la notice";

    @Value("${openai.api.url}")
    private String openaiApiUrl;

    @Value("${openai.api.token}")
    private String openaiApiToken;

    private Chats chats = new Chats();

    @PostMapping("/openai/completions")
    public ResponseEntity<String> respondToMessage(@RequestBody UserMessage message) {
        try {
            
            System.out.println("hello");
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            System.out.println(openaiApiToken);
            headers.set("Authorization", "Bearer " + openaiApiToken);
            headers.set("Content-Type", "application/json");
            Request request = new Request();
            request.setModel(MODEL);
            Message msg = new Message();
            msg.setContent(message.getContent());
            msg.setRole(USER_ROLE);
            ArrayList<Message> chat = chats.addMessage(message.getUserId(), msg);
            request.setMessages(chat);
            HttpEntity<Request> entity = new HttpEntity<>(request, headers);

            
            System.out.println("hello2");
            ResponseEntity<Completion> completion = restTemplate.exchange(openaiApiUrl + "chat/completions",
                    HttpMethod.POST, entity, Completion.class);
            System.out.println("hello3");
            System.out.println(completion.getBody().getChoices().get(0).getMessage().getContent());
            chats.addMessage(message.getUserId(), completion.getBody().getChoices().get(0).getMessage());
            return ResponseEntity.ok(completion.getBody().getChoices().get(0).getMessage().getContent());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ERRORMESSAGE);
        }
    }
}
