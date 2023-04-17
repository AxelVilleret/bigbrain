package fr.ensim.interop.introrest.bot.executor;

import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.introrest.bot.BotImpl;
import fr.ensim.interop.introrest.model.Joke;

public class JokeExecutor extends Executor {

    @Override
    public String execute(String[] commands) {
        RestTemplate restTemplate = new RestTemplate();
        Joke joke = restTemplate.getForObject(BotImpl.URL + "joke", Joke.class);
        return joke.toString();
    }
    
}
