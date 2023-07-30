package fr.ensim.interop.introrest.bot;

import org.springframework.stereotype.Component;

import fr.ensim.interop.introrest.bot.executor.Executor;
import fr.ensim.interop.introrest.bot.executor.ForecastsExecutor;
import fr.ensim.interop.introrest.bot.executor.HelloExecutor;
import fr.ensim.interop.introrest.bot.executor.JokeExecutor;
import fr.ensim.interop.introrest.bot.executor.NoticeExecutor;
import fr.ensim.interop.introrest.bot.executor.OpenAIExecutor;
import fr.ensim.interop.introrest.model.telegram.Update;

@Component
public class BotImpl implements Bot {

    public static final String HELLO = "/hello_world";
    public static final String FORECASTS = "/meteo";
    public static final String JOKE = "/blague";
    public static final String NOTICE = "/aide";

    public final static String URL = "http://localhost:9090/";
    // public static final String URL = "https://chabot-api-ensim.herokuapp.com/";
    
    public static final String[] INTROSMETEO = {"Pour la météo d'aujourd'hui:\n", "Pour la météo de demain:\n", "Pour la météo d'après-demain:\n"};

    @Override
    public void onUpdatereceived(Update update) {
        String messageClient = update.getMessage().getText();
        Long idClient = update.getMessage().getFrom().getId();
        Integer idMessage = update.getMessage().getMessageId();
        String[] splitedMessage = messageClient.split(" ");
        Executor e;
        switch (splitedMessage[0]) {
            case HELLO:
                e = new HelloExecutor();
                break;
            case FORECASTS:
                e = new ForecastsExecutor();
                break;
            case JOKE:
                e = new JokeExecutor();
                break;
            case NOTICE:
                e = new NoticeExecutor();
                break;
            default:
                e = new OpenAIExecutor();
                break;
        }
        e.sendText(idClient, e.execute(splitedMessage), idMessage);
    }
    
}
