package fr.ensim.interop.introrest.bot.executor;

import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.introrest.bot.BotImpl;
import fr.ensim.interop.introrest.model.Joke;

public class JokeExecutor extends Executor {

    @Override
    public String execute(String[] commands) {
        String url = BotImpl.URL + "joke";
        RestTemplate restTemplate = new RestTemplate();
        if (commands.length >= 2) {
            url += "?restriction=";
            url += commands[1];
        }
        Joke joke = restTemplate.getForObject(url, Joke.class);
        return joke.toString();
    }
    
}
