package fr.ensim.interop.introrest.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import fr.ensim.interop.introrest.bot.executor.DefaultExecutor;
import fr.ensim.interop.introrest.bot.executor.Executor;
import fr.ensim.interop.introrest.bot.executor.ForecastsExecutor;
import fr.ensim.interop.introrest.bot.executor.HelloExecutor;
import fr.ensim.interop.introrest.bot.executor.JokeExecutor;
import fr.ensim.interop.introrest.bot.executor.NoticeExecutor;

@Component
public class BotApi extends TelegramLongPollingBot {

    
    @Value("${telegram.bot.id}")
    private String telegramApiToken;

    @Value("${telegram.api.botusername}")
    private String telegramApiBotUsername;

    public static final String HELLO = "/hello_world";
    public static final String FORECASTS = "/meteo";
    public static final String JOKE = "/blague";
    public static final String NOTICE = "/aide";

    public static final String[] INTROSMETEO = { "Pour la météo d'aujourd'hui:\n", "Pour la météo de demain:\n",
            "Pour la météo d'après-demain:\n" };

    @Override
    public void onUpdateReceived(Update update) {
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
                e = new DefaultExecutor();
                break;
        }
        e.sendText(idClient, e.execute(splitedMessage), idMessage);
    }

    @Override
    public String getBotToken() {
        return telegramApiToken.substring(3);
    }

    @Override
    public String getBotUsername() {
        return telegramApiBotUsername;
    }
    
}
