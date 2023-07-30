package fr.ensim.interop.introrest.bot.executor;

import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.introrest.bot.BotImpl;
import fr.ensim.interop.introrest.model.UserMessage;

public class OpenAIExecutor extends Executor {

    private Long userId;
    
    public OpenAIExecutor(Long userId) {
        super();
        this.userId = userId;
    }


    @Override
    public String execute(String[] commands) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : commands) {
            stringBuilder.append(s);
        }

        UserMessage message = new UserMessage();
        message.setUserId(userId);
        message.setContent(stringBuilder.toString());

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(BotImpl.URL + "openai/completions", message, String.class);

    }
    
}
