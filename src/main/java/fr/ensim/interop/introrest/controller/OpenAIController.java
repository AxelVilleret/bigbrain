package fr.ensim.interop.introrest.controller;

import java.util.ArrayList;

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
import fr.ensim.interop.introrest.model.openai.Completion;
import fr.ensim.interop.introrest.model.openai.Message;
import fr.ensim.interop.introrest.model.openai.Request;

@Controller
public class OpenAIController {

    private static final String MODEL = "gpt-3.5-turbo";
    private static final String ERRORMESSAGE = "Désolé mais ce n'est pas une action valide... Tapper " + BotImpl.NOTICE
            + " pour voir la notice";

    private static final String INTRO = "(Tu es implémenté dans un chatbot nommé BigBrain qui devient ton nom. Réponds au message qui va suivre après la parenthèse et surtout ne parle plus d'OpenAI.)";

    @Value("${openai.api.url}")
    private String openaiApiUrl;

    @Value("${openai.api.token}")
    private String openaiApiToken;

    @PostMapping("/openai")
    public ResponseEntity<String> respondToMessage(@RequestBody String message) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + openaiApiToken);
            headers.set("Content-Type", "application/json");
            Request request = new Request();
            request.setModel(MODEL);
            ArrayList<Message> prompt = new ArrayList<>();
            Message msg = new Message();
            msg.setContent(INTRO + message);
            msg.setRole("user");
            prompt.add(msg);
            request.setMessages(prompt);
            HttpEntity<Request> entity = new HttpEntity<>(request, headers);
            ResponseEntity<Completion> completion = restTemplate.exchange(openaiApiUrl + "chat/completions",
                    HttpMethod.POST, entity, Completion.class);
            return ResponseEntity.ok(completion.getBody().getChoices().get(0).getMessage().getContent());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERRORMESSAGE);
        }
    }
}
