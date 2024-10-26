package fr.ensim.interop.bigbrain.infrastructure.chat;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.bigbrain.domain.chat.ChatRepository;
import fr.ensim.interop.bigbrain.domain.chat.MessageWithAnswer;
import fr.ensim.interop.bigbrain.domain.chat.UserConversation;
import fr.ensim.interop.bigbrain.infrastructure.chat.model.Completion;
import fr.ensim.interop.bigbrain.infrastructure.chat.model.Message;
import fr.ensim.interop.bigbrain.infrastructure.chat.model.Request;

@Repository
public class OpenAIChatRepository implements ChatRepository {

    private static final String MODEL = "gpt-3.5-turbo-0125";
    private static final String USER_ROLE = "user";
    private static final String BOT_ROLE = "bot";

    @Value("${openai.api.url}")
    private String URL;

    @Value("${openai.api.token}")
    private String TOKEN;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getResponse(String content, UserConversation conversation) {
        ArrayList<Message> openAIConversation = convertConversationToModel(conversation);
        openAIConversation.add(new Message(USER_ROLE, content));
        HttpEntity<Request> entity = new HttpEntity<>(new Request(MODEL, openAIConversation), getHeaders());
        ResponseEntity<Completion> completion = restTemplate.exchange(URL + "chat/completions",
                HttpMethod.POST, entity, Completion.class);

        Completion responseBody = completion.getBody();
        if (responseBody != null && !responseBody.getChoices().isEmpty()) {
            return responseBody.getChoices().get(0).getMessage().getContent();
        } else {
            throw new IllegalStateException("La réponse de l'API est vide ou nulle");
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + TOKEN);
        headers.set("Content-Type", "application/json");
        return headers;
    }

    private ArrayList<Message> convertConversationToModel(UserConversation conversation) {
        ArrayList<Message> messages = new ArrayList<>();
        for (MessageWithAnswer messageWithAnswer : conversation) {
            messages.add(new Message(USER_ROLE, messageWithAnswer.getMessage().getContent()));
            messages.add(new Message(BOT_ROLE, messageWithAnswer.getAnswer().getContent()));
        }
        return messages;
    }
}