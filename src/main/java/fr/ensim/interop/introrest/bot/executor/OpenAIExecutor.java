package fr.ensim.interop.introrest.bot.executor;

import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.introrest.bot.BotImpl;

public class OpenAIExecutor extends Executor {

    @Override
    public String execute(String[] commands) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : commands) {
            stringBuilder.append(s);
        }

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(BotImpl.URL + "openai/completions", stringBuilder.toString(), String.class);

    }
    
}
